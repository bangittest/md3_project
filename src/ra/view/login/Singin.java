package ra.view.login;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.RoleName;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;
import ra.view.admin.AdminManagement;

public class Singin {
    UserReponsitory userReponsitory=new UserService();
    Config<Users>usersConfig=new Config<>();
    public void login() {
        System.out.println("***FORM LOGIN***");
        System.out.println("Nhập tài khoản :");
        String username= Validate.validateString();
        System.out.println("Nhập mật khẩu");
        String password=Validate.validateString();
        Users users=userReponsitory.checkLogin(username,password);
        if (users==null){
            System.out.println("Sai tên tài khoản hoặc mật khẩu");
        }else {
            checkRoleLogin(users);
            //Nếu đúng tài khoản

        }
    }
    public void checkRoleLogin(Users users){
        if (users.getRoles().equals(RoleName.ADMIN)){
            usersConfig.writeFile(Config.URL_USERS_LOGIN,users);
            //chuyển trang admin
            new AdminManagement().menuAdmin();
//                System.out.println("chuyển trang admin");
            System.out.println("Đăng nhập thành công");
        }else {
            if (users.isStatus()){
                usersConfig.writeFile(Config.URL_USERS_LOGIN,users);
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
