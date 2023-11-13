package ra.view.shoppingcart;

import ra.config.Validate;
import ra.model.Cart;
import ra.reponsitory.CartReponsitory;
import ra.service.CartService;

import java.util.Map;

public class DeleteCart {
    CartReponsitory cartReponsitory = new CartService();
    public void delete() {
        System.out.println("Nhập mã sản phẩm muốn xóa:");
        int deleteProductId = Validate.validateInt();
        Cart cart = cartReponsitory.findCartByUserLogin();

        if (cart != null) {
            Map<Integer, Integer> productsInCart = cart.getProductCart();

            if (productsInCart.containsKey(deleteProductId)) {
                productsInCart.remove(deleteProductId);
                cartReponsitory.save(cart);
                System.out.println("Đã xóa sản phẩm khỏi giỏ hàng thành công.");
            } else {
                System.out.println("Không tìm thấy sản phẩm có ID " + deleteProductId + " trong giỏ hàng.");
            }
        } else {
            System.out.println("Giỏ hàng trống.");
        }
    }
}
