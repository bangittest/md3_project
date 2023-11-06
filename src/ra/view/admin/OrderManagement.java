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
import java.util.*;

import static ra.config.Color.RED;
import static ra.config.Color.RESET;

public class OrderManagement {
    ProductReponsitory productReponsitory=new ProductService();
    OderReponsitory oderReponsitory=new OrderService();
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
                    case 2:
                        // Lưu trạng thái hiện tại của đơn hàng trước khi hủy
                        OrderStatus currentStatus = orderList.getOrderStatus();
                        // Cập nhật trạng thái đơn hàng thành CANCELED
                        orderList.setOrderStatus(OrderStatus.CANCEL);
                        oderReponsitory.save(orderList);
                        System.out.println("Đã hủy đơn hàng thành công");


                        // Nếu đơn hàng đã ở trạng thái PENDING, thì cập nhật số lượng tồn kho
                        if (currentStatus == OrderStatus.WAITING) {
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
                        orderList.setOrderStatus(OrderStatus.CONFIRM);
                        oderReponsitory.save(orderList);
                        System.out.println("Xác nhận đơn hàng thành công");
                        // Bắt đầu tính thời gian và tự động cập nhật trạng thái
                        scheduleDeliveryUpdate(orderList);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");

                }
            } else if (orderList.getOrderStatus() == OrderStatus.CONFIRM ) {
                System.out.println("Không thể thay đổi trạng thái đơn hàng đã xác nhận.");
            } else if (orderList.getOrderStatus() == OrderStatus.CANCEL) {
                System.out.println("Không thể thay đổi trạng thái đơn hàng đã hủy.");
            } else if (orderList.getOrderStatus() == OrderStatus.DELIVERY){
                System.out.println("Đơn hàng đang vận chuyển");
            }else {
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
                        System.out.println("Đã cập nhật trạng thái đơn hàng thành DELIVERY.");
                        timer.cancel(); // Dừng timer sau khi cập nhật trạng thái
                    }
                }
            }
        }, 0, 60000); // Kiểm tra mỗi 1 phút (60.000 miliseconds)
    }


    private void showOrder() {
        System.out.println("Tất cả các đơn hàng:");
        List<Order> orderList = oderReponsitory.findAll();

        if (orderList.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
        } else {
            // Sắp xếp danh sách đơn hàng dựa trên thời gian
            Collections.sort(orderList, new Comparator<Order>() {
                @Override
                public int compare(Order order1, Order order2) {
                    return order1.getOrderTime().compareTo(order2.getOrderTime());
                }
            });

            for (Order order : orderList) {
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
    }
}

