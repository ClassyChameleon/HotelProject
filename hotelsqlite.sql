/*
Regarding ROOM.Room_ID
(Hotel ID number.Room type number concatted with the room ID number of that type in that hotel)
E.g. (2.34) is the fourth standard triple room in hotel number 2.
(2.414) is the 14th superior room in hotel number 2.
Room types:
-1:Standard Single
-2:Standard Twin
-3:Standard Triple
-4:Superior Room
We have 5 more room types to work with if necessary.
Of course in real life this would be just the room number,
I thought this system would be cool to use as a mock database.
*/
/*
Regarding ROOM_PRICE
Hotels might want to charge more on weekends or holidays.
If date is not between Start_Date and End_Date in ROOM_PRICE, assume it's unavailable.
If End_Date is NULL, the price never changes.
Optimally this table would be updated periodically:
- Start_Date moved up to today's date
- Entries removed after End_Date has passed
*/
/*
Regarding PROFILE.role
0: Owner
1: Customer/standard user {may book hotel rooms}
2: Hotel owner/manager {may manage their hotels or add new hotels and rooms}
3: Staff/Moderator {yet to be decided how this role works}
*/
CREATE TABLE HOTEL
	( Name VARCHAR(100) NOT NULL
	, Location VARCHAR(100) NOT NULL
	, Rating DECIMAL(3,2)
	, Owner VARCHAR(25) NOT NULL
	, PRIMARY KEY(Name,Location)
	, FOREIGN KEY(Owner) REFERENCES PROFILE(Username)
	);

CREATE TABLE ROOM
	( Hotel_Name VARCHAR(100) NOT NULL
	, Hotel_Location VARCHAR(100) NOT NULL
	, Room_ID DECIMAL(3,5) NOT NULL
	, Room_Type VARCHAR(50) NOT NULL
	, PRIMARY KEY(Hotel_Name, Hotel_Location, Room_ID)
	, FOREIGN KEY(Hotel_Name, Hotel_Location) REFERENCES HOTEL(Name, Location)
	);
CREATE TABLE ROOM_PRICE
	( Room_ID DECIMAL(3,5) NOT NULL
	, Start_Date DATE NOT NULL 
	, End_Date DATE
	, Price DECIMAL(10,2) NOT NULL
	, PRIMARY KEY(Room_ID, Start_Date, End_Date)
	, UNIQUE(Room_ID, Start_Date)
	, UNIQUE(Room_ID, End_Date)
	, FOREIGN KEY(Room_ID) REFERENCES ROOM(Room_ID)
	);
CREATE TABLE BOOKING
	( Hotel_Name VARCHAR(100) NOT NULL
	, Hotel_Location VARCHAR(100) NOT NULL
	, Room_ID DECIMAL(3,5) NOT NULL
	, Entry_Date DATE NOT NULL
	, Exit_Date DATE NOT NULL
	, User VARCHAR(100) NOT NULL
	, Price DECIMAL(10,2) -- Null if no payment is required
	, PRIMARY KEY(Room_ID, Entry_Date)
	, FOREIGN KEY(Hotel_Name, Hotel_Location) REFERENCES HOTEL(Name, Location)
	);
CREATE TABLE PROFILE
	( Username VARCHAR(25) NOT NULL
	, Password VARCHAR(25) NOT NULL 
	, Role TINYINT NOT NULL
	, PRIMARY KEY(Username)
	);

PRAGMA foreign_keys=ON;

INSERT INTO PROFILE VALUES('gvg8', '123', 0);
INSERT INTO PROFILE VALUES('JonRagnarsson', 'Password', 2);
INSERT INTO PROFILE VALUES('karlhermann', 'kalli', 1);
INSERT INTO PROFILE VALUES('maggi', 'gammi', 1);

INSERT INTO HOTEL VALUES('Hotel Cabin', 'Reykjavik', 7, 'JonRagnarsson');
INSERT INTO HOTEL VALUES('Hotel Klettur', 'Reykjavik', 8.4, 'JonRagnarsson');
INSERT INTO HOTEL VALUES('Hotel Ork', 'Hveragerdi', 8.8, 'JonRagnarsson');

INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 1.11, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 1.12, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 1.31, 'Standard Triple');
INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 1.41, 'Superior');
INSERT INTO ROOM VALUES('Hotel Klettur', 'Reykjavik', 2.11, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Klettur', 'Reykjavik', 2.21, 'Standard Twin');
INSERT INTO ROOM VALUES('Hotel Ork', 'Hveragerdi', 3.11, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Ork', 'Hveragerdi', 3.12, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Ork', 'Hveragerdi', 3.13, 'Standard Single');

INSERT INTO BOOKING VALUES('Hotel Cabin', 'Reykjavik', 1.12, '2021-01-01', '2030-12-31', 'maggi', NULL);

SELECT * FROM BOOKING;