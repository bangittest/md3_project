package ra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private Map<Products,Integer>Products = new HashMap<>();
    private boolean status;

    public Cart() {}

    public Cart(int id, int userId, Map<ra.model.Products, Integer> products, boolean status) {
        this.id = id;
        this.userId = userId;
        Products = products;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", Products=" + Products +
                ", status=" + status +
                '}';
    }
}
