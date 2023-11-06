package ra.view.shoppingcart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.account.Users;
import ra.model.order.Order;
import ra.model.order.OrderStatus;
import ra.reponsitory.*;
import ra.service.*;

import java.util.List;

import static ra.config.Color.*;
import static ra.config.Color.RESET;

public class History {
    ProductReponsitory productReponsitory = new ProductService();

    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    OderReponsitory oderReponsitory = new OrderService();


    public void history() {
        List<Order> orders = oderReponsitory.findAll();
        if (orders == null || orders.isEmpty()) {
            System.out.println(YELLOW + "Không có đơn hàng nào" + RESET);
        }
        for (Order order : orders) {
            if (order.getUserId() == users.getId()) {
               bill(order);
            }
        }

        System.out.print("Mời nhập ID để chọn đơn hàng cần huỷ(chọn 0 để thoát): ");
        int orderId = Validate.validateInt();
        if (orderId==0){
            return;
        }
        Order order = oderReponsitory.findById(orderId);
        if (order == null) {
            System.out.println(RED + "Không tồn tại trong danh sách" + RESET);
            return;
        }

        if (order.getOrderStatus() == OrderStatus.WAITING) {
            System.out.println("1. Huỷ đơn hàng");
            System.out.println("O. Quay lại");
            int choiceCheck = Validate.validateInt();
            switch (choiceCheck) {
                case 1:
                    // Lưu trạng thái hiện tại của đơn hàng trước khi hủy
                    OrderStatus currentStatus = order.getOrderStatus();
                    // Cập nhật trạng thái đơn hàng thành CANCELED
                    order.setOrderStatus(OrderStatus.CANCEL);
                    oderReponsitory.save(order);
                    System.out.println("Đã hủy đơn hàng thành công");
                    System.out.println(order);

                    // Nếu đơn hàng đã ở trạng thái WAITING, thì cập nhật số lượng tồn kho
                    if (currentStatus == OrderStatus.WAITING) {
                        // Duyệt qua các sản phẩm trong đơn hàng để cộng số lượng đã hủy vào số lượng tồn kho
                        for (OrdersDetail orderDetail : order.getOrdersDetails()) {
                            Products product = productReponsitory.findById(orderDetail.getProductId());
                            int quantityToCancel = orderDetail.getQuantity();
                            int currentStock = product.getStock();
                            product.setStock(currentStock + quantityToCancel);
                            // Cập nhật số lượng tồn kho của sản phẩm trong cơ sở dữ liệu
                            productReponsitory.updateStock(product.getId(), currentStock + quantityToCancel);
                        }
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
            }
        } else if (order.getOrderStatus() == OrderStatus.CONFIRM) {
            System.out.println("Đơn hàng đã được xác nhận");
            bill(order);
        } else if (order.getOrderStatus() == OrderStatus.CANCEL) {
            System.out.println("Đơn hàng đã bị hủy.");
            bill(order);
        } else if (order.getOrderStatus() == OrderStatus.DELIVERY) {
            System.out.println("Đơn hàng đang vận chuyển");
            bill(order);
        } else {
            System.out.println("Trạng thái đơn hàng không hợp lệ.");
        }
    }

    public void bill(Order order){
        System.out.println("Đơn hàng:"  +order.getOrderId());
        System.out.println("Tên khách hàng: " + order.getName());
        System.out.println("Điện thoại: " + order.getPhoneNumber());
        System.out.println("Địa chỉ: " + order.getAddress());
        System.out.println("Thời gian đặt hàng: " + order.getOrderTime());

        System.out.println("Chi tiết đơn hàng:");
        List<OrdersDetail> orderDetails = order.getOrdersDetails();

        for (OrdersDetail orderDetail : orderDetails) {
            Products product = productReponsitory.findById(orderDetail.getProductId());

            System.out.println("Sản phẩm: " + product.getProductName());
            System.out.println("Số lượng: " + orderDetail.getQuantity());
            System.out.println("Giá tiền: " + Validate.formatCurrency(orderDetail.getUnitPrice()));
        }

        System.out.println("Tổng tiền đơn hàng: " + Validate.formatCurrency(order.getTotal()));
        System.out.println("Trạng thái đơn hàng: " + order.getOrderStatus());
        System.out.println("--------------------------------------------------------------------------------");
    }


}
