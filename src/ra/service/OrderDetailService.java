package ra.service;

import ra.config.Config;
import ra.model.OrdersDetail;
import ra.reponsitory.OrderDetailReponsitory;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailService implements OrderDetailReponsitory {
    static Config<List<OrdersDetail>>config=new Config<>();
    static List<OrdersDetail>ordersDetailList;
    static {
        ordersDetailList=config.readFile(Config.URL_ORDERSDETAIL);
        if (ordersDetailList==null){
            ordersDetailList=new ArrayList<>();
        }
    }
    @Override
    public List<OrdersDetail> findAll() {
        return ordersDetailList;
    }

    @Override
    public void save(OrdersDetail ordersDetail) {
        OrdersDetail existingOrder = findById(ordersDetail.getProductId());
        if (existingOrder == null) {
            ordersDetailList.add(ordersDetail);
            updateData();
        } else {
            int index = ordersDetailList.indexOf(existingOrder);
            ordersDetailList.set(index, ordersDetail);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        ordersDetailList.remove(findById(id));
    }

    @Override
    public OrdersDetail findById(int id) {
        for (OrdersDetail order:
             ordersDetailList) {
            if (order.getProductId()==id){
                return order;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax=0;
        for (OrdersDetail order:ordersDetailList) {
            if (order.getProductId()>idMax){
                idMax=order.getProductId();
            }
        }
        return idMax+1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ORDERSDETAIL,ordersDetailList);
    }
}
