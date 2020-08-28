CREATE TABLE Books
(
    ID       INTEGER NOT NULL AUTO_INCREMENT,
    Title    VARCHAR(255) NOT NULL,
    Publisher VARCHAR(255) NOT NULL,
    Publish_date INTEGER NOT NULL,
    Page_count INTEGER NOT NULL,
    Isbn VARCHAR(255) NOT NULL,
    Description VARCHAR(255),
    Total_amount INTEGER NOT NULL,
    Available_amount INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE Readers
(
    ID       INTEGER NOT NULL AUTO_INCREMENT,
    Name    VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Date_of_registration DATE,
    Phone_number BIGINT,
    PRIMARY KEY (ID)
);

CREATE TABLE Authors (
    ID          INTEGER NOT NULL AUTO_INCREMENT,
    Name VARCHAR (255) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE Genres (
    ID          INTEGER NOT NULL AUTO_INCREMENT,
    Title VARCHAR (255) NOT NULL,
    PRIMARY KEY (ID)
);


CREATE TABLE Books_Readers
(
    ID       INTEGER NOT NULL AUTO_INCREMENT,
    Book_ID INTEGER NOT NULL,
    Reader_ID INTEGER NOT NULL,
    Borrow_date DATE NOT NULL,
    Time_Period INTEGER NOT NULL,
    Return_date DATETIME,
    PRIMARY KEY (ID),
    FOREIGN KEY (Book_ID) REFERENCES Books (ID) ON DELETE CASCADE,
    FOREIGN KEY (Reader_ID) REFERENCES Readers (ID) ON DELETE CASCADE
);


CREATE TABLE Books_Authors
(
    ID          INTEGER NOT NULL AUTO_INCREMENT,
    Book_ID INTEGER NOT NULL,
    Author_ID INTEGER NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Book_ID) REFERENCES Books (ID) ON DELETE CASCADE,
    FOREIGN KEY (Author_ID) REFERENCES Authors (ID) ON DELETE CASCADE
);

CREATE TABLE Books_Genres
(
    ID    long NOT NULL AUTO_INCREMENT,
    Book_ID INTEGER NOT NULL,
    Genre_ID INTEGER NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Book_ID) REFERENCES Books (ID) ON DELETE CASCADE,
    FOREIGN KEY (Genre_ID) REFERENCES Genres (ID) ON DELETE CASCADE
);

CREATE TABLE Covers
(
    ID    long NOT NULL AUTO_INCREMENT,
    Book_ID INTEGER NOT NULL,
    Title VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (Book_ID) REFERENCES Books (ID) ON DELETE CASCADE
);

INSERT INTO Books(Title, Publisher, Publish_date, Page_count, Isbn, Description, Total_amount, Available_amount)
VALUES ('AAA', 'Alpina', 2013, 294, '2-266-11156', 'AAA', 4, 4),
       ('BBB', 'Business Book', 2008, 424, '2-254-15436', 'BBB', 3, 3),
       ('CCC', 'Alpina', 2019, 178, '2-433-54545', 'CCC', 1, 1),
       ('DDD', 'Business Book', 2016, 524, '3-458-19854', 'DDD', 5, 5),
       ('EEE', 'Tech', 1999, 344, '5-566-46738', 'EEE', 1, 1),
       ('FFF', 'Admire', 1998, 376, '6-326-17890', 'FFF', 5, 5),
       ('GGG', 'Alpina', 2004, 322, '4-222-43436', 'GGG', 6, 6),
       ('HHH', 'Peresvet', 2006, 214, '3-346-14346', 'HHH', 4, 4),
       ('III', 'Tech', 2009, 336, '8-166-99900', 'III', 6, 6),
       ('JJJ', 'Alpina', 2018, 817, '9-446-44456', 'JJJ', 4, 4),
       ('KKK', 'Admire', 1998, 376, '6-326-17890', 'KKK', 5, 5),
       ('LLL', 'Alpina', 2004, 322, '4-222-43436', 'LLL', 6, 6),
       ('MMM', 'Peresvet', 2006, 214, '3-346-14346', 'MMM', 4, 4),
       ('NNN', 'Tech', 2009, 336, '8-166-99900', 'NNN', 6, 6),
       ('OOO', 'Alpina', 2018, 817, '9-446-44456', 'OOO', 4, 4),
       ('PPP', 'Alpina', 2018, 817, '9-446-44456', 'PPP', 4, 3);

INSERT INTO Readers(Name, Email, Date_of_registration, Phone_number)
VALUES ('Kuznetsov', 'kuznetsov@gmail.com', '2016-12-31', 375292342343),
       ('Pavlov', 'pavlov@gmail.com', '2018-11-24', 375294234234),
       ('Sidorov', 'sidorov@gmail.com', '2014-01-06', 375445556677),
       ('Ivanov', 'ivanov@gmail.com', '2014-02-27', 375334456567),
       ('Prokofev', 'prokofev@gmail.com', '2018-10-11', 375299807654),
       ('Poloneichik', 'poloneichik@gmail.com', '2019-09-24',375295433455),
       ('Borisov', 'borisov@gmail.com', '2020-07-01', 375294343434),
       ('Antonov', 'antonov@gmail.com', '2015-10-09', 375448768736),
       ('Efremov', 'efremov@gmail.com', '2015-11-11', 375295454556),
       ('Alferov', 'alferov@gmail.com', '2020-02-03', 375337767763),
       ('Petrov', 'petrov@gmail.com', '2019-04-23', 375444565651),
       ('Kozlov', 'kozlov@gmail.com', '2008-08-12', 375335435430);

INSERT INTO Authors(Name)
VALUES ('Kuznetsov'),
       ('Pavlov'),
       ('Sidorov'),
       ('Ivanov'),
       ('Prokofev'),
       ('Poloneichik'),
       ('Borisov'),
       ('Antonov'),
       ('Efremov'),
       ('Alferov'),
       ('Petrov'),
       ('Kozlov'),
       ('Borisov'),
       ('Antonov'),
       ('Efremov'),
       ('Alferov'),
       ('Petrov'),
       ('Kozlov');

INSERT INTO Genres(Title)
VALUES ('horror'),
       ('comedy'),
       ('science'),
       ('fantasy'),
       ('drama');

INSERT INTO Covers(Book_ID, Title)
VALUES (1, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (1, 'ceff27d9d31e677493288f8a95e9ebc3.png'),
       (1, 'f2314ffdcbc394a3bdd0ba8543a56844.png'),
       (2, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (3, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (4, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (5, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (6, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (7, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (8, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (9, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (10, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (11, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (12, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (13, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (14, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (15, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (16, '69aacd9ea1219a3a4b43b64f6a4c7276.png');

INSERT INTO Books_Authors(Book_ID, Author_ID)
VALUES (1, 1),
       (1, 4),
       (2, 3),
       (3, 6),
       (4, 5),
       (5, 6),
       (6, 11),
       (7, 12),
       (8, 16),
       (9, 8),
       (9, 9),
       (10, 14),
       (11, 2),
       (12, 10),
       (13, 7),
       (14, 13),
       (15, 15),
       (15, 18),
       (16, 17),
       (16, 3);

INSERT INTO Books_Readers(Book_ID, Reader_ID, Borrow_date, Time_Period, Return_date)
VALUES (1, 1, '2019-11-27', 3, '2019-12-31 16:24:00'),
       (1, 2, '2016-12-31', 6, '2017-03-31 12:31:08'),
       (2, 3, '2015-01-04', 2, '2015-02-24 13:02:36'),
       (2, 4, '2020-06-01', 6, '2020-06-30 15:22:16'),
       (3, 5, '2019-11-16', 3, '2019-12-24 16:02:12'),
       (3, 6, '2020-08-03', 1, '2020-08-24 16:02:12'),
       (2, 2, '2019-08-21', 6, '2019-08-31 12:03:31'),
       (6, 3, '2014-10-22', 3, '2014-11-14 14:22:10'),
       (5, 1, '2018-01-29', 6, '2018-04-16 14:04:44'),
       (8, 8, '2016-09-27', 3, '2016-10-29 10:24:06');

INSERT INTO Books_Readers(Book_ID, Reader_ID, Borrow_date, Time_Period)
VALUES (16, 1, '2020-08-21', 3);

INSERT INTO Books_Genres(Book_ID, Genre_ID)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 3),
       (2, 5),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 4),
       (4, 5),
       (5, 2),
       (5, 3),
       (6, 4),
       (6, 5),
       (7, 1),
       (7, 2),
       (7, 3),
       (7, 4),
       (8, 2),
       (9, 3),
       (9, 4),
       (10, 4),
       (10, 5),
       (11, 1),
       (11, 2),
       (12, 4),
       (13, 3),
       (14, 1),
       (14, 2),
       (15, 3),
       (15, 4),
       (15, 5),
       (16, 1),
       (16, 2),
       (16, 3),
       (16, 4),
       (16, 5);
