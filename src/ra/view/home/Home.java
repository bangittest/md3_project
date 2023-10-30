package ra.view.home;

import ra.config.Validate;
import ra.model.account.RoleName;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;
import ra.view.admin.AdminManagement;

public class Home {
    UserReponsitory userReponsitory=new UserService();
    public static Users usersLogin;

    public void menuHome() {

                do {
                    System.out.println("**********************HOME************************");
                    System.out.println("1. Login");
                    System.out.println("2. Register");
                    System.out.println("0. Thoát");
                    System.out.print("Mời lựa chọn (1/2/3/4/5/6/7/8): ");
                    switch (Validate.validateInt()) {
                        case 1:
                            login();
                            break;
                        case 2:
                            register();
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

    private void login() {
        System.out.println("***FORM LOGIN***");
        System.out.println("Nhập tài khoản :");
        String username=Validate.validateString();
        System.out.println("Nhập mật khẩu");
        String password=Validate.validateString();
        Users users=userReponsitory.checkLogin(username,password);
        if (users==null){
            System.out.println("Sai tên tài khoản hoặc mật khẩu");
        }else {
            //Nếu đúng tài khoản
            if (users.getRoles().equals(RoleName.ADMIN)){
                usersLogin=users;
                //chuyển trang admin
                new AdminManagement().menuAdmin();
//                System.out.println("chuyển trang admin");
                System.out.println("Đăng nhập thành công");
            }else {
                if (users.isStatus()){
                    usersLogin=users;
                    //chuyển trang user
//                    new UserManagement().menuUserManagement();
                    System.out.println("chuyển trang người dùng");
                    System.out.println("Đăng nhập thành công");
                }else {
                    System.out.println("Tài khoản bạn đã bị khóa");
                }
            }
        }
    }

    private void register() {
        System.out.println("***FORM REGISTER***");
        Users users = new Users();
        users.setId(userReponsitory.getNewId());
        System.out.println("Nhập họ và tên :");
        users.setFullName(Validate.validateString());
        System.out.println("Nhập tài khoản:");
        while (true) {
            String username = (Validate.validateString());
            if (userReponsitory.existsUsername(username)) {
                System.out.println("Tên đăng nhập đã tồn tại vui lòng nhập lại:");
            } else {
                users.setUsername(username);
                break;
            }

        }
        System.out.println("Nhập mật khẩu :");
        users.setPassword(Validate.validateString());
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
        System.out.println("Chọn vai trò (1: ADMIN, 2: USER): ");
        switch (Validate.validateInt()) {
            case 1:
                System.out.println("Nhập mật khẩu xác nhận ADMIN: ");
                String adminPassword = Validate.validateString();

                if (adminPassword.equals("admin")) {
                    users.setRoles(RoleName.ADMIN);
                    // Lưu vào cơ sở dữ liệu
                    userReponsitory.save(users);
                    System.out.println("Phân quyền ADMIN tài khoản thành công");
                } else {
                    System.out.println("Mật khẩu xác nhận ADMIN không đúng. Không thể phân quyền ADMIN.");
                }
                break;
            case 2:
                users.setRoles(RoleName.USER);
                userReponsitory.save(users);
                System.out.println("Phân quyền USER tài khoản thành công");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ. Phân quyền không thành công.");
        }
        System.out.println("Nhập số điện thoại :");
        users.setPhoneNumber(Validate.validatePhone());
        userReponsitory.save(users);
        System.out.println("Tạo mới tài khoản thành công");
        login();

    }
}
