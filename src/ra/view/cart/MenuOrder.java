package ra.view.cart;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Products;
import ra.model.account.Users;
import ra.reponsitory.CartReponsitory;
import ra.reponsitory.ProductReponsitory;

import ra.service.CartService;
import ra.service.ProductService;
import ra.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class MenuOrder {
    Config<Users> config = new Config<>();
    Users getUsers1 = config.readFile(Config.URL_USERS_LOGIN);
    ProductReponsitory productReponsitory = new ProductService();
    CartReponsitory cartReponsitory = new CartService();
    UserService userReponsitory = new UserService();
    Users users = userReponsitory.findById(getUsers1.getId());

    public void addProductToCart() {
//        System.out.println(users);
        if (users != null) {
            int userId = users.getId();
             // Chỉ cần cartId bằng userId
            Cart cart = cartReponsitory.findCartByUserLogin();
            Users userLogin = new Config<Users>().readFile(Config.URL_USERS_LOGIN);
            if (cart == null){
                cart = new Cart(userId,userLogin.getId(),new HashMap<>(),false);
            }

            // Nhập ID sản phẩm
            System.out.print("Nhập ID sản phẩm: ");
            int productId = Validate.validateInt();

            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
            Map<Products, Integer> productsInCart = cart.getProducts();
            Products product = productReponsitory.findById(productId);

            if (product != null) {
                if (productsInCart.containsKey(product)) {
                    int existingQuantity = productsInCart.get(product);
                    productsInCart.put(product, existingQuantity + 1);
                } else {
                    // Nếu sản phẩm chưa có trong giỏ hàng, thêm nó vào giỏ hàng với số lượng 1
                    productsInCart.put(product, 1);
                }

                // Cập nhật giỏ hàng
                cartReponsitory.save(cart);
                System.out.println("Thêm sản phẩm vào giỏ hàng thành công");
//                for (Cart cartw: cartReponsitory.findAll()) {
//                    System.out.println(cartw);
//                }
            } else {
                System.out.println("Không tìm thấy sản phẩm có ID " + productId);
            }
        } else {
            System.out.println("Không tìm thấy thông tin người dùng. Hãy đăng nhập trước khi thêm sản phẩm vào giỏ hàng.");
        }
    }

}
