CREATE TABLE country (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  population INT NOT NULL,
  capital_city VARCHAR(255),
  location VARCHAR(255),
  currency VARCHAR(255) NOT NULL,
  iso2 VARCHAR(255) NOT NULL,
  iso3 VARCHAR(255) NOT NULL
);

CREATE TABLE state (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  state_code VARCHAR(255) NOT NULL,
  country_id BIGINT NOT NULL,
  FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE city (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  population INT,
  state_id BIGINT NOT NULL,
  FOREIGN KEY (state_id) REFERENCES state(id)
);

CREATE TABLE conversion_rate (
  id SERIAL PRIMARY KEY,
  source_currency VARCHAR(255) NOT NULL,
  target_currency VARCHAR(255) NOT NULL,
  rate DECIMAL(19, 2) NOT NULL
);





