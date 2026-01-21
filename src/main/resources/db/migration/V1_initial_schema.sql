-- Crisis-taulu
CREATE TABLE crises (
    id UUID PRIMARY KEY,
    crisis_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    resolved_at TIMESTAMP,
    closed_at TIMESTAMP,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

CREATE INDEX idx_crisis_status ON crises(status);
CREATE INDEX idx_crisis_priority ON crises(priority);
CREATE INDEX idx_crisis_location ON crises(location);
CREATE INDEX idx_crisis_created ON crises(created_at);

-- Event-taulu (päivitetty)
CREATE TABLE events (
    id UUID PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    severity VARCHAR(50),
    timestamp TIMESTAMP NOT NULL,
    description TEXT,
    crisis_id UUID NOT NULL,
    FOREIGN KEY (crisis_id) REFERENCES crises(id)
);

CREATE INDEX idx_event_crisis ON events(crisis_id);
CREATE INDEX idx_event_type ON events(event_type);
CREATE INDEX idx_event_timestamp ON events(timestamp);