--liquibase formatted sql
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE specialist (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    status VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE specialty (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    status VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE disease (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    status VARCHAR(255),
    symptom TEXT,
    treatment TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE hospital (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    status VARCHAR(255),
    address TEXT,
    telephone VARCHAR(255),
    location geometry(Point, 4326),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE specialist_specialty (
    id SERIAL PRIMARY KEY,
    specialist INTEGER REFERENCES specialist(id),
    specialty INTEGER REFERENCES specialty(id),
    UNIQUE (specialist, specialty)
);

CREATE TABLE disease_specialty (
    id SERIAL PRIMARY KEY,
    disease   INTEGER REFERENCES disease(id),
    specialty INTEGER REFERENCES specialty(id),
    UNIQUE (disease, specialty)
);

CREATE TABLE hospital_specialist (
    id SERIAL PRIMARY KEY,
    hospital INTEGER REFERENCES hospital(id),
    specialist INTEGER REFERENCES specialist(id),
    UNIQUE (hospital, specialist)
);

CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    patient VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    hospital INTEGER REFERENCES hospital(id),
    specialist INTEGER REFERENCES specialist(id),
    appointment INTEGER REFERENCES appointment(id),
    date TIMESTAMP,
    from_time TIME,
    to_time TIME,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
