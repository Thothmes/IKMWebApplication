CREATE TABLE galaxies (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

-- Создание таблицы "Планеты"
CREATE TABLE planets (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    galaxy_id SERIAL NOT NULL,
    FOREIGN KEY (galaxy_id) REFERENCES galaxies(id) ON DELETE CASCADE
);

-- Создание таблицы "Континенты"
CREATE TABLE continents (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    planet_id SERIAL NOT NULL,
    FOREIGN KEY (planet_id) REFERENCES planets(id) ON DELETE CASCADE
);

-- Создание таблицы "Страны"
CREATE TABLE countries (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    continent_id SERIAL NOT NULL,
    FOREIGN KEY (continent_id) REFERENCES continents(id) ON DELETE CASCADE
);

-- Создание таблицы "Города"
CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    country_id SERIAL NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE
);