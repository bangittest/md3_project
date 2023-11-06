package ra.model;

import java.io.Serializable;
import java.util.Map;

public class Cart implements Serializable {
    private int idCart;
    private int idUser;
    private Map<Integer, Integer> productCart;
    private boolean status;

    public Cart() {
    }

    public Cart(int idCart, int idUser, Map<Integer, Integer> productCart, boolean status) {
        this.idCart = idCart;
        this.idUser = idUser;
        this.productCart = productCart;
        this.status = status;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Map<Integer, Integer> getProductCart() {
        return productCart;
    }

    public void setProductCart(Map<Integer, Integer> productCart) {
        this.productCart = productCart;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IdCart = " + idCart +
                " -  IdUser=" + idUser +
                " - ProductCart=" + productCart +
                " -  Status=" + (status ? "Đã thanh toán" : "Chưa thanh toán");
    }
}
