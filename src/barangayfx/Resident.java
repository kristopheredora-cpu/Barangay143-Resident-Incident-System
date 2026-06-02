package barangayfx;

public class Resident {
    private int    id;
    private String fullName;
    private String address;
    private String birthdate;
    private String contactNo;
    private String status;
    private String gender;
    private String civilStatus;
    private String occupation;
    private String email;
    private String emergencyContact;
    private String dateRegistered;

    public Resident(int id, String fullName, String address,
                    String birthdate, String contactNo, String status,
                    String gender, String civilStatus, String occupation,
                    String email, String emergencyContact) {
        this.id               = id;
        this.fullName         = fullName;
        this.address          = address;
        this.birthdate        = birthdate;
        this.contactNo        = contactNo;
        this.status           = status;
        this.gender           = gender != null ? gender : "Male";
        this.civilStatus      = civilStatus != null ? civilStatus : "Single";
        this.occupation       = occupation != null ? occupation : "";
        this.email            = email != null ? email : "";
        this.emergencyContact = emergencyContact != null ? emergencyContact : "";
        this.dateRegistered   = "";
    }

    public int    getId()               { return id; }
    public String getFullName()         { return fullName; }
    public String getAddress()          { return address; }
    public String getBirthdate()        { return birthdate; }
    public String getContactNo()        { return contactNo; }
    public String getStatus()           { return status; }
    public String getGender()           { return gender; }
    public String getCivilStatus()      { return civilStatus; }
    public String getOccupation()       { return occupation; }
    public String getEmail()            { return email; }
    public String getEmergencyContact() { return emergencyContact; }
    public String getDateRegistered()   { return dateRegistered; }

    public void setId(int v)                  { this.id               = v; }
    public void setFullName(String v)         { this.fullName         = v; }
    public void setAddress(String v)          { this.address          = v; }
    public void setBirthdate(String v)        { this.birthdate        = v; }
    public void setContactNo(String v)        { this.contactNo        = v; }
    public void setStatus(String v)           { this.status           = v; }
    public void setGender(String v)           { this.gender           = v; }
    public void setCivilStatus(String v)      { this.civilStatus      = v; }
    public void setOccupation(String v)       { this.occupation       = v; }
    public void setEmail(String v)            { this.email            = v; }
    public void setEmergencyContact(String v) { this.emergencyContact = v; }
    public void setDateRegistered(String v)   { this.dateRegistered   = v; }
}