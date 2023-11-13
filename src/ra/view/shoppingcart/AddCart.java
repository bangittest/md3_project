package ra.view.shoppingcart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Products;
import ra.model.account.Users;
import ra.reponsitory.*;
import ra.service.*;

import java.util.HashMap;

public class AddCart {

    ProductReponsitory productReponsitory = new ProductService();
    CartReponsitory cartReponsitory = new CartService();

    public void AddCart() {

        System.out.println("--------------------- Danh Sách Sản Phẩm  --------------------");
        System.out.println(".-------------------------------------------------------------.");
        System.out.printf("| %s |   %s   |  %s  |     %s       |\n", "Mã SP", "  Tên Sản Phẩm  ", " Số lượng ", "Giá");
        for (Products products : productReponsitory.findAll()) {
            if (products.isStatus()) {
                double getUnit= products.getUnitPrice();
                System.out.printf("|%4d   |  %-20s|     %-6d   |  %-13s|\n", products.getId(),
                        products.getProductName(), products.getStock(),Validate.formatCurrency(getUnit) );
            }
        }
        System.out.println("'-------------------------------------------------------------'");
        System.out.println(".-------------------------------------------------------------.");
        System.out.println("| Chọn ID sản phẩm đặt hàng                | Chọn 0 : Thoát   |");
        System.out.println("'-------------------------------------------------------------'");
        System.out.print("  Nhập ID: ");
        int idBuy = Validate.validateInt();
        Products productBuy = productReponsitory.findById(idBuy);

        if (idBuy == 0) {
            return;
        }
        if (productBuy == null) {
            System.out.println("Không tồn tại sản phẩm theo Id vừa nhập");
        } else {
            if (!productBuy.isStatus()) {
                System.out.println("Sản phẩm không tồn tại mời bạn chọn sản phẩm khác");
                return;
            }
            if (productBuy.getStock() == 0) {
                System.out.println("Sản phẩm đã hết hàng, không thể thêm vào giỏ hàng.");
            } else {
                Cart cart = cartReponsitory.findCartByUserLogin();
                Users userLogin = new Config<Users>().readFile(Config.URL_USERS_LOGIN);
                if (cart == null) {
                    cart = new Cart(cartReponsitory.getNewId(), userLogin.getId(), new HashMap<>(), false);
                }
//                Kiểm tra xem key đã cho có tồn tại trong Map hay không.
                if (cart.getProductCart().containsKey(idBuy)) {
                    cart.getProductCart().put(idBuy, cart.getProductCart().get(idBuy) + 1);
                } else {
//                    Thêm một cặp key-value vào Map
                    cart.getProductCart().put(idBuy, 1);
                }
                cartReponsitory.save(cart);
                System.out.println("Đã thêm SP vào giỏ hàng thành công");
            }
        }
    }
}
