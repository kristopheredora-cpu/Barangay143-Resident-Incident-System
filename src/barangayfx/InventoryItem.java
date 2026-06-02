package barangayfx;

public class InventoryItem {

    private int    id;
    private String itemName;
    private int    quantity;
    private String stockStatus;
    private String location;
    private String lastUpdated;
    private String notes;

    public InventoryItem(int id, String itemName, int quantity,
                         String stockStatus, String location,
                         String lastUpdated, String notes) {
        this.id          = id;
        this.itemName    = itemName;
        this.quantity    = quantity;
        this.stockStatus = stockStatus;
        this.location    = location;
        this.lastUpdated = lastUpdated;
        this.notes       = notes;
    }

    public int    getId()          { return id; }
    public String getItemName()    { return itemName; }
    public int    getQuantity()    { return quantity; }
    public String getStockStatus() { return stockStatus; }
    public String getLocation()    { return location; }
    public String getLastUpdated() { return lastUpdated; }
    public String getNotes()       { return notes; }

    public void setId(int v)            { this.id          = v; }
    public void setItemName(String v)   { this.itemName    = v; }
    public void setQuantity(int v)      { this.quantity    = v; }
    public void setStockStatus(String v){ this.stockStatus = v; }
    public void setLocation(String v)   { this.location    = v; }
    public void setLastUpdated(String v){ this.lastUpdated = v; }
    public void setNotes(String v)      { this.notes       = v; }
}