-------------------------------------------------
-- 1. TABLE CREATION SECTION
-------------------------------------------------

-- USER TABLE
CREATE TABLE [User] (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    UserName VARCHAR(100) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Role VARCHAR(10) CHECK (Role IN ('Admin', 'Customer')),
    CustomerCategory VARCHAR(10) CHECK (CustomerCategory IN ('Silver', 'Gold', 'Platinum')),
    Phone BIGINT,
    EmailID VARCHAR(100),
    Address1 VARCHAR(255),
    Address2 VARCHAR(255),
    City VARCHAR(100),
    State VARCHAR(100),
    Country VARCHAR(100),
    ZipCode INT,
    DOB DATE
);

-- CARRIER TABLE
CREATE TABLE Carrier (
    CarrierID INT PRIMARY KEY IDENTITY(1,1),
    CarrierName VARCHAR(100) NOT NULL,
    DiscountPercentageThirtyDaysAdvanceBooking INT,
    DiscountPercentageSixtyDaysAdvanceBooking INT,
    DiscountPercentageNinetyDaysAdvanceBooking INT,
    BulkBookingDiscount INT,
    RefundPercentageForTicketCancellation2DaysBeforeTravelDate INT,
    RefundPercentageForTicketCancellation10DaysBeforeTravelDate INT,
    RefundPercentageForTicketCancellation20DaysOrMoreBeforeTravelDate INT,
    SilverUserDiscount INT,
    GoldUserDiscount INT,
    PlatinumUserDiscount INT
);

-- FLIGHT TABLE
CREATE TABLE Flight (
    FlightID INT PRIMARY KEY IDENTITY(1,1),
    CarrierID INT FOREIGN KEY REFERENCES Carrier(CarrierID),
    Origin VARCHAR(100),
    Destination VARCHAR(100),
    AirFare INT,
    SeatCapacityEconomyClass INT CHECK (SeatCapacityEconomyClass >= 20),
    SeatCapacityBusinessClass INT CHECK (SeatCapacityBusinessClass >= 10),
    SeatCapacityExecutiveClass INT CHECK (SeatCapacityExecutiveClass >= 5)
);

-- FLIGHT SCHEDULE TABLE
CREATE TABLE FlightSchedule (
    FlightScheduleID INT PRIMARY KEY IDENTITY(1,1),
    FlightID INT FOREIGN KEY REFERENCES Flight(FlightID),
    DateOfTravel DATE,
    BusinessClassBookedCount INT DEFAULT 0,
    EconomyClassBookedCount INT DEFAULT 0,
    ExecutiveClassBookedCount INT DEFAULT 0
);

-- FLIGHT BOOKING TABLE
CREATE TABLE FlightBooking (
    BookingID INT PRIMARY KEY IDENTITY(1,1),
    FlightID INT FOREIGN KEY REFERENCES Flight(FlightID),
    UserID INT FOREIGN KEY REFERENCES [User](UserID),
    NoOfSeats INT,
    SeatCategory VARCHAR(20) CHECK (SeatCategory IN ('Economy', 'Executive', 'Business')),
    DateOfTravel DATE,
    BookingStatus VARCHAR(20) CHECK (BookingStatus IN ('Booked', 'Travel Completed', 'Cancelled')),
    BookingAmount INT
);

-------------------------------------------------
-- 2. INSERT SAMPLE DATA (US001 - b, US002, US007)
-------------------------------------------------

-- Insert into Carrier
INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking,
DiscountPercentageNinetyDaysAdvanceBooking, BulkBookingDiscount,
RefundPercentageForTicketCancellation2DaysBeforeTravelDate,
RefundPercentageForTicketCancellation10DaysBeforeTravelDate,
RefundPercentageForTicketCancellation20DaysOrMoreBeforeTravelDate,
SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount)
VALUES 
('IndiGo', 5, 10, 15, 8, 50, 70, 90, 5, 10, 15),
('Jet Airways', 4, 8, 12, 7, 40, 60, 85, 3, 8, 12);

-- Insert into Flight
INSERT INTO Flight (CarrierID, Origin, Destination, AirFare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass)
VALUES 
(1, 'Delhi', 'Mumbai', 4500, 100, 20, 10),
(2, 'Chennai', 'Bangalore', 3000, 80, 15, 8);

-- Insert into User
INSERT INTO [User] (UserName, Password, Role, CustomerCategory, Phone, EmailID, Address1, City, State, Country, ZipCode, DOB)
VALUES 
('admin', 'admin123', 'Admin', 'Gold', 1234567890, 'admin@example.com', 'Street 1', 'Delhi', 'Delhi', 'India', 110001, '1985-05-15'),
('john_doe', 'password123', 'Customer', 'Silver', 9876543210, 'john@example.com', 'Street 2', 'Mumbai', 'Maharashtra', 'India', 400001, '1990-09-25');

-------------------------------------------------
-- 3. QUERIES (US004 - US011)
-------------------------------------------------

-- US004: Get Carrier Name and total number of flights tagged to each carrier (ascending order)
SELECT c.CarrierName, COUNT(f.FlightID) AS FlightCount
FROM Carrier c
LEFT JOIN Flight f ON c.CarrierID = f.CarrierID
GROUP BY c.CarrierName
ORDER BY FlightCount ASC;

-- US005: Get FlightID, Carrier Name, Origin, Destination, and lowest AirFare for a given route
SELECT TOP 1 f.FlightID, c.CarrierName, f.Origin, f.Destination, f.AirFare
FROM Flight f
JOIN Carrier c ON f.CarrierID = c.CarrierID
WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai'
ORDER BY f.AirFare ASC;

-- US006: Search Flight with seat availability
SELECT f.FlightID, c.CarrierName, f.Origin, f.Destination, f.AirFare,
       (f.SeatCapacityEconomyClass - ISNULL(fs.EconomyClassBookedCount, 0)) AS AvailableEconomy,
       (f.SeatCapacityBusinessClass - ISNULL(fs.BusinessClassBookedCount, 0)) AS AvailableBusiness,
       (f.SeatCapacityExecutiveClass - ISNULL(fs.ExecutiveClassBookedCount, 0)) AS AvailableExecutive
FROM Flight f
JOIN Carrier c ON f.CarrierID = c.CarrierID
LEFT JOIN FlightSchedule fs ON f.FlightID = fs.FlightID
WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai';

-- US007: Insert flight booking
INSERT INTO FlightBooking (FlightID, UserID, NoOfSeats, SeatCategory, DateOfTravel, BookingStatus, BookingAmount)
VALUES (1, 2, 2, 'Economy', '2025-08-10', 'Booked', 9000);

-- US008: Cancel booking and update booked count
UPDATE FlightBooking
SET BookingStatus = 'Cancelled'
WHERE BookingID = 1;

UPDATE FlightSchedule
SET EconomyClassBookedCount = EconomyClassBookedCount - 2
WHERE FlightID = 1 AND DateOfTravel = '2025-08-10';

-- US009: Remove all flights of a given carrier and destination
DELETE FROM Flight
WHERE CarrierID = 1 AND Destination = 'Mumbai';

-- US010: View carrier names on a given route
SELECT DISTINCT c.CarrierName
FROM Flight f
JOIN Carrier c ON f.CarrierID = c.CarrierID
WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai';

-- US011: Increase airfare by 10% for route/carrier with min discount > 3%
UPDATE f
SET f.AirFare = f.AirFare * 1.10
FROM Flight f
JOIN Carrier c ON f.CarrierID = c.CarrierID
WHERE f.Origin = 'Delhi' AND f.Destination = 'Mumbai'
AND (c.DiscountPercentageThirtyDaysAdvanceBooking > 3 OR
     c.DiscountPercentageSixtyDaysAdvanceBooking > 3 OR
     c.DiscountPercentageNinetyDaysAdvanceBooking > 3);
