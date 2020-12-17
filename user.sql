CREATE TABLE Nutzer(
    ID int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    Vorname TEXT NOT NULL,
    Nachname TEXT NOT NULL,
    Emailadresse VARCHAR(40) NOT NULL,
    Passwort TEXT NOT NULL,
    Reg_Datum int NOT NULL
);