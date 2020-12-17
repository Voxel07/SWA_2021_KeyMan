CREATE TABLE Company(
    ID int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    Name TEXT NOT NULL,
    Abteilung TEXT NOT NULL,
    Strasse VARCHAR(40) NOT NULL,
	Hausnummer int(4) ,
	Addresszusatz VARCHAR(40) ,
    Postleitzahl int(5) ,
    Ort VARCHAR(40) NOT NULL,
    Land VARCHAR(40) NOT NULL

);