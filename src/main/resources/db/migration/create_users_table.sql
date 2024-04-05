CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email varchar(150) NOT NULL
);
