package ra.model.order;


import ra.model.OrdersDetail;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private OrderStatus orderStatus=OrderStatus.WAITING;
    private LocalDateTime orderTime;



    public Order() {
        this.orderTime = LocalDateTime.now();
    }
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
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
        return "" +
                "\nĐơn hàng=" + orderId +
//                ", \nTên khách hàng='" + name + '\'' +
//                ", \nĐiện thoại='" + phoneNumber + '\'' +
//                ", \nĐịa chỉ='" + address + '\'' +
//                ", \nTổng tiền đơn hàng=" + total +
//                ", \nTrạng thái đơn hàng=" + orderStatus +
//                ", \nThời gian đặt hàng=" +orderTime +
                "";
    }

}