package ra.model;


import java.io.Serializable;

public class Products implements Serializable {
    private int id;
    private String productName;
    private Catalogs categoryId;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status;

    public Products() {
    }

    public Products(int id, String productName, Catalogs categoryId, String description, double unitPrice, int stock, boolean status) {
        this.id = id;
        this.productName = productName;
        this.categoryId = categoryId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Catalogs getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Catalogs categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
        builder.append("================================ Products ================================\n");
        builder.append(String.format("| %-15s: %d\n", "ID", id));
        builder.append(String.format("| %-15s: %s\n", "Product Name", productName));
        builder.append(String.format("| %-15s: %s\n", "Category", categoryId.getCatalogName()));
        builder.append(String.format("| %-15s: %s\n", "Description", description));
        builder.append(String.format("| %-15s: %f\n", "Unit Price", unitPrice));
        builder.append(String.format("| %-15s: %d\n", "Stock", stock));
        builder.append(String.format("| %-15s: %s\n", "Status", statusText));
        builder.append("===========================================================================");

        return builder.toString();
    }

}
