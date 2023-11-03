package ra.view.cart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.account.Users;
import ra.model.order.Order;
import ra.reponsitory.CartReponsitory;
import ra.reponsitory.OderReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.reponsitory.UserReponsitory;
import ra.service.CartService;
import ra.service.OrderService;
import ra.service.ProductService;
import ra.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutOrder {
    UserReponsitory userReponsitory = new UserService();
    CartReponsitory cartReponsitory = new CartService();
    ProductReponsitory productReponsitory = new ProductService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    int cartId = userReponsitory.findById(users.getId()).getId();
    OderReponsitory oderReponsitory = new OrderService();

    public void checkout() {
        Cart cart = cartReponsitory.getCartInfo(cartId);
        if (cart != null) {
            System.out.println("Thông tin giỏ hàng (ID: " + cart.getId() + "):");
            Map<Products, Integer> productsInCart = cart.getProducts();

            if (productsInCart.isEmpty()) {
                System.out.println("Giỏ hàng đang trống, không thể thanh toán.");
            } else {
                boolean allProductsAvailable = true;

                for (Map.Entry<Products, Integer> entry : productsInCart.entrySet()) {
                    Products product = entry.getKey();
                    int quantity = entry.getValue();

                    // Kiểm tra số lượng tồn kho
                    int currentStock = product.getStock();
                    if (currentStock < quantity) {
                        allProductsAvailable = false;
                        System.out.println("Sản phẩm " + product.getProductName() + " chỉ còn " + currentStock + " sản phẩm trong kho và không đủ để thanh toán.");
                        break;
                    }
                }

                if (allProductsAvailable) {
                    double totalAmount = 0;

                    // Tạo danh sách chi tiết đơn hàng (OrderDetails)
                    List<OrdersDetail> orderDetailsList = new ArrayList<>();

                    for (Map.Entry<Products, Integer> entry : productsInCart.entrySet()) {
                        Products product = entry.getKey();
                        int quantity = entry.getValue();

                        double price = product.getUnitPrice();
                        double subtotal = quantity * price;
                        totalAmount += subtotal;

                        // Tạo một chi tiết đơn hàng
                        OrdersDetail orderDetails = new OrdersDetail();
                        orderDetails.setProductId(product.getId());
                        orderDetails.setQuantity(quantity);
                        orderDetails.setUnitPrice(price);

                        // Thêm chi tiết đơn hàng vào danh sách
                        orderDetailsList.add(orderDetails);
                    }

                    System.out.println("Nhập họ và tên:");
                    String name = Validate.validateString();
                    System.out.println("Nhập số điện thoại:");
                    String phone = Validate.validatePhone();
                    System.out.println("Nhập địa chỉ:");
                    String address = Validate.validateString();
                    Order order = new Order();
                    order.setName(name);
                    order.setOrderId(cart.getId());
                    order.setPhoneNumber(phone);
                    order.setUserId(users.getId());
                    order.setAddress(address);
                    order.setTotal(totalAmount);

                    // Đặt danh sách chi tiết đơn hàng cho đối tượng Order
                    order.setOrdersDetails(orderDetailsList);

                    oderReponsitory.save(order);

                    // Trừ số lượng tồn kho sau khi thanh toán
                    for (Map.Entry<Products, Integer> entry : productsInCart.entrySet()) {
                        Products product = entry.getKey();
                        int quantity = entry.getValue();
                        int rStock = product.getStock() - quantity;

                        // Cập nhật số lượng tồn kho của sản phẩm trong cơ sở dữ liệu
                        productReponsitory.updateStock(product.getId(), rStock);
                    }

                    productsInCart.clear();
                    cartReponsitory.save(cart);
                    System.out.println("Thanh toán thành công");
                    new historyOrder();
                }
            }
        }
    }
}
