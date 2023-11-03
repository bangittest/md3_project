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
        Order existingOrder = findById(order.getOrderId());
        if (existingOrder == null) {
            orderList.add(order);
            updateData();
        } else {
            int index = orderList.indexOf(existingOrder);
            orderList.set(index, order);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        orderList.remove(findById(id));
        updateData();
    }

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
