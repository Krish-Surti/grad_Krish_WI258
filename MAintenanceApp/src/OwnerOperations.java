import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OwnerOperations {

    // REGISTER OWNER (IMMEDIATE OWNER ID)
    public static void addOwner(String firstName, String lastName, String phone) {

        if (!phone.matches("\\d+")) {
            System.out.println("Invalid input: phone must contain only digits");
            return;
        }

        if (phone.length() != 10) {
            System.out.println("Phone number must be exactly 10 digits");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            // Check duplicate
            String checkSql =
              "SELECT owner_id FROM owner WHERE first_name=? AND last_name=? AND phone=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, firstName);
            checkPs.setString(2, lastName);
            checkPs.setString(3, phone);

            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                System.out.println("Owner already registered");
                System.out.println("Your Owner ID is: " + rs.getInt("owner_id"));
                return;
            }

            // Insert owner
            String insertSql =
              "INSERT INTO owner(first_name,last_name,phone) VALUES (?,?,?) RETURNING owner_id";
            PreparedStatement ps = con.prepareStatement(insertSql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phone);

            ResultSet result = ps.executeQuery();
            result.next();

            int ownerId = result.getInt("owner_id");

            System.out.println("Owner registered successfully");
            System.out.println("Your Owner ID is: " + ownerId);
            System.out.println("Use this Owner ID while adding site");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OWNER ADDS SITE USING OWNER ID
    public static void addSite(int ownerId, String type, int length, int breadth) {

        try {
            Connection con = DBConnection.getConnection();

            // Check owner exists
            PreparedStatement check =
              con.prepareStatement("SELECT * FROM owner WHERE owner_id=?");
            check.setInt(1, ownerId);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid Owner ID");
                return;
            }

            int area = length * breadth;
            int rate = type.equalsIgnoreCase("OPEN") ? 6 : 9;
            int amount = area * rate;
            String status = rate == 6 ? "OPEN" : "OCCUPIED";

            PreparedStatement ps = con.prepareStatement(
              "INSERT INTO site(site_type,length,breadth,status,owner_id) VALUES (?,?,?,?,?)");
            ps.setString(1, type);
            ps.setInt(2, length);
            ps.setInt(3, breadth);
            ps.setString(4, status);
            ps.setInt(5, ownerId);
            ps.executeUpdate();

            System.out.println("Site added successfully");
            System.out.println("Area: " + area + " sqft");
            System.out.println("Maintenance Amount: ₹" + amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OWNER VIEWS THEIR SITES
    public static void viewMySites(int ownerId) {

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
              con.prepareStatement("SELECT * FROM site WHERE owner_id=?");
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Your Sites ---");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "Site ID: " + rs.getInt("site_id") +
                    ", Type: " + rs.getString("site_type") +
                    ", Status: " + rs.getString("status") +
                    ", Maintenance Paid: " + rs.getBoolean("maintenance_paid")
                );
            }

            if (!found) {
                System.out.println("No sites found under this owner ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
