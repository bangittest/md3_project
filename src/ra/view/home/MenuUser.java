package ra.view.home;

import static java.nio.file.Files.delete;
import static ra.config.Color.*;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Catalogs;
import ra.model.OrdersDetail;
import ra.model.Products;
import ra.model.account.Users;
import ra.model.order.Order;
import ra.model.order.OrderStatus;
import ra.reponsitory.*;
import ra.service.*;
import ra.view.shoppingcart.AddCart;
import ra.view.shoppingcart.CheckOut;
import ra.view.shoppingcart.History;


import java.time.LocalDateTime;
import java.util.*;

public class MenuUser {
    UserReponsitory userReponsitory = new UserService();
    ProductReponsitory productReponsitory = new ProductService();
    CatalogsReponsitory catalogsReponsitory = new CatalogsService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    CartReponsitory cartReponsitory = new CartService();
    OderReponsitory oderReponsitory=new OrderService();


    public void menuUser() {
        do {
            System.out.println("\u001B[35m╔════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("\u001B[35m║        (\\(\\                                                                ║");
            System.out.printf("\u001B[35m║        ( -.-)                Xin chào: \u001B[34m%-20s                \u001B[35m║\n", users.getFullName());
            System.out.println("\u001B[35m║        o_(\")(\")                                                            ║");
            System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("\u001B[35m║                                 \u001B[33mTrang Chủ                                  \u001B[35m║");
            System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("\u001B[35m║                       \u001B[36m1. Lọc theo danh mục                                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Tìm kiếm sản phẩm                                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m3. Hiển thị sản phẩm nổi bật theo giá giảm dần       \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m4. Danh sách sản phẩm                                \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m5. Đặt hàng                                          \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m6. Giỏ hàng                                          \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m7. Thông tin cá nhân                                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Đăng xuất                                         \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (0/1/2/3/4/5): ");
            switch (Validate.validateInt()) {
                case 0:
                    logOut();
                    break;
                case 2:
                    searchProducts();
                    break;
                case 4:
                    showProduct();
                    break;
                case 3:
                ShowProductNew();
                break;
                case 5:
                    new AddCart().AddCart();
                    break;
                case 6:
                    showCart();
                    break;
                case 7:
                    new MenuProfile().menuProfile();
                    break;
                case 1:
                    searchCatalog();
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void showCart() {
        // Hiển thị danh sách các sản phẩm đang có trong giỏ hàng
        System.out.println(".--------------------------------------------------------------------------------.");
        System.out.println("|                                       CART                                     |");
        System.out.println("|--------------------------------------------------------------------------------|");
        System.out.printf("| %s   |               %s             |  %s  |          %s         |\n", "Mã SP", "Tên SP", "SỐ LƯỢNG", "GIÁ");
        System.out.println("|--------------------------------------------------------------------------------|");
        Cart cart = cartReponsitory.findCartByUserLogin();
        if (cart == null) {
            System.out.println("|Giỏ hàng trống                                  |");
            System.out.println("'----------------------------------------------------------------------------'");

            return;
        }
        for (int idPro : cart.getProductCart().keySet()) {
            Products products = productReponsitory.findById(idPro);
            double unitPrice = products.getUnitPrice();
            System.out.printf("|  %4d   |        %-20s      |   %-7s  |      %-16s|\n",
                    idPro, products.getProductName(), cart.getProductCart().get(idPro), Validate.formatCurrency(unitPrice));
        }
        System.out.println("'--------------------------------------------------------------------------------'");
        System.out.println("Tổng tiền: ");
        System.out.println(".--------------------------------------------------------------------------------.");
        System.out.println("|   1.Thanh toán  |  2.Thay đổi SL  |   3.Xóa SP  |  4.Đơn Mua  |   5.Quay lại   |");
        System.out.println("'--------------------------------------------------------------------------------'");

        switch (Validate.validateInt()) {
            case 1:
                new CheckOut().checkOut();
                break;
            case 2:
                increaseDecrease();
                break;
            case 3:
                delete();
                break;
            case 4:
                new History().history();
            case 5:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ");
        }

    }

    private void increaseDecrease() {
        System.out.println("Nhập mã sản phẩm bạn muốn thay đổi số lượng:");
        int productId = Validate.validateInt();
        Cart cart = cartReponsitory.findCartByUserLogin();

        if (cart != null) {
            Map<Integer, Integer> productsInCart = cart.getProductCart();

            if (productsInCart.containsKey(productId)) {
                System.out.println("Số lượng hiện tại của sản phẩm có ID " + productId + " là: " + productsInCart.get(productId));
                System.out.println("Nhập số lượng mới:");
                int newQuantity = Validate.validateInt();

                if (newQuantity <= 0) {
                    System.out.println("Số lượng phải lớn hơn 0.");
                } else {
                    productsInCart.put(productId, newQuantity);
                    cartReponsitory.save(cart);
                    System.out.println("Đã cập nhật số lượng sản phẩm thành công.");
                }
            } else {
                System.out.println("Không tìm thấy sản phẩm có ID " + productId + " trong giỏ hàng.");
            }
        } else {
            System.out.println("Giỏ hàng trống.");
        }
    }


    private void delete() {
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





    private void searchCatalog() {
        System.out.println("Mời bạn chọn danh mục:");

        // Hiển thị danh sách danh mục
        for (int i = 0; i < catalogsReponsitory.findAll().size(); i++) {
            System.out.println((i + 1) + ". " + catalogsReponsitory.findAll().get(i).getCatalogName());
        }
        int choice = Validate.validateInt();
        if (choice >= 1 && choice <= catalogsReponsitory.findAll().size()) {
            // Lấy danh mục đã chọn
            System.out.println("Danh sách sản phẩm thuộc danh mục " + catalogsReponsitory.findAll().get(choice - 1).getCatalogName() + ":");
            System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s\n" + RESET,
                    "ID", "Tên sản phẩm", "Danh mục", "Mô tả sản phẩm", "Giá sản phẩm");
            // Lặp qua tất cả sản phẩm và kiểm tra xem sản phẩm thuộc danh mục đã chọn

            for (Products product : productReponsitory.findAll()) {
                List<Catalogs> catalogsList = catalogsReponsitory.findAll();
                Catalogs cat = null;
                for (Catalogs catalog : catalogsList) {
                    if (product.getCategoryId().getId() == catalog.getId()) {
                        cat = catalog;
                        break;
                    }
                }

                if (product.getCategoryId().getCatalogName().equals(catalogsReponsitory.findAll().get(choice - 1).getCatalogName())) {
                    if (product.isStatus() && cat.isStatus()) {

                        System.out.println(product.toShortString());
                    } else {
//                        System.out.println("Danh mục rỗng đã hết hàng");
                        return;
                    }
                }
            }
        } else {
            System.out.println("Lựa chọn không hợp lệ, mời nhập lại.");
        }
    }


    private void logOut() {
        while (true) {
            System.out.println("""
                    Bạn có muốn đăng xuất
                    1.Có
                    2.Không
                    Mời lựa chọn
                    """);
            int choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    new Config<Users>().writeFile(Config.URL_USERS_LOGIN, null);
                    new Home().menuHome();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Lựa chọn không đúng mời nhập lại");
                    break;
            }
        }
    }

    private void ShowProductNew() {
        System.out.println("Danh sách 10 sản phẩm có giá cao nhất:");

        List<Products> products = productReponsitory.findAll();

        // Lọc ra các sản phẩm có trạng thái hoạt động
        List<Products> activeProducts = new ArrayList<>();
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s\n" + RESET,
                "ID", "Tên sản phẩm", "Danh mục", "Mô tả sản phẩm", "Giá sản phẩm");
        for (Products product : products) {
            List<Catalogs> catalogsList = catalogsReponsitory.findAll();
            Catalogs cat = null;
            for (Catalogs catalog : catalogsList) {
                if (product.getCategoryId().getId() == catalog.getId()) {
                    cat = catalog;
                    break;
                }
            }
            if (product.isStatus() && cat.isStatus()) {
                activeProducts.add(product);
            }
        }

        // Sắp xếp danh sách sản phẩm theo giá tiền giảm dần
        activeProducts.sort(Comparator.comparing(Products::getUnitPrice).reversed());

        // Hiển thị 10 sản phẩm có giá cao nhất hoặc ít hơn nếu không có đủ 10 sản phẩm
        for (int i = 0; i < Math.min(10, activeProducts.size()); i++) {
            System.out.println(activeProducts.get(i).toShortString());
        }
    }

    private void searchProducts() {
        System.out.println("Nhập từ khóa cần tìm kiếm sản phẩm :");
        String searchKey = Validate.validateString();
        System.out.println("Sản phẩm cần tìm kiếm là:");
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s\n" + RESET,
                "ID", "Tên sản phẩm", "Danh mục", "Mô tả sản phẩm", "Giá sản phẩm");
        boolean check = true;
        for (Products products : productReponsitory.findAll()) {
            if (products.getProductName().contains(searchKey)) {
                System.out.println(products.toShortString());
                check = false;
            }
        }
        if (check) {
            System.out.println(RED + "Không tìm thấy tên sản phẩm hoặc danh mục sản phẩm bị rỗng!!!" + RESET);
        }
    }

    private void showProduct() {
        System.out.println("Tất cả các sản phẩm :");
        List<Products> activeProducts = new ArrayList<>();
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s\n" + RESET,
                "ID", "Tên sản phẩm", "Danh mục", "Mô tả sản phẩm", "Giá sản phẩm");
        boolean check = true;
        for (int j = 0; j < productReponsitory.findAll().size(); j++) {
            Products products = productReponsitory.findAll().get(j);
            List<Catalogs> catalogsList = catalogsReponsitory.findAll();
            Catalogs cat = null;
            for (Catalogs catalog : catalogsList) {
                if (products.getCategoryId().getId() == catalog.getId()) {
                    cat = catalog;
                    break;
                }
            }
            if (cat == null) {
                System.out.println("Danh mục đã bị xoa");
                return;
            }
            if (cat.isStatus() && products.isStatus()) {
                System.out.println(products.toShortString());
                check = false;
            }
        }
        if (check) {
            System.out.println("Danh sách sản phẩm rỗng ");
        }
    }
}
