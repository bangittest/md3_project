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

import static ra.config.Color.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogManagement {
    private CatalogsReponsitory catalogsReponsitory = new CatalogsService();
    private ProductReponsitory productReponsitory = new ProductService();

    public void menuCategoryManagement() {
        int pageSize = 5;
        int currentPage = 1;

        Config<Users> config = new Config<>();
        Users users = config.readFile(Config.URL_USERS_LOGIN);

        do {
//            System.out.println("Xin Chào " + users.getFullName());
            System.out.println("\u001B[35m╔══════════════════════════  CATALOGS MANAGEMENT  ═══════════════════════════╗");
            System.out.println("\u001B[35m║                       \u001B[36m1. Hiển thị tất cả danh mục sản phẩm                 \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m2. Thêm danh mục sản phẩm                            \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m3. Sửa danh mục sản phẩm                             \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m4. Xóa danh mục sản phẩm                             \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m5. Sắp xếp danh mục sản phẩm                         \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m6. Tìm kiếm danh mục sản phẩm                        \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[36m7. Sửa trạng thái danh mục                           \u001B[35m║");
            System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
            System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print("\u001B[33mMời lựa chọn (1/2/3/4/5/6/7/0): " + RESET);

            switch (Validate.validateInt()) {
                case 1:
                    showCatalogs();
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
                case 7:
                    statusCatalog();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void statusCatalog() {
        System.out.println("Mời nhập ID danh mục cần sửa :");
        int idEdit = Validate.validateInt();
        Catalogs catalogsEdit = catalogsReponsitory.findById(idEdit);
        if (catalogsEdit == null) {
            System.out.println("Mã danh mục cần sửa theo mã ID vừa nhập không tồn tại");
        } else {
            System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                    "ID", "Tên danh mục", "Mô tả", "Trạng thái");
            System.out.println(catalogsEdit);
         while (true){
             System.out.println("""
                    Bạn có muốn thay đổi trạng thái không?
                    1.Có
                    2.Không
                    Mời bạn lựa chọn
                    """);
             switch (Validate.validateInt()){
                 case 1:
                     catalogsEdit.setStatus(!catalogsEdit.isStatus());
                     catalogsReponsitory.save(catalogsEdit);
                     System.out.println("Sửa trạng thái danh mục sản phẩm thành công");
                     System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                             "ID", "Tên danh mục", "Mô tả", "Trạng thái");
                     System.out.println(catalogsEdit);
                     return;
                 case 2:
                     return;
                 default:
                     System.out.println("Lựa chọn không hợp lệ");
             }
         }
        }
    }


    private void seachCatalog() {
        System.out.println("Nhập tên danh mục bạn cần tìm kiếm :");
        String searchKey = Validate.validateString();
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
        boolean check=true;
        for (Catalogs catalogs: catalogsReponsitory.findAll()) {
            if (catalogs.getCatalogName().toLowerCase().contains(searchKey)){
                System.out.println(catalogs);
                check=false;
            }
        }
        if (check){
            System.out.println(RED + "Không tìm thấy danh mục nào phù hợp với tìm kiếm của bạn." + RESET);
        }
    }

    private void sortCatalog() {
        System.out.println("Danh sách sau khi đã sắp xếp (a-b):");
        List<Catalogs>catalogsList=catalogsReponsitory.findAll();
        List<Catalogs>sortCatalogs=new ArrayList<>(catalogsList);
        sortCatalogs.sort(new Comparator<Catalogs>() {
            @Override
            public int compare(Catalogs o1, Catalogs o2) {
                return o2.getCatalogName().compareTo(o1.getCatalogName());
            }
        });
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
        for (Catalogs catalogs: sortCatalogs) {
            System.out.println(catalogs);
        }
        //Luu danh sach da sap xep vao 1 tap tin moi
        Config<List<Catalogs>>config1=new Config<>();
        config1.writeFile(Config.URL_CATALOGS_SORT,sortCatalogs);
    }

    private void deleteCatalog() {
        System.out.println("Nhập ID danh mục cần xóa:");
        int idDelete = Validate.validateInt();

        Catalogs catalogsDelete = catalogsReponsitory.findById(idDelete);
        System.out.println(catalogsDelete);
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
            System.out.println(RED + "Danh mục đã tồn tại sản phẩm. Không thể xóa." + RESET);
        } else {
            while (true) {
                System.out.println("""
                        Bạn có chắc chắn muốn xóa không?
                        1.Có
                        2.Không
                        Mời bạn lựa chọn
                        """);
                switch (Validate.validateInt()) {
                    case 1:
                        catalogsReponsitory.delete(idDelete);
                        System.out.println("Xóa danh mục thành công");
                    case 2:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        break;
                }
            }
        }
    }

    private void editCatalog() {
        System.out.println("Mời nhập ID danh mục cần sửa :");
        int idEdit = Validate.validateInt();
        Catalogs catalogsEdit = catalogsReponsitory.findById(idEdit);
        if (catalogsEdit == null) {
            System.out.println(RED + "Danh mục cần sửa theo mã ID vừa nhập không tồn tại" + RESET);
            return;
        }
        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
        System.out.println(catalogsEdit);
        System.out.println("\u001B[35m╔══════════════════════════   Cập nhật danh mục   ═══════════════════════════╗");
        System.out.println("\u001B[35m║                       \u001B[36m1. Sửa tên danh mục                                  \u001B[35m║");
        System.out.println("\u001B[35m║                       \u001B[36m2. Sửa mô tả danh mục                                \u001B[35m║");
        System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
        System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
        System.out.print("\u001B[33mMời lựa chọn (1/2/3/4/0): " + RESET);
        int editChoice = Validate.validateInt();

        switch (editChoice) {
            case 1:
                System.out.println("Mời bạn nhập tên danh mục mới:");

                while (true) {
                    String newCatalogName = Validate.validateString();
                    if (catalogsReponsitory.checkCatalog(newCatalogName)) {
                        System.out.println("Tên danh mục trùng vui lòng nhập lại:");
                    } else {
                        // Lấy danh sách sản phẩm thuộc danh mục cũ
                        List<Products> productsUpdateId = productReponsitory.findByCategoryId(idEdit);

                        catalogsEdit.setCatalogName(newCatalogName);
                        catalogsReponsitory.save(catalogsEdit);
                        System.out.println("Sửa thành công tên danh mục");
                        System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                                "ID", "Tên danh mục", "Mô tả", "Trạng thái");
                        System.out.println(catalogsEdit);

                        // Cập nhật mã danh mục mới cho các sản phẩm thuộc danh mục cũ
                        for (Products product : productsUpdateId) {
                            product.setCategoryId(catalogsEdit);
                            productReponsitory.save(product);
                        }
                        break;
                    }
                }
                break;
            case 2:
                System.out.println("Mời bạn nhập tên mô tả danh mục cần sửa:");
                catalogsEdit.setDescription(Validate.validateString());
                catalogsReponsitory.save(catalogsEdit);
                System.out.println("Sửa thành công mô tả danh mục");
                System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                        "ID", "Tên danh mục", "Mô tả", "Trạng thái");
                System.out.println(catalogsEdit);
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại" + RESET);
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
            while (true) {
                String catalog = Validate.validateString();
                if (catalogsReponsitory.checkCatalog(catalog)) {
                    System.out.println("Danh mục sản phẩm đã tồn tại, vui lòng nhập lại :");
                } else {
                    catalogs.setCatalogName(catalog);
                    break;
                }
            }
            System.out.println("Nhập mô tả sản phẩm danh mục :");
            catalogs.setDescription(Validate.validateString());
            catalogsReponsitory.save(catalogs);
        }
        System.out.println("Thêm mới thành công");
    }

    private void showCatalogs() {
        List<Catalogs> catalogsList = catalogsReponsitory.findAll();
        if (catalogsList.isEmpty()) {
            System.out.println("Danh mục đang bị rỗng");
        } else {
            System.out.println("Tất cả danh mục là : ");
            System.out.printf("\u001B[36m%-10s %-20s %-20s %-20s%n" + RESET,
                    "ID", "Tên danh mục", "Mô tả", "Trạng thái");
            for (Catalogs cat : catalogsList) {
                System.out.println(cat);
            }
        }
    }
}


