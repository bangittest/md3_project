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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Collections.*;
import static ra.config.Color.*;

public class OrderManagement {
    ProductReponsitory productReponsitory = new ProductService();
    OderReponsitory oderReponsitory = new OrderService();

    public void menuOrderManagement() {
        do {
            System.out.println("\u001B[35m╔════════════════════════════  ORDER MANAGEMENT  ════════════════════════════╗");
            System.out.println("\u001B[36m║                        1. Hiển thị đơn hàng                                ║");
            System.out.println("\u001B[36m║                        2. Sửa trạng thái đơn hàng                          ║");
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
            if (orderList.getOrderStatus() == OrderStatus.WAITING) {
                System.out.println("1. Xác nhận đơn hàng");
                System.out.println("2. Hủy đơn hàng");
                switch (Validate.validateInt()) {
                    case 1:
                        orderList.setOrderStatus(OrderStatus.CONFIRM);
                        oderReponsitory.save(orderList);
                        System.out.println("Xác nhận đơn hàng thành công");
                        // Bắt đầu tính thời gian và tự động cập nhật trạng thái
                        scheduleDeliveryUpdate(orderList);
                        break;
                    case 2:
                        System.out.println("Bạn có chắc chắn muốn hủy không");
                        System.out.println("1.Đồng ý");
                        System.out.println("2.Quay lại");
                        switch (Validate.validateInt()) {
                            case 1:
                                // Lưu trạng thái hiện tại của đơn hàng trước khi hủy
                                OrderStatus currentStatus = orderList.getOrderStatus();
                                // Cập nhật trạng thái đơn hàng thành CANCEL
                                orderList.setOrderStatus(OrderStatus.CANCEL);
                                oderReponsitory.save(orderList);
                                System.out.println("Đã hủy đơn hàng thành công");


                                // Nếu đơn hàng đã ở trạng thái Waiting, thì cập nhật số lượng tồn kho
                                if (currentStatus.equals(OrderStatus.WAITING)) {
                                    // Duyệt qua các sản phẩm trong đơn hàng để cộng số lượng đã hủy vào số lượng tồn kho
                                    // Cập nhật số lượng tồn kho của sản phẩm trong cơ sở dữ liệu
                                    orderList.getOrdersDetails().forEach(orderDetail -> {
                                        Products product = productReponsitory.findById(orderDetail.getProductId());
                                        int quantityToCancel = orderDetail.getQuantity();
                                        int currentStock = product.getStock();
                                        product.setStock(currentStock + quantityToCancel);
                                        productReponsitory.updateStock(product.getId(), currentStock + quantityToCancel);
                                    });
                                }
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Lựa chọn không hợp lí mời nhập lại");
                                break;
                        }
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");

                }
            } else if (orderList.getOrderStatus() == OrderStatus.CONFIRM) {
                System.out.println("Không thể thay đổi trạng thái đơn hàng đã xác nhận.");
            } else if (orderList.getOrderStatus() == OrderStatus.CANCEL) {
                System.out.println("Không thể thay đổi trạng thái đơn hàng đã hủy.");
            } else if (orderList.getOrderStatus() == OrderStatus.DELIVERY) {
                System.out.println(YELLOW + "Đơn hàng đang vận chuyển" + RESET);
            } else {
                System.out.println("Trạng thái đơn hàng không hợp lệ.");
            }
        }
    }

    private void scheduleDeliveryUpdate(Order order) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (order.getOrderStatus() == OrderStatus.CONFIRM) {
                    // Kiểm tra thời gian và tự động cập nhật trạng thái thành DELIVERY
                    LocalDateTime confirmationTime = order.getOrderTime();
                    LocalDateTime currentTime = LocalDateTime.now();
                    long minutesPassed = Duration.between(confirmationTime, currentTime).toMinutes();
                    if (minutesPassed >= 5) {
                        order.setOrderStatus(OrderStatus.DELIVERY);
                        oderReponsitory.save(order);
                        System.out.println(YELLOW + "Đơn hàng " + order.getOrderId() + " đang vận chuyển." + RESET);
                        timer.cancel(); // Dừng timer sau khi cập nhật trạng thái
                    }
                }
            }
        }, 0, 1800000); // Kiểm tra mỗi 1 phút (60.000 miliseconds)
    }


    private void showOrder() {
        System.out.println("Tất cả các đơn hàng:");
        List<Order> orderList = oderReponsitory.findAll();

        if (orderList.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
        } else {
            // Sắp xếp danh sách đơn hàng dựa trên thời gian

            sort(orderList,
                    (order1, order2) -> order1.getOrderTime().compareTo(order2.getOrderTime()));

            for (Order order : orderList) {
                LocalDateTime orderTime = order.getOrderTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedOrderTime = orderTime.format(formatter);
                for (String s : Arrays.asList("Đơn hàng:" + order.getOrderId(), "Tên khách hàng: " + order.getName(), "Điện thoại: " + order.getPhoneNumber(), "Địa chỉ: " + order.getAddress(), "Thời gian đặt hàng: " + formattedOrderTime, "Chi tiết đơn hàng:")) {
                    System.out.println(s);
                }

                List<OrdersDetail> orderDetails;
                orderDetails = order.getOrdersDetails();

                orderDetails.forEach(orderDetail -> {
                    Products product = productReponsitory.findById(orderDetail.getProductId());
                    System.out.println("Sản phẩm: " + product.getProductName());
                    System.out.println("Số lượng: " + orderDetail.getQuantity());
                    System.out.println("Giá tiền: " + Validate.formatCurrency(orderDetail.getUnitPrice()));
                });

                System.out.printf("Tổng tiền đơn hàng: %s%n", Validate.formatCurrency(order.getTotal()));
//                System.out.println("Trạng thái đơn hàng: " + order.getOrderStatus());
                System.out.print("Trạng thái đơn hàng: ");
                String orderStatus = String.valueOf(order.getOrderStatus());

                switch (orderStatus) {
                    case "WAITING":
                        System.out.println("Đang chờ");
                        break;
                    case "CONFIRM":
                        System.out.println("Đã xác nhận");
                        break;
                    case "DELIVERY":
                        System.out.println("Đang giao hàng");
                        break;
                    case "CANCEL":
                        System.out.println("Đã hủy đơn hàng");
                        break;
                    default:
                        System.out.println("Trạng thái không xác định");
                        break;
                }
                System.out.println("--------------------------------------------------------------------------------");
            }
        }
    }
}

