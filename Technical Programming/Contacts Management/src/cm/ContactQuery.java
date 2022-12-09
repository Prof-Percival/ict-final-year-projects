package cm;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Christoph Percival
 */
public class ContactQuery {
    
    public void insertContact(Contact contact)
    {
        try 
        {
            Connection con = Database.getConnection();
            PreparedStatement ps; 
            ps = con.prepareStatement("INSERT INTO contact (FirstName, LastName, GroupCategory, Phone, Email, Address, Picture, UserId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getGroup());
            ps.setString(4, contact.getPhone());
            ps.setString(5, contact.getEmail());
            ps.setString(6, contact.getAddress());
            ps.setBytes(7, contact.getPicture());
            ps.setInt(8, contact.getUserId());

            //Returns number of Rows Affected, 0 if none
            if (ps.executeUpdate() != 0)
            {
                JOptionPane.showMessageDialog(null, "New Contact Added Successfully", "New Contact", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error While Adding New Contact", "New Contact", JOptionPane.ERROR_MESSAGE);
            }
            
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Adding New Contact", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateContact(Contact contact, boolean withImage)
    {
        try 
        {
            Connection con = Database.getConnection();
            PreparedStatement ps = null; 
            String query = "";
            
            //Check If User Wants To Update Details Along with The Image
            if (withImage)
            {
                query = "UPDATE contact SET FirstName = ?, LastName = ?, GroupCategory = ?, Phone = ?, Email = ?, Address = ?, Picture = ? WHERE id = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getLastName());
                ps.setString(3, contact.getGroup());
                ps.setString(4, contact.getPhone());
                ps.setString(5, contact.getEmail());
                ps.setString(6, contact.getAddress());
                ps.setBytes(7, contact.getPicture());
                ps.setInt(8, contact.getContactId());
            }
            else
            {
                query = "UPDATE contact SET FirstName = ?, LastName = ?, GroupCategory = ?, Phone = ?, Email = ?, Address = ? WHERE id = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getLastName());
                ps.setString(3, contact.getGroup());
                ps.setString(4, contact.getPhone());
                ps.setString(5, contact.getEmail());
                ps.setString(6, contact.getAddress());
                ps.setInt(7, contact.getContactId());
            }
            

            //Returns number of Rows Affected, 0 if none
            if (ps.executeUpdate() != 0)
            {
                JOptionPane.showMessageDialog(null, "Contact Updated Successfully", "Contact Update", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error While Adding New Contact", "Contact Update", JOptionPane.ERROR_MESSAGE);
            }
            
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Updating Contact", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteContact(int contactId)
    {
        try 
        {
            Connection con = Database.getConnection();
            PreparedStatement ps; 
            ps = con.prepareStatement("DELETE FROM contact WHERE id = ?");
            ps.setInt(1, contactId);

            //Returns number of Rows Affected, 0 if none
            if (ps.executeUpdate() != 0)
            {
                JOptionPane.showMessageDialog(null, "Contact Deleted Successfully", "Delete Contact", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error While Deleting Contact", "Delete Contact", JOptionPane.ERROR_MESSAGE);
            }
            
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Deleting Contact", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Create a List of Contacts
    public ArrayList<Contact> contactList(int userId)
    {
        var cList = new ArrayList<Contact>();
        
        Connection con = Database.getConnection();
        Statement statement;
        ResultSet resultSet;
        
        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM contact WHERE UserId = " + userId);
            
            Contact contact;
            
            while (resultSet.next())
            {
                contact = new Contact(resultSet.getInt("id"),
                                        resultSet.getString("FirstName"),
                                        resultSet.getString("LastName"),
                                        resultSet.getString("Phone"),
                                        resultSet.getString("Email"),
                                        resultSet.getString("GroupCategory"),
                                        resultSet.getString("Address"),
                                        resultSet.getBytes("Picture"),
                                        resultSet.getInt("UserId"));
                
                cList.add(contact);
            }
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(ContactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cList;
    }
    
    //Create a List of Contacts by Filter
    public ArrayList<Contact> contactListFilter(String query, int userId)
    {
        //Add User Id To Query
        query += " AND UserId = ?";
        
        var cList = new ArrayList<Contact>();
        
        Connection con = Database.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;
        
        try {
            statement = con.prepareStatement(query);
            statement.setString(1, Contacts.filterValue);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            
            Contact contact;
            
            while (resultSet.next())
            {
                contact = new Contact(resultSet.getInt("id"),
                                        resultSet.getString("FirstName"),
                                        resultSet.getString("LastName"),
                                        resultSet.getString("Phone"),
                                        resultSet.getString("Email"),
                                        resultSet.getString("GroupCategory"),
                                        resultSet.getString("Address"),
                                        resultSet.getBytes("Picture"),
                                        resultSet.getInt("UserId"));
                
                cList.add(contact);
            }
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(ContactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cList;
    }
}