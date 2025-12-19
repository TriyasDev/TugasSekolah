package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DBkoneksi {
    private static Connection connection;
    
    // Database configuration - sesuaikan dengan MySQL di Arch Linux
    private static final String URL = "jdbc:mysql://localhost:3306/DBpoliklinik?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "DBtrizas"; // Ganti dengan password MySQL Anda
    
    private static final Logger logger = Logger.getLogger(DBkoneksi.class.getName());
    
    static {
        testDrivers();
    }
    
    private static void testDrivers() {
        logger.info("Testing available JDBC drivers...");
        
        String[] drivers = {
            "com.mysql.cj.jdbc.Driver",      // MySQL 8.x
            "com.mysql.jdbc.Driver",         // MySQL 5.x
            "org.mariadb.jdbc.Driver"        // MariaDB
        };
        
        boolean driverFound = false;
        for (String driver : drivers) {
            try {
                Class.forName(driver);
                logger.info("✓ Driver found: " + driver);
                driverFound = true;
                break;
            } catch (ClassNotFoundException e) {
                logger.info("✗ Driver not found: " + driver);
            }
        }
        
        if (!driverFound) {
            showDriverError();
        }
    }
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Coba driver MySQL 8.x dulu
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    // Fallback ke driver lama
                    Class.forName("com.mysql.jdbc.Driver");
                }
                
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                logger.info("✅ Database Connection Established Successfully");
            }
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "❌ MySQL JDBC Driver Not Found", ex);
            showDriverError();
            return null;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "❌ Database Connection Failed", ex);
            showConnectionError(ex.getMessage());
            return null;
        }
        return connection;
    }
    
    private static void showDriverError() {
        String message = """
            MySQL JDBC Driver tidak ditemukan di Arch Linux!
            
            SOLUSI:
            1. Install package: sudo pacman -S mysql-connector-j
            2. File JAR akan tersedia di: /usr/share/java/mysql-connector-java.jar
            3. Tambahkan JAR tersebut ke project NetBeans:
               - Klik kanan project -> Properties -> Libraries
               - Add JAR/Folder -> pilih file JAR
            4. Clean and Build project
            
            Atau download manual dari:
            https://dev.mysql.com/downloads/connector/j/
            """;
        
        JOptionPane.showMessageDialog(null, message, "Driver Error - Arch Linux", JOptionPane.ERROR_MESSAGE);
    }
    
    private static void showConnectionError(String errorDetail) {
        String message = """
            Gagal terhubung ke MySQL Database!
            
            Pastikan:
            1. MySQL service berjalan: sudo systemctl start mysqld
            2. Database 'DBpoliklinik' sudah dibuat
            3. Username dan password benar
            4. Port MySQL (3306) terbuka
            
            Error Detail: """ + errorDetail;
        
        JOptionPane.showMessageDialog(null, message, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error closing connection", ex);
        }
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }
}