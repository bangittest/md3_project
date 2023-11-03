package ra.view.admin;

import ra.config.Validate;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.order.Order;
import ra.model.order.OrderStatus;
import ra.reponsitory.OderReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.service.OrderService;
import ra.service.ProductService;

import java.util.List;

import static ra.config.Color.RED;
import static ra.config.Color.RESET;

public class OrderManagement {
    ProductReponsitory productReponsitory=new ProductService();
    OderReponsitory oderReponsitory=new OrderService();
    public void menuOrderManagement() {
        do {
            System.out.println("\u001B[35m╔════════════════════════════  ORDER MANAGEMENT  ════════════════════════════╗");
            System.out.println("\u001B[36m║                        1. Hiển thị đơn hàng                                ║");
            System.out.println("\u001B[36m║                        2. Sửa trạng thái đơn hàng                                    ║");
            System.out.println("\u001B[31m║                        0. Quay lại                                         ║");
            System.out.println("\u001B[33m╚════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("\u001B[33mMời lựa chọn (0/1/2/3/4/5/6/): ");


            System.out.println(RESET);
            switch (Validate.validateInt()) {
                case 1:
                    showOrder();
                    break;
                case 2:
                    status();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void status() {
        showOrder();
        System.out.println("Nhập id muốn sửa trạng thái");
        int idStatusOrder = Validate.validateInt();
        Order orderList = oderReponsitory.findById(idStatusOrder);
        if (orderList == null) {
            System.out.println("Nhập id không đúng hoặc id đó không tồn tại");
        } else {
            if (orderList.getOrderStatus() == OrderStatus.PENDING) {
                System.out.println("1. Xác nhận đơn hàng");
                System.out.println("2. Hủy đơn hàng");
                switch (Validate.validateInt()) {
                    case 2:
                        // Lưu trạng thái hiện tại của đơn hàng trước khi hủy
                        OrderStatus currentStatus = orderList.getOrderStatus();
                        // Cập nhật trạng thái đơn hàng thành CANCELED
                        orderList.setOrderStatus(OrderStatus.CANCELED);
                        oderReponsitory.save(orderList);
                        System.out.println("Đã hủy đơn hàng thành công");


                        // Nếu đơn hàng đã ở trạng thái PENDING, thì cập nhật số lượng tồn kho
                        if (currentStatus == OrderStatus.PENDING) {
                            // Duyệt qua các sản phẩm trong đơn hàng để cộng số lượng đã hủy vào số lượng tồn kho
                            for (OrdersDetail orderDetail : orderList.getOrdersDetails()) {
                                Products product = productReponsitory.findById(orderDetail.getProductId());
                                int quantityToCancel = orderDetail.getQuantity();
                                int currentStock = product.getStock();
                                product.setStock(currentStock + quantityToCancel);
                                // Cập nhật số lượng tồn kho của sản phẩm trong cơ sở dữ liệu
                                productReponsitory.updateStock(product.getId(), currentStock + quantityToCancel);
                            }
                        }
                        break;
                    case 1:
                        orderList.setOrderStatus(OrderStatus.PROCESSING);
                        oderReponsitory.save(orderList);
                        System.out.println("Xác nhận đơn hàng thành công");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");

                }
            } else if (orderList.getOrderStatus() == OrderStatus.PROCESSING || orderList.getOrderStatus() == OrderStatus.CANCELED) {
                System.out.println("Không thể thay đổi trạng thái đơn hàng đã xác nhận hoặc đã hủy.");
            } else {
                System.out.println("Trạng thái đơn hàng không hợp lệ.");
            }
        }
    }


    private void showOrder() {
        System.out.println("Tất cả các đơn hàng:");
        List<Order> orderList = oderReponsitory.findAll();

        if (orderList.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
        } else {
            for (Order order : orderList) {
                System.out.println("Đơn hàng (ID: " + order.getOrderId() + "):");
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
            }
        }

    }
}

