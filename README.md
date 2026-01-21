# Crisis & Situation Tracker

Kriisi- ja hätätilanteiden hallintajärjestelmä, joka mahdollistaa kriisien ja niihin liittyvien tapahtumien reaaliaikaisen seurannan. Backend-palvelu hallinnoi kriisejä (FIRE, MEDICAL, TRAFFIC jne.), tapahtumia, prioriteetteja, statuksia sekä paikkatietoa.

## Tech Stack

| Komponentti | Versio |
|---|---|
| **Java** | 17 |
| **Spring Boot** | 4.0.1 |
| **PostgreSQL** | 15+ |
| **Gradle** | 9.2+ |

## Aloitus

### Vaatimukset

- Java 17+
- Docker & Docker Compose
- Gradle 9.2+

### Asennus

```bash
# Käynnistä PostgreSQL
docker-compose up -d

# Käynnistä sovellus
./gradlew bootRun
```

Sovellus käynnistyy osoitteessa `http://localhost:8080`

## API-dokumentaatio

### Crisis Endpoints

| Metodi | Endpoint | Kuvaus |
|--------|----------|--------|
| **POST** | `/api/crises` | Luo uusi kriisi |
| **GET** | `/api/crises` | Hae kaikki kriisit |
| **GET** | `/api/crises/active` | Hae aktiiviset kriisit |
| **GET** | `/api/crises/{id}` | Hae kriisi ID:llä |
| **PUT** | `/api/crises/{id}` | Päivitä kriisiä |
| **DELETE** | `/api/crises/{id}` | Arkistoi kriisi |

### Event Endpoints

| Metodi | Endpoint | Kuvaus |
|--------|----------|--------|
| **POST** | `/api/crises/{id}/events` | Lisää tapahtuma |
| **GET** | `/api/crises/{id}/events` | Hae tapahtumat |

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
  "description": "Useita loukkaantuneita",
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

## Domain-mallit

### Crisis
- **type:** FIRE, MEDICAL, TRAFFIC, HAZMAT, NATURAL_DISASTER, SECURITY, OTHER
- **status:** OPEN, ONGOING, RESOLVED, CLOSED, ARCHIVED
- **priority:** ROUTINE, URGENT, EMERGENCY, CRITICAL
- **location:** Sijainti ja koordinaatit (latitude/longitude)

### Event
- **type:** DISPATCH, ARRIVED, TREATING, PATIENT_TRANSPORTED, jne.
- **severity:** LOW, MEDIUM, HIGH, CRITICAL
- **description:** Tapahtuman kuvaus

## Projektin rakenne

```
src/main/java/com/example/eventsituationtracker/
├── domain/           # Entiteetit (Crisis, Event)
├── repository/       # Data-aksessi
├── service/          # Liiketoimintalogiikka
├── controller/       # REST API
└── dto/              # Data Transfer Objects
```

## Testaus

**Unit-testit:**
```bash
./gradlew test
```

**API-testit:**
- Käytä [Postman Collectionia](./docs/postman-collection.json)

Testit tarkistavat:
- Crisis-luonti ja -validointi
- Event-luonti ja kriisi-linkitys
- Status-siirtymät ja prioriteetit

## Lisenssi

MIT License
