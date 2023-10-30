package ra.view.home;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;
import ra.view.login.Singin;
import ra.view.login.Singnup;

public class Home {
    UserReponsitory userReponsitory=new UserService();
   Config<Users>usersConfig=new Config<>();

    public void menuHome() {

                do {
                    System.out.println("**********************HOME************************");
                    System.out.println("1. Login");
                    System.out.println("2. Register");
                    System.out.println("0. Thoát");
                    System.out.print("Mời lựa chọn (1/2/3/4/5/6/7/8): ");
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

//    private void login() {
//        System.out.println("***FORM LOGIN***");
//        System.out.println("Nhập tài khoản :");
//        String username=Validate.validateString();
//        System.out.println("Nhập mật khẩu");
//        String password=Validate.validateString();
//        Users users=userReponsitory.checkLogin(username,password);
//        if (users==null){
//            System.out.println("Sai tên tài khoản hoặc mật khẩu");
//        }else {
//            checkRoleLogin(users);
//            //Nếu đúng tài khoản
//
//        }
//    }
//    public void checkRoleLogin(Users users){
//        if (users.getRoles().equals(RoleName.ADMIN)){
//            usersConfig.writeFile(Config.URL_USERS_LOGIN,users);
//            //chuyển trang admin
//            new AdminManagement().menuAdmin();
////                System.out.println("chuyển trang admin");
//            System.out.println("Đăng nhập thành công");
//        }else {
//            if (users.isStatus()){
//                usersConfig.writeFile(Config.URL_USERS_LOGIN,users);
//                //chuyển trang user
////                    new UserManagement().menuUserManagement();
//                System.out.println("chuyển trang người dùng");
//                System.out.println("Đăng nhập thành công");
//            }else {
//                System.out.println("Tài khoản bạn đã bị khóa");
//            }
//        }
//    }

//    private void register() {
//        System.out.println("***FORM REGISTER***");
//        Users users = new Users();
//        users.setId(userReponsitory.getNewId());
//        System.out.println("Nhập họ và tên :");
//        users.setFullName(Validate.validateString());
//        System.out.println("Nhập tài khoản:");
//        while (true) {
//            String username = (Validate.validateString());
//            if (userReponsitory.existsUsername(username)) {
//                System.out.println("Tên đăng nhập đã tồn tại vui lòng nhập lại:");
//            } else {
//                users.setUsername(username);
//                break;
//            }
//
//        }
//        System.out.println("Nhập mật khẩu :");
//        users.setPassword(Validate.validateString());
//        System.out.println("Xác nhận lại mật khẩu :");
//        while (true){
//            String confirmPassword = Validate.validateString();
//            if (users.getPassword().equals(confirmPassword)){
//                break;
//            }else {
//                System.out.println("Mật khẩu không trùng khớp mời nhập lại :");
//            }
//        }
//        System.out.println("Nhập địa chỉ email :");
//        while (true){
//            String email=Validate.validateEmail();
//            if (userReponsitory.existsEmail(email)){
//                System.out.println("Địa email đã tồn tại vui lòng nhập lại :");
//            }else {
//                users.setEmail(email);
//                break;
//            }
//        }
//
//        boolean shouldContinue = true; // Biến để kiểm soát việc tiếp tục vòng lặp
//        while (shouldContinue) {
//            System.out.println("Chọn vai trò (1: ADMIN(nếu có),  0: Bỏ qua): ");
//            int roleChoice = Validate.validateInt();
//
//            switch (roleChoice) {
//                case 1:
//                    String adminPassword;
//                    boolean isAdminConfirmed = false;
//
//                    while (!isAdminConfirmed) {
//                        System.out.println("Nhập mật khẩu xác nhận ADMIN (hoặc nhập '0' để thoát): ");
//                        adminPassword = Validate.validateString();
//
//                        if (adminPassword.equals("admin")) {
//                            users.setRoles(RoleName.ADMIN);
//                            // Lưu vào cơ sở dữ liệu
//                            userReponsitory.save(users);
//                            System.out.println("Phân quyền ADMIN tài khoản thành công");
//                            isAdminConfirmed = true;
//                        } else if (adminPassword.equals("0")) {
//                            // Nếu người dùng nhập '0', thoát khỏi vòng lặp và quay lại chọn vai trò
//                            shouldContinue = false;
//                            break;
//                        } else {
//                            System.out.println("Mật khẩu xác nhận ADMIN không đúng. Vui lòng nhập lại hoặc nhập '0' để thoát.");
//                        }
//                    }
//                    break;
//                case 0:
//                    shouldContinue = false; // Thoát khỏi vòng lặp
//                    break;
//                default:
//                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại vai trò.");
//            }
//        }
//        System.out.println("Nhập số điện thoại :");
//        users.setPhoneNumber(Validate.validatePhone());
//        userReponsitory.save(users);
//        System.out.println("Tạo mới tài khoản thành công");
//        login();
//    }
}
