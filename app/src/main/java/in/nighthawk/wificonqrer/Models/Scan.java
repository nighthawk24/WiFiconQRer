package in.nighthawk.wificonqrer.Models;

/**
 * Created by adi on 4/5/16.
 */
public class Scan {

    private long id;
    private String scan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return scan;
    }

}
