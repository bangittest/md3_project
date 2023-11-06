package ra.reponsitory;



import ra.model.order.Order;

public interface OderReponsitory extends Responsitory<Order>{
    void update(Order order);
}
