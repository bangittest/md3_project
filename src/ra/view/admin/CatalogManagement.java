package ra.view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Catalogs;
import ra.model.Products;
import ra.model.account.Users;
import ra.reponsitory.CatalogsReponsitory;
import ra.reponsitory.ProductReponsitory;
import ra.service.CatalogsService;
import ra.service.ProductService;
import ra.view.home.Home;

import java.util.Collections;
import java.util.List;

public class CatalogManagement {
    private CatalogsReponsitory catalogsReponsitory = new CatalogsService();
    private ProductReponsitory productReponsitory = new ProductService();

    public void menuCategoryManagement() {
        int pageSize = 5;
        int currentPage = 1;
        String resetColor = "\u001B[0m"; // Đặt màu về màu mặc định

        System.out.println("\u001B[33m"); // Màu vàng
        Config<Users> config = new Config<>();
        Users users = config.readFile(Config.URL_USERS_LOGIN);

        do {
            System.out.println("Xin Chào " + users.getFullName());
            System.out.println("\u001B[35m╔═══════════════════════════ CATALOGS MANAGEMENT ════════════════════════════╗");
            System.out.println("\u001B[36m║                   1. Hiển thị tất cả danh mục sản phẩm                     ║");
            System.out.println("\u001B[36m║                   2. Thêm danh mục sản phẩm                                ║");
            System.out.println("\u001B[36m║                   3. Sửa danh mục sản phẩm                                 ║");
            System.out.println("\u001B[36m║                   4. Xóa danh mục sản phẩm                                 ║");
            System.out.println("\u001B[36m║                   5. Sắp xếp danh mục sản phẩm                             ║");
            System.out.println("\u001B[36m║                   6. Tìm kiếm danh mục sản phẩm                            ║");
            System.out.println("\u001B[31m║                   0. Thoát                                                 ║");
            System.out.println("\u001B[33m╚════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("\u001B[33mMời lựa chọn (1/2/3/4/5/6/7/0): ");

            System.out.println(resetColor); // Reset màu


            switch (Validate.validateInt()) {
                case 1:
                    showCatalogs(currentPage, pageSize);
                    break;
                case 2:
                    addCataLog();
                    break;
                case 3:
                    editCatalog();
                    break;
                case 4:
                    deleteCatalog();
                    break;
                case 5:
                    sortCatalog();
                    break;
                case 6:
                    seachCatalog();
                    break;
//                case 7:
//                    statusCatalog();
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

//    private void statusCatalog() {
//        System.out.println("Mời nhập ID danh mục cần sửa trang thai :");
//        int idStatus = Validate.validateInt();
//        Catalogs catalogsEdit = catalogsReponsitory.findById(idStatus);
//        if (catalogsEdit == null) {
//            System.out.println("Danh mục cần sửa theo mã ID vừa nhập không tồn tại");
//        } else {
//            catalogsEdit.setStatus(!catalogsEdit.isStatus());
//            catalogsReponsitory.save(catalogsEdit);
//            System.out.println("Sửa trạng thái danh mục sản phẩm thành công");
//        }
//    }

    private void seachCatalog() {
        System.out.println("Nhập tên danh mục bạn cần tìm kiếm :");
        String searchKey = Validate.validateString();
        List<Catalogs> searchCatalog = catalogsReponsitory.findByName(searchKey);
        if (searchCatalog.isEmpty()) {
            System.out.println("Không tìm thấy danh mục nào phù hợp với tìm kiếm của bạn.");
        } else {
            System.out.println("Tìm thấy danh mục:");
            for (Catalogs catalog : searchCatalog) {
                System.out.println(catalog);
            }
        }
    }

    private void sortCatalog() {
        System.out.println("Danh sách sau khi đã sắp xếp:");
        catalogsReponsitory.sortCatalog();
        showCatalogs(1, 5); //Hiển thị trang đầu tiên sau khi sắp xếp
    }

    private void deleteCatalog() {
        System.out.println("Nhập ID danh mục cần xóa:");
        int idDelete = Validate.validateInt();

        Catalogs catalogsDelete = catalogsReponsitory.findById(idDelete);
        if (catalogsDelete == null) {
            System.out.println("Danh mục theo ID vừa nhập không tồn tại");
            return;
        }

        List<Products> productsList = productReponsitory.findAll();

        boolean checkProduct = false;
        for (Products product : productsList) {
            if (product.getCategoryId().getId() == idDelete) {
                checkProduct = true;
                break;
            }
        }

        if (checkProduct) {
            System.out.println("Danh mục đã tồn tại sản phẩm. Không thể xóa.");
        } else {
            catalogsReponsitory.delete(idDelete);
            System.out.println("Xóa danh mục thành công");
        }
    }

    private void editCatalog() {
        System.out.println("Mời nhập ID danh mục cần sửa :");
        int idEdit = Validate.validateInt();
        Catalogs catalogsEdit = catalogsReponsitory.findById(idEdit);
        if (catalogsEdit == null) {
            System.out.println("Danh mục cần sửa theo mã ID vừa nhập không tồn tại");
            return;
        }
        List<Products> productsList = productReponsitory.findAll();

        boolean checkProduct = false;
        for (Products product : productsList) {
            if (product.getCategoryId().getId() == idEdit) {
                checkProduct = true;
                break;
            }
        }

        if (checkProduct) {
            System.out.println("Danh mục đã tồn tại sản phẩm. Không thể sửa.");
        }else {
            System.out.println(catalogsEdit);
            System.out.println("1.Sửa tên danh mục");
            System.out.println("2.Sửa mô tả danh mục");
            System.out.println("3.Sửa trạng thái danh mục");
            System.out.println("4.Quay lại");
            System.out.print("Mời chọn tùy chọn (1/2/3/4): ");
            int editChoice = Validate.validateInt();

            switch (editChoice) {
                case 1:
                    System.out.println("Mời bạn nhập tên danh mục mới:");
                    catalogsEdit.setCatalogName(Validate.validateString());
                    catalogsReponsitory.save(catalogsEdit);
                    System.out.println("Sửa thành công tên danh mục");
                    break;
                case 2:
                    System.out.println("Mời bạn nhập tên mô tả danh mục cần sửa:");
                    catalogsEdit.setDescription(Validate.validateString());
                    catalogsReponsitory.save(catalogsEdit);
                    System.out.println("Sửa thành công mô tả danh mục");
                    break;
                case 3:
                    catalogsEdit.setStatus(!catalogsEdit.isStatus());
                    catalogsReponsitory.save(catalogsEdit);
                    System.out.println("Sửa trạng thái danh mục sản phẩm thành công");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại");
            }
        }
    }

    private void addCataLog() {
        System.out.println("Nhập số lượng danh mục cần thêm :");
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhập số lượng danh mục " + (i + 1));
            Catalogs catalogs = new Catalogs();
            catalogs.setId(catalogsReponsitory.getNewId());
            System.out.println("Nhập tên danh mục sản phẩm : ");
            catalogs.setCatalogName(Validate.validateString());
            System.out.println("Nhập mô tả sản phẩm danh mục :");
            catalogs.setDescription(Validate.validateString());
            System.out.println("Nhập trạng thái danh mục");
            catalogs.setStatus(Boolean.parseBoolean(Config.scanner().nextLine()));
            catalogsReponsitory.save(catalogs);
        }
        System.out.println("Thêm mới thành công");
    }

        private void showCatalogs(int currentPage, int pageSize) {
        List<Catalogs> allCatalogs = catalogsReponsitory.findAll();
        int totalCatalogs = allCatalogs.size();

        if (totalCatalogs == 0) {
            System.out.println("Danh mục sản phẩm rỗng");
            return;
        }
//        Collections.reverse(allCatalogs);
        int totalPages = (int) Math.ceil((double) totalCatalogs / pageSize);

        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        while (true) {  // Sử dụng vòng lặp while để phân trang liên tục
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCatalogs);

            System.out.println("Page " + currentPage + " of " + totalPages);
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(allCatalogs.get(i));
            }

            System.out.print("9. Trang trước | 0. Trang kế | Q. Thoát");
            System.out.println();
            System.out.print("Mời lựa chọn: ");
            String choice = Validate.validateString();

            if (choice.equalsIgnoreCase("Q")) {
                break;
            }

            int intChoice = Integer.parseInt(choice);
            switch (intChoice) {
                case 9:
                    if (currentPage > 1) {
                        currentPage--;
                    }
                    break;
                case 0:
                    if (currentPage < totalPages) {
                        currentPage++;
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        }
    }
}


