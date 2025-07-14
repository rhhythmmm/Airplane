import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().start();
    }

    private Map<String, User> users = new HashMap<>();
    private Map<String, Flight> flights = new HashMap<>();
    private Map<String, List<Flight>> bookings = new HashMap<>();
    private String currentUserId = null;

    private void start() {
        while (true) {
            System.out.println("\n=== Airline Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: register(); break;
                case 2: login(); break;
                case 3: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void register() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pwd = sc.nextLine();
        System.out.print("Enter Role (Admin/Customer): ");
        String role = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        if (users.containsKey(id)) {
            System.out.println("User already exists!");
            return;
        }

        users.put(id, new User(id, pwd, role, name, email));
        System.out.println("✅ Registered Successfully!");
    }

    private void login() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pwd = sc.nextLine();

        if (!users.containsKey(id) || !users.get(id).password.equals(pwd)) {
            System.out.println("❌ Invalid credentials.");
            return;
        }

        currentUserId = id;
        System.out.println("✅ Login Successful!");

        if (users.get(id).role.equalsIgnoreCase("Admin")) {
            adminMenu();
        } else {
            customerMenu();
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Flight");
            System.out.println("2. View Flights");
            System.out.println("3. Cancel Flight (System-wide)");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: addFlight(); break;
                case 2: viewFlights(); break;
                case 3: cancelFlightSystemWide(); break;
                case 4: currentUserId = null; return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Edit Profile");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: viewFlights(); break;
                case 2: bookFlight(); break;
                case 3: viewBookings(); break;
                case 4: cancelBooking(); break;
                case 5: editProfile(); break;
                case 6: currentUserId = null; return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void addFlight() {
        System.out.print("Enter Flight ID: ");
        String id = sc.nextLine();
        System.out.print("From: ");
        String from = sc.nextLine();
        System.out.print("To: ");
        String to = sc.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        if (flights.containsKey(id)) {
            System.out.println("Flight already exists!");
            return;
        }

        flights.put(id, new Flight(id, from, to, date, price));
        System.out.println("✅ Flight added successfully!");
    }

    private void viewFlights() {
        if (flights.isEmpty()) {
            System.out.println("❗ No flights available.");
            return;
        }
        System.out.println("\n🛫 Available Flights:");
        for (Flight f : flights.values()) {
            System.out.println(f);
        }
    }

    private void cancelFlightSystemWide() {
        System.out.print("Enter Flight ID to cancel: ");
        String id = sc.nextLine();

        if (!flights.containsKey(id)) {
            System.out.println("Flight not found!");
            return;
        }

        flights.remove(id);

        for (List<Flight> list : bookings.values()) {
            list.removeIf(f -> f.id.equals(id));
        }

        System.out.println("✅ Flight cancelled for all users.");
    }

    private void bookFlight() {
        System.out.print("Enter Flight ID to book: ");
        String id = sc.nextLine();

        if (!flights.containsKey(id)) {
            System.out.println("❌ Invalid flight ID.");
            return;
        }

        bookings.putIfAbsent(currentUserId, new ArrayList<>());
        bookings.get(currentUserId).add(flights.get(id));
        System.out.println("✅ Flight booked successfully!");
    }

    private void viewBookings() {
        List<Flight> userBookings = bookings.getOrDefault(currentUserId, new ArrayList<>());
        if (userBookings.isEmpty()) {
            System.out.println("❗ No bookings found.");
        } else {
            System.out.println("\n📄 Your Bookings:");
            for (Flight f : userBookings) {
                System.out.println(f);
            }
        }
    }

    private void cancelBooking() {
        List<Flight> userBookings = bookings.get(currentUserId);
        if (userBookings == null || userBookings.isEmpty()) {
            System.out.println("❗ No bookings found.");
            return;
        }

        System.out.print("Enter Flight ID to cancel: ");
        String id = sc.nextLine();

        boolean removed = userBookings.removeIf(f -> f.id.equals(id));
        if (removed) {
            System.out.println("✅ Booking cancelled.");
        } else {
            System.out.println("❌ Booking not found.");
        }
    }

    private void editProfile() {
        User u = users.get(currentUserId);
        System.out.println("Current Name: " + u.name);
        System.out.print("New Name: ");
        u.name = sc.nextLine();

        System.out.println("Current Email: " + u.email);
        System.out.print("New Email: ");
        u.email = sc.nextLine();

        System.out.println("✅ Profile updated!");
    }

    // Inner classes
    static class User {
        String id, password, role, name, email;

        public User(String id, String password, String role, String name, String email) {
            this.id = id;
            this.password = password;
            this.role = role;
            this.name = name;
            this.email = email;
        }
    }

    static class Flight {
        String id, from, to, date;
        double price;

        public Flight(String id, String from, String to, String date, double price) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.date = date;
            this.price = price;
        }

        public String toString() {
            return id + ": " + from + " → " + to + " on " + date + " | ₹" + price;
        }
    }
}
