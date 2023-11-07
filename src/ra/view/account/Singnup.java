package ra.view.account;

import ra.config.Validate;
import ra.model.account.RoleName;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;;

public class Singnup {
    UserReponsitory userReponsitory=new UserService();
    public void register() {
        System.out.println("+-------------------------------------------------+");
        System.out.println("| ****************** Đăng Ký ******************** |");
        System.out.println("+-------------------------------------------------+");
        Users users = new Users();
        users.setId(userReponsitory.getNewId());
        System.out.println("Nhập họ và tên :");
        users.setFullName(Validate.validateString());
        System.out.println("Nhập tài khoản:");
        while (true) {
            String username = (Validate.validateString());
           if (username.trim().length()>6){
               if (userReponsitory.existsUsername(username)) {
                   System.out.println("Tên đăng nhập đã tồn tại vui lòng nhập lại:");
               } else {
                   users.setUsername(username);
                   break;
               }
           }else {
               System.out.println("Tên tài khoản cần ít nhất 6 kí tự");
           }

        }
//        System.out.println("Nhập mật khẩu :");
//        users.setPassword(Validate.validateString());
        while (true) {
            System.out.print("Nhập mật khẩu: ");
            String password = Validate.validateString();

            if (password.length() >= 6) {
                users.setPassword(password);
                break;
            } else {
                System.out.println("Mật khẩu cần ít nhất 6 kí tự");
            }
        }
        System.out.println("Xác nhận lại mật khẩu :");
        while (true){
            String confirmPassword = Validate.validateString();
            if (users.getPassword().equals(confirmPassword)){
                break;
            }else {
                System.out.println("Mật khẩu không trùng khớp mời nhập lại :");
            }
        }
        System.out.println("Nhập địa chỉ email :");
        while (true){
            String email=Validate.validateEmail();
            if (userReponsitory.existsEmail(email)){
                System.out.println("Địa email đã tồn tại vui lòng nhập lại :");
            }else {
                users.setEmail(email);
                break;
            }
        }

        boolean shouldContinue = true; // Biến để kiểm soát việc tiếp tục vòng lặp
        while (shouldContinue) {
            System.out.println("Chọn vai trò (1: ADMIN(nếu có),  0: Bỏ qua): ");
            int roleChoice = Validate.validateInt();

            switch (roleChoice) {
                case 1:
                    String adminPassword;
                    boolean isAdminConfirmed = false;

                    while (!isAdminConfirmed) {
                        System.out.println("Nhập mật khẩu xác nhận ADMIN (hoặc nhập '0' để thoát): ");
                        adminPassword = Validate.validateString();

                        if (adminPassword.equals("admin")) {
                            users.setRoles(RoleName.ADMIN);
                            // Lưu vào cơ sở dữ liệu
                            userReponsitory.save(users);
                            System.out.println("Phân quyền ADMIN tài khoản thành công");
                            isAdminConfirmed = true;
                            shouldContinue = false;
                        } else if (adminPassword.equals("0")) {
                            // Nếu người dùng nhập '0', thoát khỏi vòng lặp và quay lại chọn vai trò
                            shouldContinue = false;
                            break;
                        } else {
                            System.out.println("Mật khẩu xác nhận ADMIN không đúng. Vui lòng nhập lại hoặc nhập '0' để thoát.");
                        }
                    }
                    break;
                case 0:
                    shouldContinue = false; // Thoát khỏi vòng lặp
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại vai trò.");
            }
        }
        System.out.println("Nhập số điện thoại :");
        users.setPhoneNumber(Validate.validatePhone());
        userReponsitory.save(users);
        System.out.println("Tạo mới tài khoản thành công");
        new Singin().login();
    }
}
