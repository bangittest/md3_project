package ra.model.order;


import ra.model.OrdersDetail;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private String name;
    private String phoneNumber;
    private List<OrdersDetail>ordersDetails=new ArrayList<>();
    private String address;

    private double total;
    private OrderStatus orderStatus=OrderStatus.PENDING;

    public Order(int orderId, int userId, String name, String phoneNumber, List<OrdersDetail> ordersDetails, String address, double total, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ordersDetails = ordersDetails;
        this.address = address;
        this.total = total;
        this.orderStatus = orderStatus;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public List<OrdersDetail> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(List<OrdersDetail> ordersDetails) {
        this.ordersDetails = ordersDetails;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", ordersDetails=" + ordersDetails +
                ", address='" + address + '\'' +
                ", total=" + total +
                ", orderStatus=" + orderStatus +
                '}';
    }
    //    @Override
//    public String toString() {
//        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        String format = "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s";
//        System.out.println();
//        return String.format(format, orderId, userId, productId, productName, String.format(currencyFormat.format(total)),name,phoneNumber,quantity,address,orderStatus);
//    }
}