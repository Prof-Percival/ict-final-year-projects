package cm;
/**
 *
 * @author Christoph Percival
 */
import java.sql.DriverManager;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Database {
    
    public static Connection getConnection()
    {
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/contactsmanagement", "root", "");
            
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Database Connection", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        
        return con;
    }
    
}