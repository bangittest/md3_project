package ra.model;

import java.io.Serializable;

public class Catalogs implements Serializable {
    private int id;
    private String catalogName;
    private String description;
    private boolean status;

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
        String statusText = status ? "Đang hoạt động" : "Không hoạt động";

        StringBuilder builder = new StringBuilder();
        builder.append("================================ Catalog ================================\n");
        builder.append(String.format("| %-15s: %d\n", "Catalog ID", id));
        builder.append(String.format("| %-15s: %s\n", "Tên danh mục", catalogName));
        builder.append(String.format("| %-15s: %s\n", "Mô tả", description));
        builder.append(String.format("| %-15s: %s\n", "Trạng thái", statusText));
        builder.append("===========================================================================");

        return builder.toString();
    }

}
