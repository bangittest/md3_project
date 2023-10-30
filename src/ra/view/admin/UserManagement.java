package ra.view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.account.RoleName;
import ra.model.account.Users;
import ra.reponsitory.UserReponsitory;
import ra.service.UserService;

import java.util.List;

public class UserManagement {
    UserReponsitory userReponsitory = new UserService();

    Config<Users>config=new Config<>();
    Users users=config.readFile(Config.URL_USERS_LOGIN);
    public void menuUserManagement() {

        do {
            System.out.println("Xin Chào " + users.getFullName());
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║              USER MANAGEMENT             ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║ 1. Hiển thị danh sách khách hàng         ║");
            System.out.println("║ 2. BLOCK tài khoản                       ║");
            System.out.println("║ 3. Xóa tài khoản                         ║");
            System.out.println("║ 4. Phân quyền                            ║");
            System.out.println("║ 5. Tìm kiếm tên tài khoản khách hàng     ║");
            System.out.println("║ 0. Quay lại                              ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("Mời lựa chọn (1/2/3/4/5/6/7/8): ");
            switch (Validate.validateInt()) {
                case 1:
                    showUser();
                    break;
                case 2:
                    blockUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    rolesUser();
                    break;
                case 5:
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
        List<Users> searchUser = userReponsitory.findByName(searchUserName);
        if (searchUser.isEmpty()) {
            System.out.println("Không tìm thấy tên tài khoản hoặc danh sách bị rỗng");
        } else {
            System.out.println("Tất cả các tài khoản tìm thấy là:");
            System.out.println(searchUser);
        }

    }

    private void rolesUser() {
        System.out.println("Nhập ID tài khoản cần phân quyền:");
        int idRoles = Validate.validateInt();
        if (idRoles == 0) {
            System.out.println("ID không hợp lệ. Không thể chỉnh sửa.");
            return;
        }

        Users users = userReponsitory.findById(idRoles);
        if (users == null) {
            System.out.println("Tài khoản cần sửa theo mã ID vừa nhập không tồn tại");
            return;
        }

        System.out.println(users);

        System.out.println("Nhập mã xác thực để cấp quyền ADMIN (ví dụ: 'admin_key'): ");
        String adminKey = Validate.validateString();

        if (adminKey.equals("admin")) { // Thay "admin_key" bằng mã xác thực thực sự
            System.out.println("Chọn vai trò (1: ADMIN, 2: USER): ");
            int choice = Validate.validateInt();

            switch (choice) {
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
        } else {
            System.out.println("Mã xác thực không đúng. Không thể phân quyền ADMIN.");
        }
    }


    private void deleteUser() {
        System.out.println("Nhập id cần xóa tài khoản: ");
        int idDelete = Validate.validateInt();
        if (idDelete == 0) {
            System.out.println("ID không hợp lệ. Không thể chỉnh sửa.");
            return;
        }
        Users users = userReponsitory.findById(idDelete);
        if (users == null) {
            System.out.println("Tài khoản cần sửa theo mã ID vừa nhập không tồn tại");
        } else {
            userReponsitory.findById(idDelete);
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
            System.out.println("Tài khoản có quyền ADMIN. Không thể Block");
        } else {
            System.out.println(users);
            users.setStatus(!users.isStatus());
            userReponsitory.save(users);
            System.out.println("Sửa trạng thái tài khoản thành công");
        }
    }


//    private void addUser() {
//        System.out.println("Nhập số lượng tài khoản cần thêm :");
//        int n=Validate.validateInt();
//        for (int i = 0; i <n ; i++) {
//            System.out.println("Nhập số lượng tài khoản: "+(i+1));
//            Users users=new Users();
//            users.setId(userReponsitory.getNewId());
//            System.out.println("Nhập tên tài khoản :");
//            users.setUsername(Validate.validateString());
//            System.out.println("Nhập mật khẩu :");
//            users.setPassword(Validate.validateString());
//            userReponsitory.save(users);
//        }
//        System.out.println("Thêm mới thành công");
//    }

    private void showUser() {
        if (userReponsitory.findAll().isEmpty()) System.out.println("Danh sách khách hàng rỗng");
        System.out.println("Tất cả danh sách khách hàng :");
        for (Users user : userReponsitory.findAll()) {
            System.out.println(user);
        }
    }
}
