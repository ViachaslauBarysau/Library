CREATE TABLE Books
(
    ID       INTEGER NOT NULL AUTO_INCREMENT,
    Title    VARCHAR(255) NOT NULL,
    Publisher VARCHAR(255) NOT NULL,
    Publish_date INTEGER NOT NULL,
    Page_count INTEGER NOT NULL,
    Isbn VARCHAR(255) NOT NULL,
    Description VARCHAR(400),
    Total_amount INTEGER NOT NULL,
    Available_amount INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE Readers
(
    ID       INTEGER NOT NULL AUTO_INCREMENT,
    Name    VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
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
    ReaderCard_ID       INTEGER NOT NULL AUTO_INCREMENT,
    Book_ID INTEGER NOT NULL,
    Reader_ID INTEGER NOT NULL,
    Borrow_date DATE NOT NULL,
    Status VARCHAR NOT NULL,
    Due_date DATE NOT NULL,
    Return_date DATETIME,
    Comment VARCHAR,
    PRIMARY KEY (ReaderCard_ID),
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
VALUES ('The Film Experience', 'Alpina', 2012, 512, '2-266-11156', 'Enjoy a more poignant film experience by diving' ||
        ' into the details which built modern-day cinema as Film Experience combines, technology, business, the visual ' ||
        'language of film, and its history into a single, cohesive presentation of this ever-evolving medium.', 2, 0),
       ('War and Peace', 'Alpina', 2008, 1225, '2-254-15436', 'The novel spans the period 1805 to 1820. ' ||
        'The era of Catherine the Great was still fresh in the minds of older people. Catherine had made ' ||
        'French the language of her royal court. For the next 100 years, it became a social requirement for' ||
        ' the Russian nobility to speak French and understand French culture', 3, 3),
       ('The Captain''s Daughter', 'Alpina', 2004, 178, '2-433-54545', 'Pyotr Andreyich Grinyov (the narrative ' ||
        'is conducted on his behalf) is the only surviving child of a retired army officer. When Pyotr turns 17,' ||
        ' his father sends him into military service in Orenburg. En route Pyotr gets lost in a blizzard, but is ' ||
        'rescued by a mysterious man. As a token of his gratitude, Pyotr gives the guide his hareskin coat.', 1, 1),
       ('Dead Souls', 'Myth', 2008, 524, '3-458-19854', 'The story follows the exploits of Chichikov, a middle-aged ' ||
        'gentleman of middling social class and means. Chichikov arrives in a small town and turns on the charm to ' ||
        'woo key local officials and landowners. He reveals little about his past, or his purpose, as he sets about ' ||
        'carrying out his bizarre and mysterious plan to acquire "dead souls."', 5, 5),
       ('Kashtanka ', 'Admire', 1999, 444, '5-566-46738', 'Based on a new translation and adapted especially for ' ||
        'young readers, Kashtanka is an enchanting introduction to the work of one of the world’s foremost authors.' ||
        ' Gennady Spirin’s award-winning illustrations bring new life to this adaptation of Anton Chekhov’s ' ||
        'charming adventure.', 1, 0),
       ('Ruslan and Ludmila', 'Admire', 1998, 376, '6-326-17890', 'In a brief prologue, the narrator of the story ' ||
        'describes a green oak by the sea, on which a learned cat walks back and forth on a gold chain. When the ' ||
        'cat walks to the right, he sings - to the left he tells stories. In this magical place, ' ||
        'fairy tales come alive, including those common in Russian folktales.', 5, 5),
       ('Mumu', 'Alpina', 2004, 522, '4-222-43436', 'The story opens in Moscow, at the home of an unnamed, wealthy, ' ||
        'and elderly widow. Mean and spiteful, she has been abandoned by whatever living friends and relatives she ' ||
         'still has. The exposition then focuses on one of her porters, Gerasim, a man from the countryside.', 6, 6),
       ('Fathers and Sons', 'Peresvet', 2006, 814, '3-346-14346', 'Arkady Kirsanov has just graduated from the ' ||
        'University of Petersburg and returns with a friend, Bazarov, to his father''s modest estate in an outlying ' ||
        'province of Russia. His father, Nikolay, gladly receives the two young men at his estate, called ' ||
        'Marino, but Nikolay''s brother, Pavel, soon becomes upset by the strange new philosophy.', 4, 4),
       ('Evenings on a Farm Near Dikanka', 'Peresvet', 2001, 636, '8-166-99900', 'Evenings on a Farm Near Dikanka is' ||
        ' a collection of short stories by Nikolai Gogol, written in 1829–32. They appeared in various magazines and' ||
        ' were published in book form when Gogol was twenty-two. The writer was born in the village of Velyki ' ||
        'Sorochyntsi near Poltava, and he spent his life in Ukraine up to the age of nineteen.', 6, 6),
       ('Crime and Punishment', 'Alpina', 2004, 817, '9-446-44456', 'Rodion Romanovich Raskolnikov, a former law ' ||
        'student, lives in extreme poverty in a tiny, rented room in Saint Petersburg. Isolated and antisocial, he ' ||
        'has abandoned all attempts to support himself, and is brooding obsessively on a scheme' ||
        ' he has devised to murder and rob an elderly pawn-broker.', 4, 4),
       ('The Idiot', 'Admire', 1998, 376, '6-326-17890', 'Prince Myshkin, a young man in his mid-twenties and a ' ||
        'descendant of one of the oldest Russian lines of nobility, is on a train to Saint Petersburg on a cold ' ||
        'November morning. He is returning to Russia having spent the past four years in a ' ||
        'Swiss clinic for treatment of a severe epileptic condition.', 5, 5),
       ('Poems', 'Alpina', 2004, 322, '4-222-43436', 'Collection of poem written by Fyodor Tyutchev.', 6, 6),
       ('Romeo and Juliet', 'Peresvet', 2006, 914, '3-346-14346', 'Romeo and Juliet is a tragedy written by William ' ||
        'Shakespeare early in his career about two young star-crossed lovers whose deaths ultimately' ||
        ' reconcile their feuding families. It was among Shakespeare''s most popular plays during his lifetime ' ||
        'and, along with Hamlet, is one of his most frequently performed plays.', 4, 4),
       ('The Book of Life', 'Romantic', 2009, 336, '8-166-99900', 'Bringing the magic and suspense of the' ||
        ' All Souls Trilogy to a deeply satisfying conclusion, this highly anticipated finale went' ||
        ' straight to #1 on the New York Times bestseller list.', 6, 6),
       ('Migrations', 'Alpina', 2018, 817, '9-446-44456', 'Franny Stone has always been a wanderer. By following ' ||
        'the ocean’s tides and the birds that soar above, she can forget the losses that have haunted her life. But' ||
        ' when the wild she loves begins to disappear, Franny can no longer wander without a destination.', 4, 4),
       ('Vesper Flights', 'Alpina', 2018, 817, '9-446-44456', 'Vesper Flights is a book about observation, ' ||
        'fascination, time, memory, love and loss and how we make the world around us. Moving and frank, personal ' ||
        'and political, it confirms Helen Macdonald as one of this century''s greatest nature writers.', 2, 1);

INSERT INTO Readers(Name, Email, Date_of_registration, Phone_number)
VALUES ('Alexandr Kuznetsov', 'kuznetsov@gmail.com', '2016-12-31', 375292342343),
       ('Polina Pavlova', 'pavlova@gmail.com', '2018-11-24', 375294234234),
       ('Stanislav Sidorov', 'sidorov@gmail.com', '2014-01-06', 375445556677),
       ('Ivan Ivanov', 'ivanov@gmail.com', '2014-02-27', 375334456567),
       ('Svetlana Prokofeva', 'prokofeva@gmail.com', '2018-10-11', 375299807654),
       ('Valerii Poloneichik', 'poloneichik@gmail.com', '2019-09-24',375295433455),
       ('Anton Antonov', 'antonov@gmail.com', '2015-10-09', 375448768736),
       ('Viachaslau Borisov', 'borisov@gmail.com', '2020-07-01', 375294343434),
       ('Elena Efremova', 'efremoav@gmail.com', '2015-11-11', 375295454556),
       ('Sergei Alferov', 'alferov@gmail.com', '2020-02-03', 375337767763),
       ('Petr Petrov', 'petrov@gmail.com', '2019-04-23', 375444565651),
       ('Antonina Ivanova', 'ivanova2020@gmail.com', '2008-08-12', 375335435430);

INSERT INTO Authors(Name)
VALUES ('Timothy Corrigan'),
       ('Pavlov'),
       ('Leo Tolstoy'),
       ('Patricia White'),
       ('Nikolai Gogol'),
       ('Aleksandr Pushkin'),
       ('Fyodor Dostoevsky'),
       ('Fyodor Tyutchev'),
       ('William Shakespeare'),
       ('Deborah Harkness'),
       ('Anton Chekhov'),
       ('Ivan Turgenev'),
       ('Charlotte McConaghy'),
       ('Helen Macdonald');

INSERT INTO Genres(Title)
VALUES ('horror'),
       ('comedy'),
       ('science'),
       ('fantasy'),
       ('drama');

INSERT INTO Covers(Book_ID, Title)
VALUES (1, 'a3103bf8144b3d9eb6caa1f9cd054a8e.png'),
       (2, '69aacd9ea1219a3a4b43b64f6a4c7276.png'),
       (3, '8050c43d87011868b0705f83ac453cf2.png'),
       (4, '5347ce8a2856b9813d0e83ee4650f925.png'),
       (5, '42095c5de08042104e757cbea336f9eb.png'),
       (6, 'ruslan-and-ludmila-9781471177453_xlg.jpg'),
       (7, 'beadcdd4850bdc000250c67e62088c1a.png'),
       (8, '06e9cc1b60e4d28d08134f1048028b5a.png'),
       (9, '1d923a2644f586ee00f8df7a566806dc.png'),
       (10, 'ab6d9d25f1bd696f2d5e44149b8d8e82.png'),
       (11, '28a4ab2a9d54b9b5a8d3f64b4d41df9e.png'),
       (12, 'd51b8bc4e4e061944916f414014fb9ac.png'),
       (13, '1424f3059622f672343ad639ea566fcd.png'),
       (14, '8c31fba4177c38792d8cd4f6f8852bb8.png'),
       (15, 'a9763bc01ef6534ca83bf898b9aa78b6.png'),
       (16, '5573252d8727cc831655eb5a2fc33f94.png');

INSERT INTO Books_Authors(Book_ID, Author_ID)
VALUES (1, 1),
       (1, 4),
       (2, 3),
       (3, 6),
       (4, 5),
       (5, 11),
       (6, 6),
       (7, 12),
       (8, 12),
       (9, 5),
       (10, 7),
       (11, 7),
       (12, 8),
       (13, 9),
       (14, 10),
       (15, 13),
       (16, 14);

INSERT INTO Books_Readers(Book_ID, Reader_ID, Borrow_date, Status, Due_date, Return_date)
VALUES (1, 1, '2019-11-27', 'returned', '2020-02-27', '2019-12-31 16:24:00'),
       (1, 2, '2016-12-31', 'returned', '2017-07-31', '2017-03-31 12:31:08'),
       (2, 3, '2015-01-04', 'returned', '2015-03-04','2015-02-24 13:02:36'),
       (2, 4, '2020-06-01', 'damaged', '2021-01-01', '2020-06-30 15:22:16'),
       (3, 5, '2019-11-16', 'returned', '2020-02-16', '2019-12-24 16:02:12'),
       (3, 6, '2020-08-03', 'lost', '2020-09-03', '2020-08-24 16:02:12'),
       (2, 2, '2019-08-21', 'returned', '2020-02-21', '2019-08-31 12:03:31'),
       (6, 3, '2014-10-22', 'lost', '2014-01-22', '2014-11-14 14:22:10'),
       (5, 1, '2018-01-29', 'returned', '2018-07-29', '2018-04-16 14:04:44'),
       (8, 8, '2016-09-27', 'returned', '2016-12-27', '2016-10-29 10:24:06');

INSERT INTO Books_Readers(Book_ID, Reader_ID, Borrow_date, Status, Due_date)
VALUES (5, 2, '2020-08-24', 'borrowed', '2020-11-24'),
       (16, 1, '2020-08-21', 'borrowed', '2020-11-21'),
       (1, 8, '2020-09-08', 'borrowed', '2020-12-08'),
       (1, 3, '2020-07-10', 'borrowed', '2020-10-10');

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
