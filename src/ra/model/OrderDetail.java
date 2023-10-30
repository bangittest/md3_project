package ra.model;

public class OrderDetail {
    private Long productId;
    private Long orderId;
    private String name;
    private double unitPrice;
    private int quantity;

    public OrderDetail(Long productId, Long orderId, String name, double unitPrice, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public OrderDetail() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productId=" + productId +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                '}';
    }
}