package ra.view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;
import ra.view.home.Home;
import static ra.config.Color.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminManagement {
    UserReponsitory userReponsitory=new UserService();
    Config<Users> config=new Config<>();
    Users users=config.readFile(Config.URL_USERS_LOGIN);
    public void menuAdmin() {
                do {
                    System.out.println("\u001B[35m╔════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("\u001B[35m║        (\\(\\                                                                ║");
                    System.out.printf("\u001B[35m║        ( -.-)                Xin chào: \u001B[34m%-20s                \u001B[35m║\n", users.getFullName());
                    System.out.println("\u001B[35m║        o_(\")(\")                                                            ║");
                    System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                    System.out.println("\u001B[35m║                                 \u001B[33mADMIN HOME                                 \u001B[35m║");
                    System.out.println("\u001B[35m║                      \u001B[33mThời gian:  " + getCurrentTime() +"                       \u001B[35m║");
                    System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
                    System.out.println("\u001B[35m║                           \u001B[36m1. Quản lý danh mục                              \u001B[35m║");
                    System.out.println("\u001B[35m║                           \u001B[36m2. Quản lý sản phẩm                              \u001B[35m║");
                    System.out.println("\u001B[35m║                           \u001B[36m3. Quản lý người dùng                            \u001B[35m║");
                    System.out.println("\u001B[35m║                           \u001B[36m4. Quản lý đơn hàng                              \u001B[35m║");
                    System.out.println("\u001B[35m║                           \u001B[36m5. Xem thông tin cá nhân                         \u001B[35m║");
                    System.out.println("\u001B[35m║                           \u001B[31m0. Đăng xuất                                     \u001B[35m║");
                    System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
                    System.out.print("Mời lựa chọn (1/2/3/4/5/0): ");
                    switch (Validate.validateInt()) {
                        case 1:
                            new CatalogManagement().menuCategoryManagement();
                            break;
                        case 2:
                            new ProductManagement().menuProductManagement();
                            break;
                        case 3:
                            new UserManagement().menuUserManagement();
                            break;
                        case 4:
                            new OrderManagement().menuOrderManagement();
                            break;
                        case 5:
                            profile();
                            break;
                        case 0:
                            logOut();
                            break;
                        default:
                            System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
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

    private void profile() {
        Users userProfile = userReponsitory.findById(users.getId());
        System.out.println("Thông tin cá nhân là :");
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
        System.out.println(userProfile);
    }

    public static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return currentTime.format(formatter);
    }
}
