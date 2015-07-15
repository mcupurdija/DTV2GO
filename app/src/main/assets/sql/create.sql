
-- DTV2GO DB CREATION SCRIPT

CREATE TABLE Channels (
	_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	ID INTEGER,
    Name TEXT NOT NULL,
	Icon_URI TEXT,
	Video_URI TEXT NOT NULL
);

CREATE TABLE Movies (
	_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	Title TEXT NOT NULL,
    Description TEXT,
	Duration TEXT,
	Genre TEXT,
	Poster TEXT,
	Video_URI TEXT NOT NULL,
	Subtitle TEXT
);

CREATE TABLE Favourites (
    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    ID INTEGER,
    Name TEXT NOT NULL,
    Icon_URI TEXT,
    Video_URI TEXT NOT NULL
);

CREATE TABLE Users (
    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    ID INTEGER,
    Name TEXT,
    Active INTEGER
);

CREATE TABLE Accounts (
    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    ID INTEGER,
    IMEI TEXT,
    MAC TEXT,
    Type TEXT,
    Created TEXT,
    Updated TEXT
);

-- INSERT INTO Channels VALUES (1, 0, 'RTS1', 'http://www.lyngsat-logo.com/hires/rr/rts_rs_1.png', '');
-- INSERT INTO Channels VALUES (2, 0, 'RTS2', 'http://www.lyngsat-logo.com/hires/rr/rts_rs_2.png', '');
-- INSERT INTO Channels VALUES (3, 0, 'PINK', 'http://www.lyngsat-logo.com/hires/pp/pink_1_rs.png', '');
-- INSERT INTO Channels VALUES (4, 0, 'B92', 'http://www.lyngsat-logo.com/hires/bb/b92_rs_tv.png', '');
-- INSERT INTO Channels VALUES (5, 0, 'PRVA', 'http://www.lyngsat-logo.com/hires/pp/prva_rs_crna_gora.png', '');
-- INSERT INTO Channels VALUES (6, 0, 'ANIMAL PLANET', 'http://www.lyngsat-logo.com/hires/aa/animal_planet_us.png', '');