package ra.model;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static ra.config.Color.RESET;

public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String productName;
    private Catalogs categoryId;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status=true;

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
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String format = "%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n";
        System.out.println();
        return String.format(format, id, productName, description, String.format(currencyFormat.format(unitPrice)), stock, categoryId.getCatalogName(), (status ? "Mở bán" : "Không mở bán"));
    }
    public String toShortString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        return String.format("%-10s %-20s %-20s %-20s %-20s", id, productName, categoryId.getCatalogName(), description, String.format(currencyFormat.format(unitPrice)));
    }

}
