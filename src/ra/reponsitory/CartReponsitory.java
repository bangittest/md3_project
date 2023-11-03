package ra.reponsitory;

import ra.model.Cart;


public interface CartReponsitory extends Responsitory<Cart> {
    Cart getCartInfo(int cartId);
    Cart findCartByUserLogin();
}
