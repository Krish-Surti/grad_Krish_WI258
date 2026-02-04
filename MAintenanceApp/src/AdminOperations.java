import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminOperations {

    public static void viewAllOwners() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs =
              con.prepareStatement("SELECT * FROM owner").executeQuery();

            System.out.println("\n--- Owners ---");
            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("owner_id") +
                    ", Name: " + rs.getString("first_name") + " " +
                    rs.getString("last_name") +
                    ", Phone: " + rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewSites(boolean pendingOnly) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = pendingOnly
                ? "SELECT * FROM site WHERE maintenance_paid=false"
                : "SELECT * FROM site";

            ResultSet rs = con.prepareStatement(sql).executeQuery();

            System.out.println("\n--- Sites ---");
            while (rs.next()) {
                System.out.println(
                    "Site ID: " + rs.getInt("site_id") +
                    ", Owner ID: " + rs.getInt("owner_id") +
                    ", Type: " + rs.getString("site_type") +
                    ", Paid: " + rs.getBoolean("maintenance_paid")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void collectMaintenance(int siteId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
              con.prepareStatement("UPDATE site SET maintenance_paid=true WHERE site_id=?");
            ps.setInt(1, siteId);

            if (ps.executeUpdate() > 0)
                System.out.println("Maintenance collected successfully");
            else
                System.out.println("Site not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
