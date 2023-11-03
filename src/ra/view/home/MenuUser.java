package ra.view.home;

import static ra.config.Color.*;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Catalogs;
import ra.model.Products;
import ra.model.account.Users;
import ra.reponsitory.CatalogsReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.reponsitory.UserReponsitory;
import ra.service.CatalogsService;
import ra.service.ProductService;
import ra.service.UserService;
import ra.view.cart.MenuCart;
import ra.view.cart.MenuOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MenuUser {
    UserReponsitory userReponsitory = new UserService();
    ProductReponsitory productReponsitory = new ProductService();
    CatalogsReponsitory catalogsReponsitory=new CatalogsService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);
    Users usersChangepass = userReponsitory.findById(users.getId());

    int userId = userReponsitory.findById(users.getId()).getId();

    public void menuUser() {
        do {
            System.out.println("\u001B[35m╔════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("\u001B[35m║        /\\_/\\                                                               ║");
            System.out.printf("\u001B[35m║       ( o.o )                Xin chào: \u001B[34m%-20s                \u001B[35m║\n", users.getFullName());
            System.out.println("\u001B[35m║        > ^ <                                                               ║");
            System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("\u001B[35m║                                 \u001B[33mMENU HOME                                  \u001B[35m║");
            System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("\u001B[35m║                       \u001B[36m1. Tìm kiếm sản phẩm                                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Hiển thị sản phẩm nổi bật                         \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m3. Danh sách sản phẩm                                \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m4. Đặt hàng                                          \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m5. Giỏ hàng                                          \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m6. Thông tin cá nhân                                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Đăng xuất                                         \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (0/1/2/3/4/5): ");
            switch (Validate.validateInt()) {
                case 0:
                    logOut();
                    break;
                case 1:
                    searchProducts();
                    break;
                case 2:
                    ShowProductNew();
                    break;
                case 3:
                    showProduct();
                    break;
                case 4:
                    new MenuOrder().addProductToCart();
                    break;
                case 5:
                    new MenuCart().menuCart();
                    break;
                case 6:
                    new MenuProfile().menuProfile();
                    break;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void logOut() {
        while (true){
            System.out.println("""
                Bạn có muốn đăng xuất
                1.Có
                2.Không
                Mời lựa chọn
                """);
            switch (Validate.validateInt()){
                case 1:
                    new Config<Users>().writeFile(Config.URL_USERS_LOGIN,null);
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
            if (product.isStatus()) {
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
        String searchKey=Validate.validateString();
        System.out.println("Sản phẩm cần tìm kiếm là:");
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s\n" + RESET,
                "ID", "Tên sản phẩm", "Danh mục", "Mô tả sản phẩm", "Giá sản phẩm");
        for (Products products: productReponsitory.findAll()) {
            if (products.getProductName().contains(searchKey)){
                System.out.println(products.toShortString());
            }else {
                System.out.println(RED + "Không tìm thấy tên sản phẩm hoặc danh mục sản phẩm bị rỗng!!!" + RESET);
                return;
            }
        }
    }

    private void showProduct() {
        System.out.println("Tất cả các sản phẩm :");
        List<Products> activeProducts = new ArrayList<>();
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s\n" + RESET,
                "ID", "Tên sản phẩm", "Danh mục", "Mô tả sản phẩm", "Giá sản phẩm");
        for (int j = 0; j < productReponsitory.findAll().size(); j++) {
            Products products = productReponsitory.findAll().get(j);
            List<Catalogs>catalogsList=catalogsReponsitory.findAll();
            Catalogs cat = null;
            for (Catalogs catalog : catalogsList) {
              if (products.getCategoryId().getId() == catalog.getId()){
                 cat = catalog;
                 break;
              }
            }
            if (cat == null){
                System.out.println("Danh mục đã bị xoa");
                return;
            }
            if (cat.isStatus() && products.isStatus()) {

                System.out.println( products.toShortString());
            }else {
                System.out.println("Danh sách sản phẩm rỗng ");
                return;
            }
        }
    }


}
