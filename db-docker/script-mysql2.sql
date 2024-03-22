USE medilabo_auth;

CREATE TABLE users(
    id INT NOT NULL AUTO_INCREMENT,
    prenom VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role TINYINT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE backend_services(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    ip VARCHAR(255) NOT NULL,
    port INT NOT NULL,
    reg_number binary(16) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO users (prenom, nom, email, password, role) values
    ('Organisateur', 'Organisateur', 'organisateur@mail.com', '$2a$12$iQg4bka5RySvKQNNBIy1Ve5RNwSvGvuieNpUiN9XcBUxsG9q6h.l2', 0),
    ('Medecin', 'Medecin', 'medecin@mail.com', '$2a$12$iQg4bka5RySvKQNNBIy1Ve5RNwSvGvuieNpUiN9XcBUxsG9q6h.l2', 1);