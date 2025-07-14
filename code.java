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

    private AtomicInteger userIdCounter = new AtomicInteger(2);
    private AtomicInteger carrierIdCounter = new AtomicInteger(1);
    private AtomicInteger bookingIdCounter = new AtomicInteger(1001);
    private AtomicInteger flightIdCounter = new AtomicInteger(101);

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
        int id = carrierIdCounter.getAndIncrement();
        carriers.add(new Carrier(id, name));
        System.out.println("Carrier added: " + name);
    }

    private void editCarrier() {
        System.out.print("Carrier ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Carrier c : carriers) {
            if (c.id == id) {
                System.out.print("New Name: ");
                c.name = sc.nextLine();
                System.out.println("Carrier updated: " + c.name);
                return;
            }
        }
        System.out.println("Carrier not found.");
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

    static class Carrier {
        int id;
        String name;
        public Carrier(int id, String name) {
            this.id = id;
            this.name = name;
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
