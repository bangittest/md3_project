package ra.view.shoppingcart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Products;
import ra.model.account.Users;
import ra.reponsitory.CartReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.service.CartService;
import ra.service.ProductService;

import java.util.Map;

public class IncreaseDecrease {
    ProductReponsitory productReponsitory = new ProductService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    CartReponsitory cartReponsitory = new CartService();
    public void increaseDecrease() {
        System.out.println("Nhập mã sản phẩm bạn muốn thay đổi số lượng:");
        int productId = Validate.validateInt();
        Cart cart = cartReponsitory.findCartByUserLogin();

        if (cart != null) {
            Map<Integer, Integer> productsInCart = cart.getProductCart();
                //kiểm tra mã sản phẩm nhập vào có tồn tại hay không nếu tồn tại thì thực hiện
            if (productsInCart.containsKey(productId)) {
                Products product = productReponsitory.findById(productId);
                if (product != null) {
                    int currentQuantity = productsInCart.get(productId);
                    int stock = product.getStock();

                    System.out.println("Số lượng hiện tại của sản phẩm có ID " + productId + " trong giỏ hàng là: " + currentQuantity);
                    System.out.println("Số lượng hiện tại trong kho là: " + stock);

                    System.out.println("Nhập số lượng mới:");
                    int newQuantity = Validate.validateInt();

                    if (newQuantity <= 0) {
                        System.out.println("Số lượng phải lớn hơn 0.");
                    } else if (newQuantity <= stock) {
                        productsInCart.put(productId, newQuantity);
                        cartReponsitory.save(cart);
                        System.out.println("Đã cập nhật số lượng sản phẩm trong giỏ hàng thành công.");
                    } else {
                        System.out.println("Không đủ số lượng sản phẩm trong kho.");
                    }
                } else {
                    System.out.println("Không tìm thấy sản phẩm có ID " + productId);
                }
            } else {
                System.out.println("Không tìm thấy sản phẩm có ID " + productId + " trong giỏ hàng.");
            }
        } else {
            System.out.println("Giỏ hàng trống.");
        }
    }

}
