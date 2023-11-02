package ra.service;

import ra.reponsitory.CartReponsitory;
import ra.view.home.Cart;

import java.util.List;

public class CartService implements CartReponsitory {
    public  static List<Cart>cartList;

    @Override
    public List<Cart> findAll() {
        return cartList;
    }

    @Override
    public void save(Cart cart) {

    }

    @Override
    public void delete(int t) {

    }

    @Override
    public Cart findById(int id) {
        return null;
    }

    @Override
    public int getNewId() {
        return 0;
    }

    @Override
    public void updateData() {

    }
}
