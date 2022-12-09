package cm;

/**
 *
 * @author Christoph Percival
 */

public class Contact {
    
    private int contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String group;
    private String address;
    private byte[] picture;
    private int userId;
    

    //Default Constructor
    public Contact() 
    {
        contactId = 0;
        firstName = "";
        lastName = "";
        phone = "";
        email = "";
        group = "";
        address = "";
        picture = null;
        userId = 0;
    }
    
    //Parametarized Constructor
    public Contact(int contactId, String firstName, String lastName, String phone, String email, String group, String address, byte[] picture, int userId) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.group = group;
        this.address = address;
        this.picture = picture;
        this.userId = userId;
    }
    
    //Setters
    public void setContactId(int contactId) 
    {
        this.contactId = contactId;
    }
    
    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setGroup(String group) 
    {
        this.group = group;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }
    
    public void setPicture(byte[] picture) 
    {
        this.picture = picture;
    }

    public void setUserId(int userId) 
    {
        this.userId = userId;
    }
    
    //Getters
    public int getContactId()
    {
        return contactId;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public String getPhone() 
    {
        return phone;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getGroup() 
    {
        return group;
    }
    
    public String getAddress() 
    {
        return address;
    }
    
    public byte[] getPicture() 
    {
        return picture;
    }
    
    public int getUserId() 
    {
        return userId;
    }    

}