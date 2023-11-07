package ra.view.shoppingcart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.account.Users;
import ra.model.order.Order;
import ra.reponsitory.*;
import ra.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckOut {
    ProductReponsitory productReponsitory = new ProductService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    CartReponsitory cartReponsitory = new CartService();
    OderReponsitory oderReponsitory=new OrderService();


    public void checkOut() {
        Cart cart = cartReponsitory.findCartByUserLogin();

        if (cart == null) {
            System.out.println("Giỏ hàng trống, không thể thanh toán.");
            return;
        }
        Map<Integer, Integer> productsInCart = cart.getProductCart();
        if (productsInCart.isEmpty()) {
            System.out.println("Giỏ hàng đang trống, không thể thanh toán.");
            return;
        }

        System.out.println("Thông tin giỏ hàng (ID: " + cart.getIdCart() + "):");
        System.out.println("|--------------------------------------------------------------------------------|");
        System.out.printf("| %s   |               %s             |  %s  |          %s         |\n", "Mã SP", "Tên SP", "SỐ LƯỢNG", "GIÁ");
        System.out.println("|--------------------------------------------------------------------------------|");

        double totalAmount = 0;

        for (Map.Entry<Integer, Integer> entry : productsInCart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            Products product = productReponsitory.findById(productId);
            if (product != null) {
                if (product.isStatus()){
                    int stock = product.getStock();

                    // Kiểm tra số lượng tồn kho
                    if (quantity > stock) {
                        System.out.println("Sản phẩm " + product.getProductName() + " không đủ số lượng tồn kho.");
                        return;
                    }
                    double price = product.getUnitPrice();
                    double subtotal = quantity * price;
                    totalAmount += subtotal;
                    System.out.printf("|  %4d   |        %-20s      |   %-7s  |      %-16s|\n",
                            productId, product.getProductName(), quantity, Validate.formatCurrency(price));
                    System.out.println("'--------------------------------------------------------------------------------'");
                }else {
                    System.out.println("Sản phẩm " + product.getProductName() + " không còn hoạt động để thanh toán.");
                    return;
                }
            }
        }

        System.out.println("Tổng tiền: " + Validate.formatCurrency(totalAmount));

        System.out.println("Nhập họ và tên:");
        String nameOrder = Validate.validateString();
        System.out.println("Nhập số điện thoại:");
        String phone = Validate.validatePhone();
        System.out.println("Nhập địa chỉ:");
        String address = Validate.validateString();
        Order order = new Order();
        order.setName(nameOrder);
        order.setOrderId(oderReponsitory.getNewId());
        order.setPhoneNumber(phone);
        order.setUserId(users.getId());
        order.setAddress(address);
        order.setTotal(totalAmount);
        order.setOrderTime(LocalDateTime.now());

        // Tạo danh sách chi tiết đơn hàng (OrderDetails)
        List<OrdersDetail> orderDetailsList = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : productsInCart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Products product = productReponsitory.findById(productId);

            if (product != null) {
                double price = product.getUnitPrice();

                // Tạo một chi tiết đơn hàng
                OrdersDetail orderDetails = new OrdersDetail();
                orderDetails.setProductId(productId);
                orderDetails.setQuantity(quantity);
                orderDetails.setUnitPrice(price);

                // Thêm chi tiết đơn hàng vào danh sách
                orderDetailsList.add(orderDetails);
            }
        }

        // Đặt danh sách chi tiết đơn hàng cho đối tượng Order
        order.setOrdersDetails(orderDetailsList);

        // Lưu đơn hàng vào cơ sở dữ liệu
        oderReponsitory.save(order);

        // Trừ số lượng tồn kho sau khi thanh toán
        for (Map.Entry<Integer, Integer> entry : productsInCart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Products product = productReponsitory.findById(productId);

            if (product != null) {
                int rStock = product.getStock() - quantity;

                // Cập nhật số lượng tồn kho của sản phẩm trong cơ sở dữ liệu
                productReponsitory.updateStock(productId, rStock);
            }
        }

        // Xóa sản phẩm khỏi giỏ hàng
        productsInCart.clear();

        // Lưu lại giỏ hàng
        cartReponsitory.save(cart);

        System.out.println("Thanh toán thành công");
        new History().history();
    }
}
