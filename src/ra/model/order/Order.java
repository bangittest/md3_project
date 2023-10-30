package ra.model.order;

import ra.model.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long orderId;
    private Long userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus;
    private List<OrderDetail> ordersDetails;
    private LocalDateTime orderAt;
    private LocalDateTime deliverAt;

    public Order(Long orderId, Long userId, String name, String phoneNumber, String address, double total, OrderStatus orderStatus, List<OrderDetail> ordersDetails, LocalDateTime orderAt, LocalDateTime deliverAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.orderStatus = orderStatus;
        this.ordersDetails = ordersDetails;
        this.orderAt = orderAt;
        this.deliverAt = deliverAt;
    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetail> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(List<OrderDetail> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public LocalDateTime getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(LocalDateTime deliverAt) {
        this.deliverAt = deliverAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", total=" + total +
                ", orderStatus=" + orderStatus +
                ", ordersDetails=" + ordersDetails +
                ", orderAt=" + orderAt +
                ", deliverAt=" + deliverAt +
                '}';
    }
}