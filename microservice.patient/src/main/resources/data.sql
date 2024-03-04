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