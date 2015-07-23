
-- DTV2GO DB CREATION SCRIPT

CREATE TABLE Channels (
	_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	ID INTEGER,
    Name TEXT NOT NULL,
	Icon_URI TEXT,
	Video_URI TEXT NOT NULL,
	Quality INTEGER NOT NULL
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
    Video_URI TEXT NOT NULL,
    Quality INTEGER NOT NULL
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

-- INSERT INTO Channels VALUES (1, 0, 'RTS1', 'http://www.lyngsat-logo.com/hires/rr/rts_rs_1.png', '', 0);
-- INSERT INTO Channels VALUES (2, 0, 'RTS2', 'http://www.lyngsat-logo.com/hires/rr/rts_rs_2.png', '', 0);
-- INSERT INTO Channels VALUES (3, 0, 'PINK', 'http://www.lyngsat-logo.com/hires/pp/pink_1_rs.png', '', 0);
-- INSERT INTO Channels VALUES (4, 0, 'B92', 'http://www.lyngsat-logo.com/hires/bb/b92_rs_tv.png', '', 0);
-- INSERT INTO Channels VALUES (5, 0, 'PRVA', 'http://www.lyngsat-logo.com/hires/pp/prva_rs_crna_gora.png', '', 0);
-- INSERT INTO Channels VALUES (6, 0, 'ANIMAL PLANET', 'http://www.lyngsat-logo.com/hires/aa/animal_planet_us.png', '', 0);

INSERT INTO Movies VALUES (1, 'Gladiator', 'When a Roman general is betrayed and his family murdered by an emperor''s corrupt son, he comes to Rome as a gladiator to seek revenge.', '110 min', 'Akcija', 'http://ia.media-imdb.com/images/M/MV5BMTgwMzQzNTQ1Ml5BMl5BanBnXkFtZTgwMDY2NTYxMTE@._V1_SY317_CR0,0,214,317_AL_.jpg', '', '');
INSERT INTO Movies VALUES (2, 'Titanic', 'A seventeen-year-old aristocrat falls in love with a kind, but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', '120 min', 'Drama', 'http://ia.media-imdb.com/images/M/MV5BMjExNzM0NDM0N15BMl5BanBnXkFtZTcwMzkxOTUwNw@@._V1_SY317_CR0,0,214,317_AL_.jpg', '', '');