package ra.view.admin;

import ra.config.Validate;
import ra.view.home.Home;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminManagement {
    // Mã màu ANSI
    String cyan = "\u001B[36m";
    String green = "\u001B[32m";
    String yellow = "\u001B[33m";
    String purple = "\u001B[35m";
    String resetColor = "\u001B[0m";
    public void menuAdmin() {
                do {
                    System.out.println("+-----------------------------------------------------------------------+");
                    System.out.println("|                         \uD83D\uDCBB   ADMIN HOME   \uD83D\uDCBB                          |");
                    System.out.println("|-----------------------------------------------------------------------|");
                    System.out.println("|                        \uD83D\uDC4B Xin chào, " + Home.usersLogin.getFullName() + "!                            |");
                    System.out.println("|                    Thời gian: \uD83D\uDD70 " + getCurrentTime() + "                   |");
                    System.out.println("|-----------------------------------------------------------------------|");
                    System.out.println("|                      1. \uD83D\uDCD7   Quản lý danh mục                         |");
                    System.out.println("|                      2. \u274C   Quản lý sản phẩm                         |");
                    System.out.println("|                      3. \uD83D\uDD27   Quản lý khách hàng                       |");
                    System.out.println("|                      4. \uD83D\uDD13   Quản lý đơn hàng                         |");
                    System.out.println("|                      5. \uD83D\uDD13   Xem thông tin cá nhân                    |");
                    System.out.println("|                      0. \u2B05\uFE0F   Đăng xuất                                |");
                    System.out.println("+-----------------------------------------------------------------------+\n");
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
                            break;
                        case 5:

                            break;
                        case 0:
                          Home.usersLogin=null;
                          new Home().menuHome();
                        default:
                            System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                            break;
                    }
                } while (true);
            }
    public static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return currentTime.format(formatter);
    }

}
