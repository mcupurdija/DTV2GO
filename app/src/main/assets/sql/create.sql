
-- DTV2GO DB CREATION SCRIPT

CREATE TABLE Channels (
	_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name TEXT,
	icon_uri TEXT,
	video_uri TEXT
);

CREATE TABLE Favourites (
    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    channelId INTEGER NOT NULL
);

INSERT INTO Channels VALUES (1, 'RTS1', 'http://www.lyngsat-logo.com/hires/rr/rts_rs_1.png', '');
INSERT INTO Channels VALUES (2, 'RTS2', 'http://www.lyngsat-logo.com/hires/rr/rts_rs_2.png', '');
INSERT INTO Channels VALUES (3, 'PINK', 'http://www.lyngsat-logo.com/hires/pp/pink_1_rs.png', '');
INSERT INTO Channels VALUES (4, 'B92', 'http://www.lyngsat-logo.com/hires/bb/b92_rs_tv.png', '');
INSERT INTO Channels VALUES (5, 'PRVA', 'http://www.lyngsat-logo.com/hires/pp/prva_rs_crna_gora.png', '');
INSERT INTO Channels VALUES (6, 'ANIMAL PLANET', 'http://www.lyngsat-logo.com/hires/aa/animal_planet_us.png', '');