DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS adresses;

CREATE TABLE adresses(
    id INT NOT NULL AUTO_INCREMENT,
    adresse VARCHAR(255) NOT NULL,
    ville VARCHAR(255) NOT NULL,
    code_postal VARCHAR(18) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE patients(
    id INT NOT NULL AUTO_INCREMENT,
    prenom VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    date_de_naissance DATETIME NOT NULL,
    genre CHAR(1) NOT NULL,
    telephone VARCHAR(18),
    adresse_id INT,
    PRIMARY KEY(id),
    FOREIGN KEY (adresse_id) REFERENCES adresses(id)
);

INSERT INTO adresses (adresse, ville, code_postal) values
    ('1 Brookside St', 'Paris', '75010'),
    ('2 High St', 'Paris', '75001'),
    ('3 Club Road', 'Paris', '75012'),
    ('4 Valley Dr', 'Paris', '75018');

INSERT INTO patients (nom, prenom, date_de_naissance, genre, adresse_id, telephone) values
    ('TestNone', 'Test', '1966-12-31', 'F', 1, '100-222-3333'),
    ('TestBoderline', 'Test', '1945-06-24', 'M', 2, '200-33-4444'),
    ('TestDanger', 'Test', '2004-06-18', 'M', 3, '300-444-5555'),
    ('TestEarlyOnset', 'Test', '2002-06-28', 'F', 4, '400-555-6666');