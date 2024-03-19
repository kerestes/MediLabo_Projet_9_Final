USE medilabo;

CREATE TABLE adresses(
    id INT NOT NULL AUTO_INCREMENT,
    adresse VARCHAR(255) NOT NULL,
    ville VARCHAR(255) NOT NULL,
    code_postal VARCHAR(18),
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

INSERT INTO adresses(adresse, ville, code_postal) values 
    ('1 Brookside St', 'New York', '1111-111'), 
    ('2 High St', 'New York', '1111-111'),
    ('3 Club Road', 'New York', '1111-111'), 
    ('4 Valley Dr', 'New York', '1111-111');

INSERT INTO patients(prenom, nom, date_de_naissance, genre, telephone, adresse_id) values
    ('Test', 'TestNone', '1966-12-31', 'F', '100-222-3333', 1),
    ('Test', 'TestBorderline', '1945-06-24', 'M', '200-333-4444', 2),
    ('Test', 'TestInDanger', '2004-06-18', 'M', '300-444-5555', 3),
    ('Test', 'TestEarlyOnset', '2002-06-28', 'F', '400-555-6666', 4);