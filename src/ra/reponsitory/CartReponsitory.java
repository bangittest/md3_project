package ra.reponsitory;

import ra.model.Cart;


public interface CartReponsitory extends Responsitory<Cart> {
    Cart findCartByUserLogin();
    
}
