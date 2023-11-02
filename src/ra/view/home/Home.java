package ra.view.home;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;
import ra.view.account.Singin;
import ra.view.account.Singnup;

import static ra.config.Color.RESET;

public class Home {
    UserReponsitory userReponsitory = new UserService();
    Config<Users> usersConfig = new Config<>();

    public void menuHome() {

        do {
            System.out.println("\u001B[35m╔════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("\u001B[35m║                                                                            ║");
            System.out.printf("\u001B[35m║                            \u001B[34m%-20s                           \u001B[35m║\n", "Xin chào đến với Shop");
            System.out.println("\u001B[35m║                                                                            ║");
            System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("\u001B[35m║                                 \u001B[33mMENU HOME                                  \u001B[35m║");
            System.out.println("\u001B[35m║~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~║");
            System.out.println("\u001B[35m║                       \u001B[36m1. Đăng Nhập                                         \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Đăng Ký                                           \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Thoát                                             \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (0/1/2/3/4): ");
            switch (Validate.validateInt()) {
                case 1:
                    new Singin().login();
                    break;
                case 2:
                    new Singnup().register();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

}
