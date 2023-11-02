package ra.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int cartId;
    private int userId;
    private Map<Products,Integer>Products = new HashMap<>();

    public Cart(int cartId, int userId, Map<ra.model.Products, Integer> products) {
        this.cartId = cartId;
        this.userId = userId;
        Products = products;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<ra.model.Products, Integer> getProducts() {
        return Products;
    }

    public void setProducts(Map<ra.model.Products, Integer> products) {
        Products = products;
    }
}
