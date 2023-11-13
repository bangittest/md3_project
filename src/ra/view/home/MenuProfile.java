package ra.view.home;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;


import static ra.config.Color.RED;
import static ra.config.Color.RESET;

public class MenuProfile {
    UserReponsitory userReponsitory = new UserService();
    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);

    public void menuProfile() {
        do {
            System.out.println("\u001B[35m╔════════════════════════════ Thông tin cá nhân ═════════════════════════════╗");
            System.out.println("\u001B[35m║                       \u001B[36m1. Thông tin cá nhân                                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Đổi mật khẩu                                      \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (0/1/2/): ");
            switch (Validate.validateInt()) {
                case 1:
                    profile();
                    break;
                case 2:
                    changePass();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void changePass() {
        Users usersChangepass = userReponsitory.findById(users.getId());
        System.out.println("Thông tin cá nhân là :");
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
        System.out.println(usersChangepass);

        while (true) {
            System.out.println("Nhập mật khẩu cũ để xác nhận (hoặc nhập 'exit' để thoát):");
            String pass = Validate.validateString();

            if (pass.equalsIgnoreCase("exit")) {
                break;
            }

            if (usersChangepass.getPassword().equals(pass)) {
                while (true) {
                    System.out.println("Nhập mật khẩu mới để thay đổi:");
                    String passwordEdit = Validate.validateString();
                    System.out.println("Xác thực lại mật khẩu mới:");
                    String confirmPassword = Validate.validateString();

                    if (passwordEdit.equals(confirmPassword)) {
                        usersChangepass.setPassword(passwordEdit);
                        userReponsitory.save(usersChangepass);
                        System.out.println("Đổi mật khẩu thành công");
                        config.writeFile(Config.URL_USERS_LOGIN, null);
                        new Home().menuHome();
                        break;
                    } else {
                        System.out.println("Nhập mật khẩu không trùng khớp. Vui lòng nhập lại.");
                    }
                }
                break;
            } else {
                System.out.println("Nhập mật khẩu cũ không đúng. Vui lòng nhập lại.");
            }
        }
    }

    private void profile() {
        Users userProfile = userReponsitory.findById(users.getId());
        System.out.println("Thông tin cá nhân là :");
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
        System.out.println(userProfile);
        System.out.println("\u001B[35m╔══════════════════════════ Sửa thông tin cá nhân ═══════════════════════════╗");
        System.out.println("\u001B[35m║                       \u001B[36m1. Sửa thông tin email                               \u001B[35m║");
        System.out.println("\u001B[35m║                       \u001B[36m2. Sửa họ và tên                                     \u001B[35m║");
        System.out.println("\u001B[35m║                       \u001B[36m3. Sửa số điện thoại                                 \u001B[35m║");
        System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
        System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
        switch (Validate.validateInt()) {
            case 1:
                while (true) {
                    System.out.println("Nhập địa chỉ email mới:");
                    String emailNew = Validate.validateString();

                    if (!userReponsitory.existsEmail(emailNew)) {
                        userProfile.setEmail(emailNew);
                        userReponsitory.save(userProfile);
                        System.out.println("Đổi địa chỉ email thành công");
                        break;
                    } else {
                        System.out.println(RED + "Địa chỉ email đã tồn tại. Vui lòng nhập lại:" + RESET);
                    }
                }
                break;
            case 2:
                System.out.println("Nhập họ và tên mới:");
                String fullNameNew = Validate.validateString();
                userProfile.setFullName(fullNameNew);
                userReponsitory.save(userProfile);
                System.out.println(userProfile);
                System.out.println("Đổi họ và tên thành công");
                break;
            case 3:
                System.out.println("Nhập số điện thoại mới:");
                String phoneNumberNew = Validate.validatePhone();
                userProfile.setPhoneNumber(phoneNumberNew);
                userReponsitory.save(userProfile);
                System.out.println("Đổi số điện thoại thành công");
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Nhập địa chỉ không đúng mời nhập lại" + RESET);
        }
    }
}
