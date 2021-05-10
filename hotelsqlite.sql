/*
Regarding Room_ID
(Hotel ID number.Room type number concatted with the room ID number of that type in that hotel)
E.g. (2.34) is the fourth standard triple room in hotel number 2.
(2.414) is the 14th superior room in hotel number 2.
Room types:
-1:Standard Single
-2:Standard Twin
-3:Standard Triple
-4:Superior Room
We have 5 more room types to work with if necessary.
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
CREATE TABLE HOTEL
	( Name VARCHAR(100) NOT NULL
	, Location VARCHAR(100) NOT NULL
	, Rating DECIMAL(3,2)
	, PRIMARY KEY(Name,Location)
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
	( Room_ID INT NOT NULL
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
	, Room_ID INT NOT NULL
	, Entry_Date DATE NOT NULL
	, Exit_Date DATE NOT NULL
	, User VARCHAR(100) NOT NULL
	, Price DECIMAL(10,2) -- Null if no payment is required
	, FOREIGN KEY(Hotel_Name, Hotel_Location) REFERENCES HOTEL(Name, Location)
	);

PRAGMA foreign_keys=ON;

INSERT INTO HOTEL VALUES('Hotel Cabin', 'Reykjavik', 7);
INSERT INTO HOTEL VALUES('Hotel Klettur', 'Reykjavik', 8.4);
INSERT INTO HOTEL VALUES('Hotel Ork', 'Hveragerdi', 8.8);

INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 111, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 112, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Cabin', 'Reykjavik', 14, 'Superior');
INSERT INTO ROOM VALUES('Hotel Klettur', 'Reykjavik', 21, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Klettur', 'Reykjavik', 22, 'Standard Twin');
INSERT INTO ROOM VALUES('Hotel Ork', 'Hveragerdi', 311, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Ork', 'Hveragerdi', 312, 'Standard Single');
INSERT INTO ROOM VALUES('Hotel Ork', 'Hveragerdi', 313, 'Standard Single');
