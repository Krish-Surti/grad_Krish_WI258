import java.util.Scanner;

public class Main {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int mainChoice;

        do {
            System.out.println("\n===== Layout Maintenance Application =====");
            System.out.println("1. Enter as Admin");
            System.out.println("2. Enter as Owner");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            mainChoice = sc.nextInt();

            switch (mainChoice) {
                case 1:
                    adminLogin(sc);
                    break;
                case 2:
                    ownerMenu(sc);
                    break;
                case 3:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (mainChoice != 3);

        sc.close();
    }

    private static void adminLogin(Scanner sc) {

        System.out.print("Admin Username: ");
        String user = sc.next();

        System.out.print("Admin Password: ");
        String pass = sc.next();

        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            adminMenu(sc);
        } else {
            System.out.println("Invalid admin credentials");
        }
    }

    private static void adminMenu(Scanner sc) {

        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Owners");
            System.out.println("2. View All Sites");
            System.out.println("3. View Pending Maintenance");
            System.out.println("4. Collect Maintenance");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    AdminOperations.viewAllOwners();
                    break;
                case 2:
                    AdminOperations.viewSites(false);
                    break;
                case 3:
                    AdminOperations.viewSites(true);
                    break;
                case 4:
                    System.out.print("Enter Site ID: ");
                    AdminOperations.collectMaintenance(sc.nextInt());
                    break;
            }

        } while (choice != 5);
    }

    private static void ownerMenu(Scanner sc) {

        int choice;
        do {
            System.out.println("\n--- Owner Menu ---");
            System.out.println("1. Register Owner");
            System.out.println("2. Add Site");
            System.out.println("3. View My Sites");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("First Name: ");
                    String fn = sc.next();
                    System.out.print("Last Name: ");
                    String ln = sc.next();
                    System.out.print("Phone (10 digits): ");
                    String phone = sc.next();
                    OwnerOperations.addOwner(fn, ln, phone);
                    break;

                case 2:
                    System.out.print("Owner ID: ");
                    int ownerId = sc.nextInt();
                    System.out.print("Site Type: ");
                    String type = sc.next();
                    System.out.print("Length: ");
                    int l = sc.nextInt();
                    System.out.print("Breadth: ");
                    int b = sc.nextInt();
                    OwnerOperations.addSite(ownerId, type, l, b);
                    break;

                case 3:
                    System.out.print("Owner ID: ");
                    OwnerOperations.viewMySites(sc.nextInt());
                    break;
            }

        } while (choice != 4);
    }
}
