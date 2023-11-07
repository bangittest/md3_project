package ra.view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.RoleName;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;
import static ra.config.Color.*;

import java.util.List;

public class UserManagement {
    UserReponsitory userReponsitory = new UserService();

    Config<Users>config=new Config<>();
    Users users=config.readFile(Config.URL_USERS_LOGIN);
    public void menuUserManagement() {

        do {
//            System.out.println("Xin Chào " + users.getFullName());
            System.out.println("\u001B[35m╔════════════════════════════  USER MANAGEMENT  ═════════════════════════════╗");
            System.out.println("\u001B[35m║                       \u001B[36m1. Hiển thị danh sách người dùng                     \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Đổi trạng thái tài khoản                          \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m3. Phân quyền                                        \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m4. Tìm kiếm tên tài khoản khách hàng                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("Mời lựa chọn (0/1/2/3/4): ");
            switch (Validate.validateInt()) {
                case 1:
                    showUser();
                    break;
                case 2:
                    blockUser();
                    break;
                case 3:
                    rolesUser();
                    break;
                case 4:
                    searchUserName();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void searchUserName() {
        System.out.println("Nhập tên tài khoản cần tìm kiếm : ");
        String searchUserName = Validate.validateString();
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
        boolean check=true;
        for (Users user : userReponsitory.findAll()) {
            if (user.getUsername().toLowerCase().contains(searchUserName)) {
                System.out.println(user);
                check = false;
            }
        }
        if (check){
            System.out.println(RED+"Không tìm thấy tên tài khoản hoặc danh sách bị rỗng" +searchUserName+RESET);
        }
    }

    private void rolesUser() {
        System.out.println("Nhập ID tài khoản cần phân quyền:");
        int idRoles = Validate.validateInt();
        if (idRoles == 0) {
            System.out.println(RED+"ID không hợp lệ. Không thể chỉnh sửa."+RESET);
            return;
        }

        Users users = userReponsitory.findById(idRoles);
        if (users == null) {
            System.out.println(RED+"Tài khoản cần sửa theo mã ID vừa nhập không tồn tại"+RESET);
            return;
        }

        System.out.println(users);

//        System.out.println("Nhập mã xác thực để cấp quyền ADMIN (ví dụ: 'admin_key'): ");
//        String adminKey = Validate.validateString();

//        if (adminKey.equals("admin")) { // Thay "admin_key" bằng mã xác thực thực sự
            System.out.println("Chọn vai trò (1: ADMIN ,0:Thoát): ");
            int choice = Validate.validateInt();

            switch (choice) {
                case 1:
                    System.out.println("Nhập mật khẩu xác nhận ADMIN: ");
                    String adminPassword = Validate.validateString();

                    if (adminPassword.equals("admin")) {
                        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
                        System.out.println(users);
                        users.setRoles(RoleName.ADMIN);
                        // Lưu vào cơ sở dữ liệu
                        userReponsitory.save(users);
                        System.out.println("Phân quyền ADMIN tài khoản thành công");
                        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
                        System.out.println(users);
                    } else {
                        System.out.println(RED+"Mật khẩu xác nhận ADMIN không đúng. Không thể phân quyền ADMIN."+RESET);
                    }
                    break;
                case 0:
                    return;
//                case 2:
//                    System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
//                            "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
//                    System.out.println(users);
//                    users.setRoles(RoleName.USER);
//                    userReponsitory.save(users);
//                    System.out.println("Phân quyền USER tài khoản thành công");
//                    System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
//                            "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
//                    System.out.println(users);
//                    break;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Phân quyền không thành công."+RESET);
            }
//        } else {
//            System.out.println(RED+"Mã xác thực không đúng. Không thể phân quyền ADMIN."+RESET);
//        }
    }


    private void deleteUser() {
        showUser();
        System.out.println("Nhập ID cần xóa tài khoản: ");
        int idDelete = Validate.validateInt();

        if (idDelete == 0) {
            System.out.println(RED+"ID không hợp lệ. Không thể xóa."+RESET);
            return;
        }

        Users userToDelete = userReponsitory.findById(idDelete);

        if (userToDelete == null) {
            System.out.println(RED+"Tài khoản cần xóa theo mã ID vừa nhập không tồn tại"+RESET);
        } else {
            while (true){
                System.out.println("""
                    Bạn có chắc chắn muốn xóa không?
                    1.Có
                    2.Không
                    Mời bạn lựa chọn
                    """);
                // Xóa tài khoản người dùng
                switch (Validate.validateInt()){
                    case 1:
                        userReponsitory.delete(idDelete);
                        System.out.println("Xóa tài khoản thành công");
                    case 2:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        break;
                }
            }
        }
    }


    private void blockUser() {
        System.out.println("Nhập id tài khoản cần thay đổi trạng thái:");
        int idBlock = Validate.validateInt();
        if (idBlock == 0) {
            System.out.println("ID không hợp lệ. Không thể chỉnh sửa.");
            return;
        }

        Users users = userReponsitory.findById(idBlock);
        if (users == null) {
            System.out.println("Tài khoản cần sửa theo mã ID vừa nhập không tồn tại");
            return;
        }
        if (users.getRoles()==RoleName.ADMIN){
            System.out.println("Tài khoản có quyền ADMIN. Không thể khóa");
        } else {
            System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                    "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
            System.out.println(users);
           while (true){
               System.out.println("""
                    Bạn có chắc chắn thay đổi trạng thái không
                    1.Có
                    2.Không
                    Mời bạn lựa chọn
                    """);
               switch (Validate.validateInt()){
                   case 1:
                       users.setStatus(!users.isStatus());
                       userReponsitory.save(users);
                       System.out.println("Sửa trạng thái tài khoản thành công");
                       System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                               "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
                       System.out.println(users);
                       return;
                   case 2:
                       return;
                   default:
                       System.out.println("Lựa chọn không hợp lệ");
                       break;
               }
           }
        }
    }

    private void showUser() {
        if (userReponsitory.findAll().isEmpty()) System.out.println("Danh sách khách hàng rỗng");
        System.out.println("Tất cả danh sách khách hàng :");
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s %-20s %-20s %-20s%n" + RESET,
                "ID","Tài khoản","Email","Họ và tên","Trạng thái","Phân quyền","Số điện thoại");
        for (Users user : userReponsitory.findAll()) {
            System.out.println(user);
        }
    }
}
