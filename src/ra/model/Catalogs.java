package ra.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Catalogs implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String catalogName;
    private String description;
    private boolean status=true;

    public Catalogs(int id, String catalogName, String description, boolean status) {
        this.id = id;
        this.catalogName = catalogName;
        this.description = description;
        this.status = status;
    }

    public Catalogs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Override
    public String toString() {
        String format = "%-10s %-20s %-20s %-20s%n";
        System.out.println();
        return String.format(format, id, catalogName, description, (status ? "Đang hoạt động" : "Không hoạt động"));
    }

}
