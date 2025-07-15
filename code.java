import java.util.*;
import java.text.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        new AirlineManagementSystem().start();
    }
}

class AirlineManagementSystem {
    private Scanner sc = new Scanner(System.in);
    private List<User> users = new ArrayList<>();
    private List<Carrier> carriers = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    private AtomicInteger userIdCounter = new AtomicInteger(10000);       // Starts from 5-digit ID
    private AtomicInteger carrierIdCounter = new AtomicInteger(20000);    // Optional: 5-digit Carrier ID
    private AtomicInteger bookingIdCounter = new AtomicInteger(30000);    // Optional: 5-digit Booking ID
    private AtomicInteger flightIdCounter = new AtomicInteger(40000);     // Optional: 5-digit Flight ID


    public void start() {
        // Prefilled Admin
        users.add(new User(1, "admin", "admin123", "Admin", "", "", "", "", "", "", "", "", "Admin", "Gold", new Date()));
        // Prefilled Flights
        flights.add(new Flight(flightIdCounter.getAndIncrement(), "IndiGo", "Delhi", "Mumbai", "25-Aug-2025", 4500));
        flights.add(new Flight(flightIdCounter.getAndIncrement(), "AirAsia", "Chennai", "Kolkata", "20-Aug-2025", 5000));
        flights.add(new Flight(flightIdCounter.getAndIncrement(), "SpiceJet", "Bangalore", "Hyderabad", "15-Aug-2025", 3500));

        while (true) {
            System.out.println("\n--- Airline Management System ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Admin Registration");
            System.out.println("4. Customer Registration");
            System.out.println("5. Exit");
            System.out.print("Enter option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> customerLogin();
                case 3 -> register("Admin");
                case 4 -> register("Customer");
                case 5 -> {
                    System.out.println("Thank you for using AMS.");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private void register(String role) {
        System.out.println("\n--- " + role + " Registration ---");
        int id = userIdCounter.getAndIncrement();
        System.out.print("Enter UserName: ");
        String name = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Address1: ");
        String addr1 = sc.nextLine();
        System.out.print("Enter Address2: ");
        String addr2 = sc.nextLine();
        System.out.print("Enter City: ");
        String city = sc.nextLine();
        System.out.print("Enter State: ");
        String state = sc.nextLine();
        System.out.print("Enter Country: ");
        String country = sc.nextLine();
        System.out.print("Enter ZipCode: ");
        String zip = sc.nextLine();
        System.out.print("Enter DOB (dd-MM-yyyy): ");
        Date dob = null;
        try {
            dob = new SimpleDateFormat("dd-MM-yyyy").parse(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid date. Setting default date.");
            dob = new Date();
        }
        users.add(new User(id, name, pass, role, phone, email, addr1, addr2, city, state, country, zip, role, "", dob));
        System.out.println(role + " Registered successfully! Your ID: " + id);
    }

    private void adminLogin() {
        System.out.println("\n--- Admin Login ---");
        login("Admin");
    }

    private void customerLogin() {
        System.out.println("\n--- Customer Login ---");
        login("Customer");
    }

    private void login(String role) {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        for (User u : users) {
            if (u.id == id && u.password.equals(pass) && u.role.equals(role)) {
                System.out.println("Welcome, " + u.name + "!");
                if (role.equals("Admin")) adminMenu(u);
                else customerMenu(u);
                return;
            }
        }
        System.out.println("Invalid ID or Password");
    }

    private void adminMenu(User admin) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Carrier");
            System.out.println("2. Edit Carrier");
            System.out.println("3. Delete Carrier");
            System.out.println("4. Flight Cancellation Refund");
            System.out.println("5. Exit");
            System.out.print("Enter option: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> addCarrier();
                case 2 -> editCarrier();
                case 3 -> deleteCarrier();
                case 4 -> System.out.println("Flight cancellation refund to be implemented.");
                case 5 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void customerMenu(User user) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Edit Profile");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Exit");
            System.out.print("Enter option: ");
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1 -> editProfile(user);
                case 2 -> bookTicket(user);
                case 3 -> cancelTicket(user);
                case 4 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void editProfile(User user) {
        while (true) {
            System.out.println("\nEdit Profile Menu:");
            System.out.println("1. Edit Phone");
            System.out.println("2. Edit Email");
            System.out.println("3. Edit Address1");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.print("New Phone: ");
                    user.phone = sc.nextLine();
                }
                case 2 -> {
                    System.out.print("New Email: ");
                    user.email = sc.nextLine();
                }
                case 3 -> {
                    System.out.print("New Address1: ");
                    user.address1 = sc.nextLine();
                }
                case 4 -> { return; }
            }
            System.out.println("Profile Updated: " + user);
        }
    }

private void addCarrier() {
    System.out.print("Carrier Name: ");
    String name = sc.nextLine();
    System.out.print("Discount for 30 Days Advance Booking (%): ");
    int d30 = sc.nextInt();
    System.out.print("Discount for 60 Days Advance Booking (%): ");
    int d60 = sc.nextInt();
    System.out.print("Discount for 90 Days Advance Booking (%): ");
    int d90 = sc.nextInt();
    System.out.print("Bulk Booking Discount (%): ");
    int bulk = sc.nextInt();
    System.out.print("Refund if cancelled within 2 days (%): ");
    int r2 = sc.nextInt();
    System.out.print("Refund if cancelled within 10 days (%): ");
    int r10 = sc.nextInt();
    System.out.print("Refund if cancelled 20+ days before (%): ");
    int r20 = sc.nextInt();
    System.out.print("Silver User Discount (%): ");
    int silver = sc.nextInt();
    System.out.print("Gold User Discount (%): ");
    int gold = sc.nextInt();
    System.out.print("Platinum User Discount (%): ");
    int platinum = sc.nextInt();
    sc.nextLine(); // Consume newline

    int id = carrierIdCounter.getAndIncrement();
    Carrier c = new Carrier(id, name, d30, d60, d90, bulk, r2, r10, r20, silver, gold, platinum);
    carriers.add(c);
    System.out.println("Carrier Information Saved Successfully in the System. ID: " + id);
}

private void editCarrier() {
    System.out.print("Enter Carrier ID to edit: ");
    int id = sc.nextInt();
    sc.nextLine();
    for (Carrier c : carriers) {
        if (c.id == id) {
            System.out.println("Current Carrier Info:\n" + c);

            System.out.print("New Carrier Name (" + c.name + "): ");
            String name = sc.nextLine();
            c.name = name.isEmpty() ? c.name : name;

            System.out.print("Discount for 30 Days (" + c.discount30 + "): ");
            c.discount30 = sc.nextLine().trim().isEmpty() ? c.discount30 : sc.nextInt();
            System.out.print("Discount for 60 Days (" + c.discount60 + "): ");
            c.discount60 = sc.nextLine().trim().isEmpty() ? c.discount60 : sc.nextInt();
            System.out.print("Discount for 90 Days (" + c.discount90 + "): ");
            c.discount90 = sc.nextLine().trim().isEmpty() ? c.discount90 : sc.nextInt();
            System.out.print("Bulk Booking Discount (" + c.bulkDiscount + "): ");
            c.bulkDiscount = sc.nextLine().trim().isEmpty() ? c.bulkDiscount : sc.nextInt();
            System.out.print("Refund 2 Days (" + c.refund2Days + "): ");
            c.refund2Days = sc.nextLine().trim().isEmpty() ? c.refund2Days : sc.nextInt();
            System.out.print("Refund 10 Days (" + c.refund10Days + "): ");
            c.refund10Days = sc.nextLine().trim().isEmpty() ? c.refund10Days : sc.nextInt();
            System.out.print("Refund 20+ Days (" + c.refund20Days + "): ");
            c.refund20Days = sc.nextLine().trim().isEmpty() ? c.refund20Days : sc.nextInt();
            System.out.print("Silver Discount (" + c.silverDiscount + "): ");
            c.silverDiscount = sc.nextLine().trim().isEmpty() ? c.silverDiscount : sc.nextInt();
            System.out.print("Gold Discount (" + c.goldDiscount + "): ");
            c.goldDiscount = sc.nextLine().trim().isEmpty() ? c.goldDiscount : sc.nextInt();
            System.out.print("Platinum Discount (" + c.platinumDiscount + "): ");
            c.platinumDiscount = sc.nextLine().trim().isEmpty() ? c.platinumDiscount : sc.nextInt();

            sc.nextLine(); // Clean buffer
            System.out.println("Carrier updated successfully.");
            return;
        }
    }
    System.out.println("Carrier not found with ID: " + id);
}


    private void deleteCarrier() {
        System.out.print("Carrier ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        carriers.removeIf(c -> c.id == id);
        System.out.println("Carrier deleted.");
    }

    private void bookTicket(User user) {
        System.out.println("\nAvailable Flights:");
        for (Flight f : flights)
            System.out.println(f.id + " | " + f.carrier + " | " + f.from + " -> " + f.to + " | " + f.date + " | ₹" + f.fare);

        System.out.print("Enter Flight ID: ");
        int fid = sc.nextInt();
        sc.nextLine();
        Flight chosen = null;
        for (Flight f : flights) {
            if (f.id == fid) {
                chosen = f;
                break;
            }
        }
        if (chosen == null) {
            System.out.println("Invalid flight ID");
            return;
        }

        System.out.print("Enter ticket count: ");
        int count = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Seat Category (Economy/Business/Executive): ");
        String seat = sc.nextLine();
        double baseFare = chosen.fare;
        if (seat.equalsIgnoreCase("Business")) baseFare *= 2;
        else if (seat.equalsIgnoreCase("Executive")) baseFare *= 5;

        double total = baseFare * count;
        // Apply dummy discount (could add booking date logic here)
        if (count >= 10) total *= 0.98;
        switch (user.userCategory) {
            case "Silver" -> total *= 0.99;
            case "Gold" -> total *= 0.98;
            case "Platinum" -> total *= 0.96;
        }

        int bid = bookingIdCounter.getAndIncrement();
        bookings.add(new Booking(bid, user.id, fid, count, total));
        System.out.println("Booking confirmed! Amount: ₹" + total + ", Booking ID: " + bid);
    }

    private void cancelTicket(User user) {
        System.out.print("Enter Booking ID to cancel: ");
        int bid = sc.nextInt();
        sc.nextLine();
        Booking found = null;
        for (Booking b : bookings) {
            if (b.id == bid && b.userId == user.id) {
                found = b;
                break;
            }
        }
        if (found == null) {
            System.out.println("Booking not found.");
            return;
        }
        double refund = found.amount * 0.95;
        System.out.println("Booking cancelled. Refund amount: ₹" + refund);
        bookings.remove(found);
    }

    // Classes
    static class User {
        int id;
        String name, password, role;
        String phone, email, address1, address2, city, state, country, zip, userCategory;
        Date dob;

        public User(int id, String name, String pass, String role, String phone, String email, String addr1, String addr2, String city, String state, String country, String zip, String roleDup, String userCategory, Date dob) {
            this.id = id; this.name = name; this.password = pass; this.role = role;
            this.phone = phone; this.email = email; this.address1 = addr1; this.address2 = addr2;
            this.city = city; this.state = state; this.country = country; this.zip = zip;
            this.userCategory = userCategory; this.dob = dob;
        }

        public String toString() {
            return id + " | " + name + " | " + phone + " | " + email + " | " + address1 + " | " + role;
        }
    }

// Update Carrier class
static class Carrier {
    int id;
    String name;
    int discount30, discount60, discount90, bulkDiscount;
    int refund2Days, refund10Days, refund20Days;
    int silverDiscount, goldDiscount, platinumDiscount;

    public Carrier(int id, String name, int discount30, int discount60, int discount90,
                   int bulkDiscount, int refund2Days, int refund10Days, int refund20Days,
                   int silverDiscount, int goldDiscount, int platinumDiscount) {
        this.id = id;
        this.name = name;
        this.discount30 = discount30;
        this.discount60 = discount60;
        this.discount90 = discount90;
        this.bulkDiscount = bulkDiscount;
        this.refund2Days = refund2Days;
        this.refund10Days = refund10Days;
        this.refund20Days = refund20Days;
        this.silverDiscount = silverDiscount;
        this.goldDiscount = goldDiscount;
        this.platinumDiscount = platinumDiscount;
    }

    public String toString() {
        return "CarrierID: " + id + "\nCarrierName: " + name +
               "\n30 Days Discount: " + discount30 + "%" +
               "\n60 Days Discount: " + discount60 + "%" +
               "\n90 Days Discount: " + discount90 + "%" +
               "\nBulk Booking Discount: " + bulkDiscount + "%" +
               "\nRefund (2 days): " + refund2Days + "%" +
               "\nRefund (10 days): " + refund10Days + "%" +
               "\nRefund (20+ days): " + refund20Days + "%" +
               "\nSilver Discount: " + silverDiscount + "%" +
               "\nGold Discount: " + goldDiscount + "%" +
               "\nPlatinum Discount: " + platinumDiscount + "%";
    }
}

    static class Flight {
        int id;
        String carrier, from, to, date;
        double fare;
        public Flight(int id, String carrier, String from, String to, String date, double fare) {
            this.id = id; this.carrier = carrier; this.from = from; this.to = to; this.date = date; this.fare = fare;
        }
    }

    static class Booking {
        int id, userId, flightId, count;
        double amount;
        public Booking(int id, int uid, int fid, int count, double amt) {
            this.id = id; this.userId = uid; this.flightId = fid; this.count = count; this.amount = amt;
        }
    }
}
