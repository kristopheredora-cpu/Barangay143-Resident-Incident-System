package barangayfx;

public class Incident {

    private int    id;
    private String description;
    private String location;
    private String reportedBy;
    private String status;

    public Incident(int id, String description, String location,
                    String reportedBy, String status) {
        this.id          = id;
        this.description = description;
        this.location    = location;
        this.reportedBy  = reportedBy;
        this.status      = status;
    }

    public int    getId()            { return id; }
    public String getDescription()   { return description; }
    public String getLocation()      { return location; }
    public String getReportedBy()    { return reportedBy; }
    public String getStatus()        { return status; }

    public void setId(int v)             { this.id          = v; }
    public void setDescription(String v) { this.description = v; }
    public void setLocation(String v)    { this.location    = v; }
    public void setReportedBy(String v)  { this.reportedBy  = v; }
    public void setStatus(String v)      { this.status      = v; }
}