package ra.service;

import ra.config.Config;
import ra.model.order.Order;
import ra.reponsitory.OderReponsitory;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements OderReponsitory {
    static Config<List<Order>> config = new Config<>();
    public static List<Order> orderList;

    static {
        orderList = config.readFile(Config.URL_ORDER);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
    }

    @Override
    public List<Order> findAll() {
        return orderList;
    }

    @Override
    public void save(Order order) {
//        orderList.add(order);
//        config.writeFile(Config.URL_ORDER, findAll());
//        updateData();
        if (order != null) {
            Order existingOrder = findById(order.getOrderId());
            if (existingOrder == null) {
                // Nếu đơn hàng chưa tồn tại, thêm nó vào danh sách
                orderList.add(order);
            } else {
                // Nếu đơn hàng đã tồn tại, cập nhật thông tin của nó
                int index = orderList.indexOf(existingOrder);
                orderList.set(index, order);
            }

            // Lưu trạng thái mới của danh sách
            updateData();
        }
    }


    @Override
    public void delete(int id) {
        orderList.remove(findById(id));
        updateData();
    }
//    public void update(Order order) {
//        Order orderToUpdate = findById(order.getOrderId());
//        if (orderToUpdate != null) {
//            orderToUpdate.setName(order.getName());
//            orderToUpdate.setPhoneNumber(order.getPhoneNumber());
//            orderToUpdate.setAddress(order.getAddress());
//            orderToUpdate.setTotal(order.getTotal());
//            orderToUpdate.setOrderTime(order.getOrderTime());
//            updateData();
//        } else {
//            System.out.println("Không tìm thấy đơn hàng có ID: " + order.getOrderId());
//        }
//    }

    @Override
    public Order findById(int id) {
        for (Order order : orderList) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax=0;
        for (Order order : orderList) {
            if (order.getOrderId()>idMax){
                idMax=order.getOrderId();
            }
        }
        return idMax+1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ORDER, orderList);
    }


}
