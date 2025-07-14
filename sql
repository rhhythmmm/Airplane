-- Create the Users table
CREATE TABLE Users (
    UserID NUMBER PRIMARY KEY,
    UserName VARCHAR2(100) NOT NULL,
    Password VARCHAR2(100) NOT NULL,
    Role VARCHAR2(10) CHECK (Role IN ('Admin', 'Customer')),
    CustomerCategory VARCHAR2(10) CHECK (CustomerCategory IN ('Silver', 'Gold', 'Platinum')),
    Phone NUMBER(15),
    EmailID VARCHAR2(100),
    Address1 VARCHAR2(255),
    Address2 VARCHAR2(255),
    City VARCHAR2(100),
    State VARCHAR2(100),
    Country VARCHAR2(100),
    ZipCode NUMBER(10),
    DOB DATE
);

-- Create a sequence to auto-increment UserID
CREATE SEQUENCE UserSeq START WITH 1 INCREMENT BY 1;

-- Create a trigger to assign UserID from the sequence
CREATE OR REPLACE TRIGGER trg_UserID
BEFORE INSERT ON Users
FOR EACH ROW
BEGIN
    :NEW.UserID := UserSeq.NEXTVAL;
END;

CREATE TABLE Carrier (
    CarrierID NUMBER PRIMARY KEY,
    CarrierName VARCHAR2(100) NOT NULL,
    DiscountPercentageThirtyDaysAdvanceBooking NUMBER,
    DiscountPercentageSixtyDaysAdvanceBooking NUMBER,
    DiscountPercentageNinetyDaysAdvanceBooking NUMBER,
    BulkBookingDiscount NUMBER,
    RefundPercentageForTicketCancellation2DaysBeforeTravelDate NUMBER,
    RefundPercentageForTicketCancellation10DaysBeforeTravelDate NUMBER,
    RefundPercentageForTicketCancellation20DaysOrMoreBeforeTravelDate NUMBER,
    SilverUserDiscount NUMBER,
    GoldUserDiscount NUMBER,
    PlatinumUserDiscount NUMBER
);

-- 2. Create a sequence to auto-generate CarrierID
CREATE SEQUENCE CarrierSeq START WITH 1 INCREMENT BY 1;

-- 3. Create a trigger to assign the CarrierID automatically before insert
CREATE OR REPLACE TRIGGER trg_CarrierID
BEFORE INSERT ON Carrier
FOR EACH ROW
BEGIN
    :NEW.CarrierID := CarrierSeq.NEXTVAL;
END;

-- 1. Create the Flight table
CREATE TABLE Flight (
    FlightID NUMBER PRIMARY KEY,
    CarrierID NUMBER,
    Origin VARCHAR2(100),
    Destination VARCHAR2(100),
    AirFare NUMBER,
    SeatCapacityEconomyClass NUMBER CHECK (SeatCapacityEconomyClass >= 20),
    SeatCapacityBusinessClass NUMBER CHECK (SeatCapacityBusinessClass >= 10),
    SeatCapacityExecutiveClass NUMBER CHECK (SeatCapacityExecutiveClass >= 5),
    CONSTRAINT fk_flight_carrier FOREIGN KEY (CarrierID) REFERENCES Carrier(CarrierID)
);

-- 2. Create a sequence to auto-generate FlightID
CREATE SEQUENCE FlightSeq START WITH 1 INCREMENT BY 1;

-- 3. Create a trigger to auto-assign FlightID from the sequence
CREATE OR REPLACE TRIGGER trg_FlightID
BEFORE INSERT ON Flight
FOR EACH ROW
BEGIN
    :NEW.FlightID := FlightSeq.NEXTVAL;
END;

CREATE TABLE FlightSchedule (
    FlightScheduleID NUMBER PRIMARY KEY,
    FlightID NUMBER,
    DateOfTravel DATE,
    BusinessClassBookedCount NUMBER DEFAULT 0,
    EconomyClassBookedCount NUMBER DEFAULT 0,
    ExecutiveClassBookedCount NUMBER DEFAULT 0,
    CONSTRAINT fk_flight_schedule_flight FOREIGN KEY (FlightID) REFERENCES Flight(FlightID)
);


-- 2. Create a sequence to auto-increment FlightScheduleID
CREATE SEQUENCE FlightScheduleSeq START WITH 1 INCREMENT BY 1;


-- 3. Create a trigger to assign FlightScheduleID from the sequence
CREATE OR REPLACE TRIGGER trg_FlightScheduleID
BEFORE INSERT ON FlightSchedule
FOR EACH ROW
BEGIN
    :NEW.FlightScheduleID := FlightScheduleSeq.NEXTVAL;
END;

CREATE TABLE FlightBooking (
    BookingID NUMBER PRIMARY KEY,
    FlightID NUMBER,
    UserID NUMBER,
    NoOfSeats NUMBER,
    SeatCategory VARCHAR2(20) CHECK (SeatCategory IN ('Economy', 'Executive', 'Business')),
    DateOfTravel DATE,
    BookingStatus VARCHAR2(20) CHECK (BookingStatus IN ('Booked', 'Travel Completed', 'Cancelled')),
    BookingAmount NUMBER,
    CONSTRAINT fk_booking_flight FOREIGN KEY (FlightID) REFERENCES Flight(FlightID),
    CONSTRAINT fk_booking_user FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


-- 2. Create a sequence to auto-generate BookingID
CREATE SEQUENCE BookingSeq START WITH 1 INCREMENT BY 1;

-- 3. Create a trigger to auto-assign BookingID
CREATE OR REPLACE TRIGGER trg_BookingID
BEFORE INSERT ON FlightBooking
FOR EACH ROW
BEGIN
    :NEW.BookingID := BookingSeq.NEXTVAL;
END;

INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking,
DiscountPercentageNinetyDaysAdvanceBooking, BulkBookingDiscount,
RefundPercentageForTicketCancellation2DaysBeforeTravelDate,
RefundPercentageForTicketCancellation10DaysBeforeTravelDate,
RefundPercentageForTicketCancellation20DaysOrMoreBeforeTravelDate,
SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount)
VALUES 
('IndiGo', 5, 10, 15, 8, 50, 70, 90, 5, 10, 15),
('Jet Airways', 4, 8, 12, 7, 40, 60, 85, 3, 8, 12);

INSERT INTO Flight (CarrierID, Origin, Destination, AirFare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass)
VALUES 
(1, 'Delhi', 'Mumbai', 4500, 100, 20, 10),
(2, 'Chennai', 'Bangalore', 3000, 80, 15, 8);

INSERT INTO Users (
    UserName, Password, Role, CustomerCategory, Phone, EmailID, Address1, City, State, Country, ZipCode, DOB
) VALUES 
(
    'admin', 'admin123', 'Admin', 'Gold', 1234567890, 'admin@example.com', 'Street 1', 'Delhi', 'Delhi', 'India', 110001,
    TO_DATE('1985-05-15', 'YYYY-MM-DD')
),
(
    'john_doe', 'password123', 'Customer', 'Silver', 9876543210, 'john@example.com', 'Street 2', 'Mumbai', 'Maharashtra', 'India', 400001,
    TO_DATE('1990-09-25', 'YYYY-MM-DD')
);

SELECT c.CarrierName, COUNT(f.FlightID) AS FlightCount
FROM Carrier c
LEFT JOIN Flight f ON c.CarrierID = f.CarrierID
GROUP BY c.CarrierName
ORDER BY FlightCount ASC;

SELECT *
FROM (
    SELECT f.FlightID, c.CarrierName, f.Origin, f.Destination, f.AirFare
    FROM Flight f
    JOIN Carrier c ON f.CarrierID = c.CarrierID
    WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai'
    ORDER BY f.AirFare ASC
)
WHERE ROWNUM = 1;

SELECT f.FlightID, c.CarrierName, f.Origin, f.Destination, f.AirFare,
       (f.SeatCapacityEconomyClass - NVL(fs.EconomyClassBookedCount, 0)) AS AvailableEconomy,
       (f.SeatCapacityBusinessClass - NVL(fs.BusinessClassBookedCount, 0)) AS AvailableBusiness,
       (f.SeatCapacityExecutiveClass - NVL(fs.ExecutiveClassBookedCount, 0)) AS AvailableExecutive
FROM Flight f
JOIN Carrier c ON f.CarrierID = c.CarrierID
LEFT JOIN FlightSchedule fs ON f.FlightID = fs.FlightID
WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai';

INSERT INTO FlightBooking (
    FlightID, UserID, NoOfSeats, SeatCategory, DateOfTravel, BookingStatus, BookingAmount
)
VALUES (
    1, 2, 2, 'Economy', TO_DATE('2025-08-10', 'YYYY-MM-DD'), 'Booked', 9000
);

UPDATE FlightBooking
SET BookingStatus = 'Cancelled'
WHERE BookingID = 1;

UPDATE FlightSchedule
SET EconomyClassBookedCount = EconomyClassBookedCount - 2
WHERE FlightID = 1 AND DateOfTravel = TO_DATE('2025-08-10', 'YYYY-MM-DD');

-- Step 1: Delete from FlightBooking
DELETE FROM FlightBooking
WHERE FlightID IN (
    SELECT FlightID FROM Flight
    WHERE CarrierID = 1 AND Destination = 'Mumbai'
);

-- Step 2: Delete from FlightSchedule
DELETE FROM FlightSchedule
WHERE FlightID IN (
    SELECT FlightID FROM Flight
    WHERE CarrierID = 1 AND Destination = 'Mumbai'
);

-- Step 3: Delete from Flight
DELETE FROM Flight
WHERE CarrierID = 1 AND Destination = 'Mumbai';

SELECT DISTINCT c.CarrierName
FROM Flight f
JOIN Carrier c ON f.CarrierID = c.CarrierID
WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai';

UPDATE Flight f
SET f.AirFare = f.AirFare * 1.10
WHERE f.Origin = 'Delhi'
  AND f.Destination = 'Mumbai'
  AND EXISTS (
    SELECT 1
    FROM Carrier c
    WHERE c.CarrierID = f.CarrierID
      AND (
          c.DiscountPercentageThirtyDaysAdvanceBooking > 3 OR
          c.DiscountPercentageSixtyDaysAdvanceBooking > 3 OR
          c.DiscountPercentageNinetyDaysAdvanceBooking > 3
      )
);





