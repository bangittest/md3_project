package ra.view.cart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.account.Users;
import ra.model.order.Order;
import ra.model.order.OrderStatus;
import ra.reponsitory.OderReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.reponsitory.UserReponsitory;
import ra.service.OrderService;
import ra.service.ProductService;
import ra.service.UserService;

import java.util.List;

public class historyOrder {
    ProductReponsitory productReponsitory = new ProductService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    UserReponsitory userReponsitory = new UserService();
    int cartId = userReponsitory.findById(users.getId()).getId();
    OderReponsitory oderReponsitory = new OrderService();

    public void history() {
        Order order = oderReponsitory.findById(cartId);
        if (oderReponsitory.findAll().isEmpty()) {
            System.out.println("Không có đơn hàng trong lịch sử.");
            return;
        }
        if (order.getOrderStatus() == OrderStatus.PENDING) {
            System.out.println("Đơn hàng  " + order.getOrderId() + ":");
            System.out.println("Tên khách hàng: " + order.getName());
            System.out.println("Điện thoại: " + order.getPhoneNumber());
            System.out.println("Địa chỉ: " + order.getAddress());

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
            System.out.println();

            System.out.println("1. Hủy đơn hàng");
            System.out.println("2. Thoát");

            switch (Validate.validateInt()) {
                case 1:
                    // Lưu trạng thái hiện tại của đơn hàng trước khi hủy
                    OrderStatus currentStatus = order.getOrderStatus();
                    // Cập nhật trạng thái đơn hàng thành CANCELED
                    order.setOrderStatus(OrderStatus.CANCELED);
                    oderReponsitory.save(order);
                    System.out.println("Đã hủy đơn hàng thành công");
                    System.out.println(order);

                    // Nếu đơn hàng đã ở trạng thái PENDING, thì cập nhật số lượng tồn kho
                    if (currentStatus == OrderStatus.PENDING) {
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

        } else if (order.getOrderStatus() == OrderStatus.PROCESSING ) {
            System.out.println("Đã xác nhận đơn hàng");
            System.out.println("Đơn hàng  " + order.getOrderId() + ":");
            System.out.println("Tên khách hàng: " + order.getName());
            System.out.println("Điện thoại: " + order.getPhoneNumber());
            System.out.println("Địa chỉ: " + order.getAddress());

            System.out.println("Chi tiết đơn hàng:");
            List<OrdersDetail> orderDetails = order.getOrdersDetails();

            for (OrdersDetail orderDetail : orderDetails) {
                Products product = productReponsitory.findById(orderDetail.getProductId());

                System.out.println("Sản phẩm: " + product.getProductName());
                System.out.println("Số lượng: " + orderDetail.getQuantity());
                System.out.println("Giá tiền: " + Validate.formatCurrency(orderDetail.getUnitPrice()));
                System.out.println("Tổng tiền đơn hàng: " + Validate.formatCurrency(order.getTotal()));
                System.out.println("Trạng thái đơn hàng: " + order.getOrderStatus());
            }
        } else if(order.getOrderStatus() == OrderStatus.CANCELED) {
            System.out.println("Đơn hàng đã bị hủy");
        }else {
            System.out.println("Trạng thái đơn hàng không hợp lệ.");
        }

    }

}
