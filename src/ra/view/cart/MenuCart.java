package ra.view.cart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.account.Users;
import ra.model.order.Order;
import ra.model.order.OrderStatus;
import ra.reponsitory.CartReponsitory;
import ra.reponsitory.OderReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.reponsitory.UserReponsitory;
import ra.service.CartService;
import ra.service.OrderService;
import ra.service.ProductService;
import ra.service.UserService;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static ra.config.Color.RESET;

public class MenuCart {
    UserReponsitory userReponsitory = new UserService();
    CartReponsitory cartReponsitory = new CartService();
    ProductReponsitory productReponsitory = new ProductService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    int cartId = userReponsitory.findById(users.getId()).getId();
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    OderReponsitory oderReponsitory = new OrderService();

    public void menuCart() {
        do {
            System.out.println("\u001B[35m╔══════════════════════════════     CART     ════════════════════════════════╗");
            System.out.println("\u001B[35m║                       \u001B[36m1. Thanh toán                                        \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Tăng số lượng hoặc giảm                           \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m3. Xóa sản phẩm                                      \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m4. Lịch sử đơn hàng                                        \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (0/1/2/3/4/5): ");
            showCart();
            switch (Validate.validateInt()) {
                case 1:
                    new CheckoutOrder().checkout();
                    break;
                case 2:
                    updateQuantity();
                    break;
                case 3:
                    deleteProductById();
                    break;
                case 4:
                    new historyOrder().history();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }


    private void deleteProductById() {
        Cart cart = cartReponsitory.getCartInfo(cartId);
        System.out.println("Nhập id muốn xóa :");
        int deleteProductId = Validate.validateInt();
        Map<Products, Integer> productsInCart = cart.getProducts();
        for (Map.Entry<Products, Integer> entry : productsInCart.entrySet()) {
            Products key = entry.getKey();
            int value = entry.getValue();
            if (key.getId() == deleteProductId) {
                productsInCart.remove(key);
                cartReponsitory.updateData();
                System.out.println("xóa thành công");
                return;
            }
        }
        System.out.println("Không tìm thấy sản phẩm có ID " + deleteProductId + " trong giỏ hàng.");

    }


    public void updateQuantity() {
        // Lấy giỏ hàng của người dùng
        Cart cart = cartReponsitory.getCartInfo(cartId);
        System.out.print("Nhap id muốn tăng hoặc giảm: ");
        int productId = Validate.validateInt();


        int newQuantity;
        do {
            System.out.print("Nhập số lượng muốn tăng hoặc giảm: ");
            newQuantity = Integer.parseInt(Config.scanner().nextLine());
            if (newQuantity <= 0) {
                System.out.println("Vui lòng nhập một số lớn hơn 0.");
            }
        } while (newQuantity <= 0);

        Products product = null;
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
        Map<Products, Integer> productsInCart = cart.getProducts();
        for (Map.Entry<Products, Integer> entry : productsInCart.entrySet()) {
            Products key = entry.getKey();
            int value = entry.getValue();
            if (key.getId() == productId) {
                product = key;
                break;
            }
        }
        if (product != null && productsInCart.containsKey(product)) {
//            int currentQuantity = productsInCart.get(product);
//
//            int updatedQuantity = currentQuantity + newQuantity;
            productsInCart.put(product, newQuantity);
            cartReponsitory.save(cart);
            System.out.println("Số lượng sản phẩm " + product.getProductName() + " đã được cập nhật thành " + newQuantity);
        } else {
            System.out.println("Không tìm thấy sản phẩm có ID " + productId + " trong giỏ hàng.");
        }
    }


    public void showCart() {

        Cart cart = cartReponsitory.getCartInfo(cartId);
        if (cart != null) {
            System.out.println("Thông tin giỏ hàng (ID: " + cart.getId() + "):");
            Map<Products, Integer> productsInCart = cart.getProducts();

            if (productsInCart.isEmpty()) {
                System.out.println("Giỏ hàng đang trống.");
            } else {
                for (Map.Entry<Products, Integer> entry : productsInCart.entrySet()) {
                    Products product = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.println("Tên sản phẩm: " + product.getProductName() + "," + "\nGiá tiền:" + String.format(currencyFormat.format(product.getUnitPrice() * quantity)) + "," + "\nMô tả sản phẩm:" + product.getDescription() + "\nMã sản phẩm: " + product.getId() + ",\nSố lượng: " + quantity);
                }
            }
        } else {
            System.out.println("Không tìm thấy giỏ hàng có ID: " + cartId);
        }
    }
}
