# Crisis & Situation Tracker – Backend

Kriisi- ja hätätilanteiden hallintajärjestelmä, sovellus mahdollistaa kriisien ja niihin liittyvien tapahtumien reaaliaikaisen seurannan, resurssien hallinnan ja tilannetiedon johtamiseen.

## 🎯 Yleiskatsaus

Backend-palvelu kriisi- ja hätätilanteiden hallintaan. Järjestelmä hallinnoi:

- **Kriisejä** - korkean tason kriisitilanteiden hallintaa (FIRE, MEDICAL, TRAFFIC jne.)
- **Tapahtumia** - kriiseihin liittyviä reaaliaikaisia päivityksiä (DISPATCH, ARRIVED, TREATING jne.)
- **Prioriteettia ja statusta** - kriisien vakavuuden ja tilan hallintaa
- **Sijaintia ja koordinaatteja** - paikkatietoa kriiseistä

Järjestelmä ei simuloi dataa - kaikki on käyttäjän tai järjestelmien luomaa reaaliaikaista dataa.

## Tech Stack

| Komponentti | Versio |
|---|---|
| **Java** | 17 |
| **Spring Boot** | 4.0.1 |
| **PostgreSQL** | 15+ |
| **Build Tool** | Gradle 9.2+ |
| **REST API** | Spring Web MVC |
| **ORM** | Hibernate / JPA |

## Arkkitehtuuri

```
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Domain Entities (Crisis, Event)
    ↓
PostgreSQL Database
```

Backend toimii **yhden totuuden lähteenä** - kaikki liiketoimintalogiikka ja tilanhallinta käsitellään palvelimen puolella.

## Aloitus

### Vaatimukset

- Java 17+
- Docker & Docker Compose
- PostgreSQL (docker-compose:ssä)
- Gradle 9.2+

### Asennus

1. **Kloonaa repon:**
```bash
git clone https://github.com/yourusername/eventsituationtracker.git
cd eventsituationtracker
```

2. **Käynnistä PostgreSQL (Docker):**
```bash
docker-compose up -d
```

3. **Käynnistä sovellus:**
```bash
./gradlew bootRun
```

Sovellus käynnistyy osoitteessa: `http://localhost:8080`

### Testaus

**Gradle-testit:**
```bash
./gradlew test
```

**API-testit:**
- Käytä [Postman Collectionia](./docs/postman-collection.json)
- Tai PowerShell-skriptejä (katso `docs/`)

## API-dokumentaatio

### Crisis Endpoints

| Metodi | Endpoint | Kuvaus |
|--------|----------|--------|
| **POST** | `/api/crises` | Luo uusi kriisi |
| **GET** | `/api/crises` | Hae kaikki kriisit |
| **GET** | `/api/crises/active` | Hae aktiiviset kriisit (OPEN, ONGOING) |
| **GET** | `/api/crises/{id}` | Hae kriisi ID:llä |
| **GET** | `/api/crises/status/{status}` | Hae kriisit statuksen mukaan |
| **GET** | `/api/crises/location/{location}` | Hae kriisit sijainnin mukaan |
| **PUT** | `/api/crises/{id}` | Päivitä kriisiä (status, priority, description) |
| **DELETE** | `/api/crises/{id}` | Arkistoi kriisi |

### Event Endpoints

| Metodi | Endpoint | Kuvaus |
|--------|----------|--------|
| **POST** | `/api/crises/{id}/events` | Lisää tapahtuma kriisin alle |
| **GET** | `/api/crises/{id}/events` | Hae kriisiin liittyvät tapahtumat |
| **GET** | `/api/crises/{id}/events/{eventId}` | Hae yksittäinen tapahtuma |

### Esimerkkipyynnöt

**Luo kriisi:**
```bash
POST /api/crises
Content-Type: application/json

{
  "type": "MEDICAL",
  "priority": "URGENT",
  "title": "Suuronnettomuus kauppakeskuksessa",
  "location": "Downtown Mall",
  "description": "Useita loukkaantuneet",
  "latitude": 60.1699,
  "longitude": 24.9384
}
```

**Lisää tapahtuma:**
```bash
POST /api/crises/{crisisId}/events
Content-Type: application/json

{
  "type": "DISPATCH",
  "severity": "HIGH",
  "description": "Ambulanssi 42 lähetetty paikalle"
}
```

**Päivitä kriisi:**
```bash
PUT /api/crises/{crisisId}
Content-Type: application/json

{
  "status": "ONGOING",
  "priority": "CRITICAL"
}
```

## Domain-mallit

### Crisis
```java
- id: UUID (pääavain)
- type: CrisisType (FIRE, MEDICAL, TRAFFIC, HAZMAT, NATURAL_DISASTER, SECURITY, OTHER)
- status: CrisisStatus (OPEN, ONGOING, RESOLVED, CLOSED, ARCHIVED)
- priority: CrisisPriority (ROUTINE, URGENT, EMERGENCY, CRITICAL)
- title: String
- location: String
- description: String
- latitude/longitude: Double (koordinaatit)
- events: List<Event> (liittyvät tapahtumat)
- createdAt/updatedAt/resolvedAt/closedAt: Instant
```

### Event
```java
- id: UUID (pääavain)
- type: CrisisEventType (DISPATCH, ARRIVED, TREATING, PATIENT_TRANSPORTED, jne.)
- severity: Severity (LOW, MEDIUM, HIGH, CRITICAL)
- description: String
- timestamp: Instant
- crisis: Crisis (viittaus pääkriisiin)
```

### Enumit

**CrisisType:** FIRE, MEDICAL, TRAFFIC, HAZMAT, NATURAL_DISASTER, SECURITY, OTHER

**CrisisStatus:** OPEN, ONGOING, RESOLVED, CLOSED, ARCHIVED

**CrisisPriority:** ROUTINE, URGENT, EMERGENCY, CRITICAL

**CrisisEventType:** DISPATCH, ARRIVED, TREATING, PATIENT_TRANSPORTED, DELIVERED, INCIDENT_CLOSED, RESOURCE_REQUEST, STATUS_UPDATE, ESCALATION, DEESCALATION, RESOURCE_REASSIGNMENT, WEATHER_UPDATE, TRAFFIC_UPDATE

**Severity:** LOW, MEDIUM, HIGH, CRITICAL

## 🗂️ Projektin rakenne

```
src/main/java/com/example/eventsituationtracker/
├── domain/                 # Entiteetit
│   ├── Crisis.java
│   ├── Event.java
│   ├── CrisisType.java
│   ├── CrisisStatus.java
│   ├── CrisisPriority.java
│   ├── CrisisEventType.java
│   └── Severity.java
├── repository/             # Data-aksessi
│   ├── CrisisRepository.java
│   └── EventRepository.java
├── service/                # Liiketoimintalogiikka
│   ├── CrisisService.java
│   └── EventService.java
├── controller/             # REST API
│   ├── CrisisController.java
│   ├── EventController.java
│   └── GlobalExceptionHandler.java
├── dto/                    # Data Transfer Objects
│   ├── CreateCrisisRequest.java
│   ├── UpdateCrisisRequest.java
│   ├── CrisisResponse.java
│   └── CreateEventRequest.java
└── config/
    └── WebConfig.java      # CORS-konfiguraatio
```

## Testaus

### Unit-testit
```bash
./gradlew test
```

Testit tarkistavat:
- ✅ Crisis-luonti ja -validointi (16 testiä)
- ✅ Event-luonti ja kriisi-linkitys (5 testiä)
- ✅ Status-siirtymät
- ✅ Prioriteetin muutokset
- ✅ Virheenhallinta

### Integraatiotestit

Käytä **Postman Collectionia** API-testaukseen:
1. Avaa Postman
2. Tuo `docs/postman-collection.json`
3. Aseta `crisisId` -muuttuja
4. Aja pyynnöt

## Tietoturva

- ✅ Input-validointi (Bean Validation)
- ✅ Exception handling
- ✅ CORS-konfiguraatio
- ⏳ JWT-autentikaatio (Vaihe 2)
- ⏳ Roolipohjainen pääsynvalvonta (Vaihe 2)

## Kehityssuunnitelma

**Vaihe 1 ✅ (Nykyinen)**
- Crisis & Event -hallinta
- Perus-API
- Unit-testit

**Vaihe 2 (Tulevaisuus)**
- Käyttäjäanhallinta & autentikaatio
- Resurssien hallinta
- WebSocket (reaaliaikaiset päivitykset)

**Vaihe 3 (Tulevaisuus)**
- Analytiikka & raportit
- Kartta-integraatio
- Ilmoitusjärjestelmä

## Muistiinpanot

- Kriisejä ei poisteta, vaan arkistoidaan
- Tapahtumat liittyvät aina kriisiin
- Status-siirtymät validoidaan
- Kaikki timestamp-tiedot ovat UTC-aikaa

## Tuki

Ongelmia? Tarkista:
1. Onko PostgreSQL käynnissä? → `docker ps`
2. Onko portti 8080 vapaana?
3. Onko Java 17+ asennettu? → `java -version`
4. Testit menivät läpi? → `./gradlew test`

## 📄 Lisenssi

MIT License
