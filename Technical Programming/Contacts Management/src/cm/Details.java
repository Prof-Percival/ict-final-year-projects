package cm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Christoph Percival
 */
public class Details {
    
    private static final String UPPER_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_STRING = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String CHARACTERS = "@-.";
    
    
    //Method for Validating Name (First Name & Last Name)
    public static boolean nameIsValid(JTextField textBox, String type)
    {
        String name = textBox.getText();
        
        if (!name.equals(""))
        {
            if (!name.contains(" "))
            {
                if (name.length() < 2)
                {
                    JOptionPane.showMessageDialog(null, type + " Must Be At Least 2 Characters", type, JOptionPane.ERROR_MESSAGE);
                    textBox.requestFocusInWindow();
                    return false;
                }
                
                for (int i = 0; i < name.length(); i++)
                {
                    if (!(UPPER_STRING.contains(name.substring(i, i + 1)) || (LOWER_STRING.contains(name.substring(i, i + 1))) || name.substring(i, i + 1).equals("-")))
                    {
                        JOptionPane.showMessageDialog(null, type + " Must Be Alphabetic", type, JOptionPane.ERROR_MESSAGE);
                        textBox.requestFocusInWindow();
                        return false;
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, type + " Cannot Contain Space Character", type, JOptionPane.ERROR_MESSAGE);
                textBox.requestFocusInWindow();
                return false;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Input " + type, type, JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }

        return true;
    }
    
    
    //Method for Validating Phone Number
    public static boolean phoneIsValid(JTextField textBox, String phone)
    {
        if (phone.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Input Phone Number", "Phone", JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }

        for (int i = 0; i < phone.length(); i++)
        {
            if (!NUMBERS.contains(phone.substring(i, i + 1)))
            {
                JOptionPane.showMessageDialog(null, "Phone Number Must Be Numeric", "Phone", JOptionPane.ERROR_MESSAGE);
                textBox.requestFocusInWindow();
                return false;
            }
        }

        if (!phone.startsWith("0"))
        {
            JOptionPane.showMessageDialog(null, "Phone Number Must Begin With 0", "Phone", JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }

        if (phone.length() != 10)
        {
            JOptionPane.showMessageDialog(null, "Phone Number Must Be 10 Digits", "Phone", JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }

        return true;
        
    }
    
    //Picture Validation
    public static boolean imageAttached(String path)
    {
        if (path == null)
        {
            JOptionPane.showMessageDialog(null, "Attach Profile Picture", "Picture", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    //Email Validation
    public static boolean emailIsValid(JTextField textBox, String email)
    {
        if (email.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Input Email Address", "Email Address", JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }

        if (!email.contains("@"))
        {
            JOptionPane.showMessageDialog(null, "Invalid Email Address", "Email Address", JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }

        if (email.contains(" "))
        {
            JOptionPane.showMessageDialog(null, "Email Address Must Not Contain Space Character", "Email Address", JOptionPane.ERROR_MESSAGE);
            textBox.requestFocusInWindow();
            return false;
        }
        
        for (int i = 0; i < email.length(); i++)
        {
            if (!(UPPER_STRING.contains(email.substring(i, i + 1)) || (LOWER_STRING.contains(email.substring(i, i + 1))) || (NUMBERS.contains(email.substring(i, i + 1))) || (CHARACTERS.contains(email.substring(i, i + 1)))))
            {
                JOptionPane.showMessageDialog(null, "Email Address Cannot Contain \"" + email.substring(i, i + 1) + "\" Character", "Email Address", JOptionPane.ERROR_MESSAGE);
                textBox.requestFocusInWindow();
                return false;
            }
        }

        return true;
    }
    
    //Address Validation
    public static boolean addressValid(JTextArea textArea)
    {
        String address = textArea.getText();
        
        if (address.equals("")){
            JOptionPane.showMessageDialog(null, "Input Address", "Address", JOptionPane.ERROR_MESSAGE);
            textArea.requestFocusInWindow();
            return false;
        }
        
        if (address.length() < 15)
        {
            JOptionPane.showMessageDialog(null, "Invalid Address", "Address", JOptionPane.ERROR_MESSAGE);
            textArea.requestFocusInWindow();
            return false;
        }
        return true;
    }
    
    //Email Address Exists Validation
    public static boolean emailExists(String email)
    {
        try
        {
            Connection con = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            
            ps = con.prepareStatement("SELECT id, Email FROM contact WHERE Email LIKE ?");
            ps.setString(1, email);
            
            rs = ps.executeQuery();
            
            if (rs.next())
            {
                if (Contacts.id != rs.getInt("id"))
                {
                    JOptionPane.showMessageDialog(null, "Email Address [ " + email+" ] Already Exists", "Email Address", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Searching Email Address", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
            return true;
        }
        return false;
    }
    
    //Phone Already Exixts Validation
    public static boolean phoneExists(String phone)
    {
        try
        {
            Connection con = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            
            ps = con.prepareStatement("SELECT id, Phone FROM contact WHERE Phone LIKE ?");
            ps.setString(1, phone);
            
            rs = ps.executeQuery();
            
            if (rs.next())
            {
                if (Contacts.id != rs.getInt("id"))
                {
                    JOptionPane.showMessageDialog(null, "Phone [ " + phone +" ] Already Exists", "Phone", JOptionPane.ERROR_MESSAGE);
                    return true;
                }
            }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Searching Phone", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
            return true;
        }
        return false;
    }
}
