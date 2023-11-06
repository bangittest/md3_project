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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ra.config.Color.*;

public class ProductManagement {
    ProductReponsitory productReponsitory = new ProductService();
    CatalogsReponsitory catalogsReponsitory = new CatalogsService();
    private int pageSize = 5;
    private int currentPage = 1;

    Config<Users> config = new Config<>();
    Users users = config.readFile(Config.URL_USERS_LOGIN);

    public void menuProductManagement() {
        do {
//            System.out.println("Xin Chào " + users.getFullName());
            System.out.println("\u001B[35m╔════════════════════════════ PRODUCT MANAGEMENT ════════════════════════════╗");
            System.out.println("\u001B[36m║                        1. Hiển thị tất cả sản phẩm                         ║");
            System.out.println("\u001B[36m║                        2. Thêm sản phẩm                                    ║");
            System.out.println("\u001B[36m║                        3. Sửa sản phẩm                                     ║");
            System.out.println("\u001B[36m║                        4. Xóa sản phẩm                                     ║");
            System.out.println("\u001B[36m║                        5. Sắp xếp giá sản phẩm giảm dần                    ║");
            System.out.println("\u001B[36m║                        6. Tìm kiếm tên sản phẩm                            ║");
            System.out.println("\u001B[36m║                        7. Đổi trạng thái sản phẩm                          ║");
            System.out.println("\u001B[31m║                        0. Quay lại                                         ║");
            System.out.println("\u001B[33m╚════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("\u001B[33mMời lựa chọn (0/1/2/3/4/5/6/): ");
            System.out.println(RESET);
            switch (Validate.validateInt()) {
                case 1:
                    showProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    sortPriceProduct();
                    break;
                case 6:
                    searchProductName();
                    break;
                case 7:
                    statusProduct();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng chọn lại." + RESET);
                    break;
            }
        } while (true);
    }

    private void statusProduct() {
        System.out.println("Mời nhập ID sản phẩm cần sửa :");
        int idEdit = Validate.validateInt();
        Products productsEdit = productReponsitory.findById(idEdit);
        if (productsEdit == null) {
            System.out.println("Mã sản phẩm cần sửa theo mã ID vừa nhập không tồn tại");
        } else {
            Validate.ValidateToString();
            System.out.println(productsEdit);
            while (true) {
                System.out.println("""
                        Bạn có muốn thay đổi trạng thái
                        1.Có
                        2.Không
                        Mời bạn lựa chọn:
                        """);
                switch (Validate.validateInt()) {
                    case 1:
                        productsEdit.setStatus(!productsEdit.isStatus());
                        productReponsitory.save(productsEdit);
                        System.out.println("Sửa trạng thái sản phẩm thành công");
                        Validate.ValidateToString();
                        System.out.println(productsEdit);
                        return;
                    case 2:
                        return;
                    default:
                        System.out.println("Lua chon khong hop le");
                        break;
                }
            }
        }
    }

    private void searchProductName() {
        System.out.println("Nhập tên sản phẩm cần tìm kiếm:");
        String searchKey = Validate.validateString();
        System.out.println("Tất cả các sản phẩm tìm thấy là:");
        Validate.ValidateToString();
        boolean check = true;
        for (Products products : productReponsitory.findAll()) {
            if (products.getProductName().toLowerCase().contains(searchKey)) {
                System.out.println(products);
                check = false;
            }
        }
        if (check) {
            System.out.println(RED + "Không tìm thấy tên sản phẩm hoặc danh mục sản phẩm bị rỗng!!!" + RESET);
        }
    }

    private void sortPriceProduct() {
        System.out.println("Tất cả các sản phẩm sau khi sắp xếp:");

        // Lấy danh sách sản phẩm và sao chép nó sang một danh sách mới
        List<Products> productsList = productReponsitory.findAll();
        List<Products> sortedProducts = new ArrayList<>(productsList);

        // Sắp xếp danh sách sản phẩm
        sortedProducts.sort(new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return (int) (o2.getUnitPrice() - o1.getUnitPrice());
            }
        });

        // Hiển thị danh sách đã sắp xếp
        Validate.ValidateToString();
        for (Products product : sortedProducts) {
            System.out.println(product);
        }

        // Lưu danh sách đã sắp xếp vào một tệp tin mới
        Config<List<Products>> config1 = new Config<>();
        config1.writeFile(Config.URL_PRODUCTS_SORT, sortedProducts);
    }

    private void deleteProduct() {
        System.out.println("Nhập ID cần xóa sản phẩm");
        int idDelete = Validate.validateInt();

        Products productToDelete = productReponsitory.findById(idDelete);
        if (productToDelete == null) {
            System.out.println(RED + "Sản phẩm cần xóa theo mã ID vừa nhập không tồn tại" + RESET);
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
                        productReponsitory.delete(idDelete);
                        System.out.println("Xóa thành công");
                    case 2:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        break;
                }
            }
        }
    }

        private void editProduct () {
            System.out.println("Mời nhập ID sản phẩm cần sửa :");
            int idEdit = Validate.validateInt();
            Products productsEdit = productReponsitory.findById(idEdit);
            if (productsEdit == null) {
                System.out.println("Danh muc cần sửa theo mã ID vừa nhập không tồn tại");
            } else {
                Validate.ValidateToString();
                System.out.println(productsEdit);
                System.out.println("\u001B[35m-----------------------------------------------------------------------------");
                System.out.println("\u001B[35m╔══════════════════════════   Cập nhật sản phẩm  ═══════════════════════════╗");
                System.out.println("\u001B[35m║                       \u001B[36m1. Sửa tên sản phẩm                                  \u001B[35m║");
                System.out.println("\u001B[35m║                       \u001B[36m2. Sửa tên danh mục sản phẩm                         \u001B[35m║");
                System.out.println("\u001B[35m║                       \u001B[36m3. Sửa mô tả sản phẩm                                \u001B[35m║");
                System.out.println("\u001B[35m║                       \u001B[36m4. Sửa giá tiền của sản phẩm                         \u001B[35m║");
                System.out.println("\u001B[35m║                       \u001B[36m5. Sửa số lượng của sản phẩm                         \u001B[35m║");
                System.out.println("\u001B[35m║                       \u001B[31m0. Quay lại                                          \u001B[35m║");
                System.out.println("\u001B[35m╚════════════════════════════════════════════════════════════════════════════╝" + RESET);
                System.out.print("Mời lựa chọn (1/2/3/4/5/6/0): ");
                switch (Validate.validateInt()) {
                    case 1:
                        System.out.println("Mời bạn nhập tên sản phẩm mới:");
                        while (true) {
                            String productName = Validate.validateString();
                            if (productReponsitory.checkProductName(productName)) {
                                System.out.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
                            } else {
                                productsEdit.setProductName(productName);
                                productReponsitory.save(productsEdit);
                                System.out.println("Sửa thành công tên sản phẩm");
                                System.out.println(productsEdit);
                                break;
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Lựa chọn danh mục sản phẩm");
                        List<Catalogs> activeCatalogs = new ArrayList<>();
                        List<Catalogs> allCatalogs = catalogsReponsitory.findAll();
                        for (int j = 0; j < allCatalogs.size(); j++) {
                            if (allCatalogs.get(j).isStatus()) {
                                activeCatalogs.add(allCatalogs.get(j));
                                System.out.println((activeCatalogs.size()) + "." + activeCatalogs.get(activeCatalogs.size() - 1).getCatalogName());
                            }
                        }

                        while (true) {
                            int choice = Validate.validateInt();
                            if (choice >= 1 && choice <= activeCatalogs.size()) {
                                productsEdit.setCategoryId(activeCatalogs.get(choice - 1));
                                break;
                            } else {
                                System.out.println("Không có danh mục mời bạn lựa chọn lại");
                            }
                        }
                        productReponsitory.save(productsEdit);
                        System.out.println("Sửa thành công danh mục sản phẩm");
                        System.out.println(productsEdit);
                        break;
                    case 3:
                        System.out.println("Mời bạn nhập mô tả sản phẩm mới:");
                        productsEdit.setDescription(Validate.validateString());
                        productReponsitory.save(productsEdit);
                        System.out.println("Sửa thành công mô tả sản phẩm");
                        Validate.ValidateToString();
                        System.out.println(productsEdit);
                        break;
                    case 4:
//                    System.out.println("Mời bạn nhập giá tiền mới:");
//                    productsEdit.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));
//                    productReponsitory.save(productsEdit);
                        while (true) {
                            System.out.print("Mời bạn nhập giá tiền mới: ");
                            String input = Config.scanner().nextLine();

                            try {
                                double unitPrice = Double.parseDouble(input);
                                if (unitPrice > 0) {
                                    productsEdit.setUnitPrice(unitPrice);
                                    break;
                                } else {
                                    System.out.println("Giá sản phẩm không được âm. Vui lòng nhập lại.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Giá sản phẩm phải là một số. Vui lòng nhập lại.");
                            }
                        }
                        productReponsitory.save(productsEdit);
                        System.out.println("Sửa thành công giá tiền");
                        Validate.ValidateToString();
                        System.out.println(productsEdit);
                        break;
                    case 5:
//                    System.out.println("Mời bạn nhập số lượng sản phẩm mới:");
//                    productsEdit.setStock(Validate.validateInt());
//                    productReponsitory.save(productsEdit);
                        while (true) {
                            System.out.print("Mời bạn nhập số lượng sản phẩm mới: ");
                            int stock = Validate.validateInt();

                            if (stock > 0) {
                                productsEdit.setStock(stock);
                                break;
                            } else {
                                System.out.println("Số lượng sản phẩm phải lớn hơn 0. Vui lòng nhập lại.");
                            }
                        }
                        System.out.println("Sửa thành công số lượng sản phẩm");
                        productReponsitory.save(productsEdit);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại");
                }
            }
        }

        private void addProduct () {

            System.out.println("Nhập số lượng sản phẩm cần thêm :");
            int n = Validate.validateInt();
            for (int i = 0; i < n; i++) {
                System.out.println("Nhập số lượng sản phẩm " + (i + 1));
                Products products = new Products();
                products.setId(productReponsitory.getNewId());
                System.out.println("Nhập tên sản phẩm");
                while (true) {
                    String productName = Validate.validateString();
                    if (productReponsitory.checkProductName(productName)) {
                        System.out.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại");
                    } else {
                        products.setProductName(productName);
                        break;
                    }
                }
                System.out.println("Lựa chọn danh mục sản phẩm");

                // Tạo danh sách để chỉ lưu trữ các danh mục đang hoạt động
                List<Catalogs> activeCatalogs = new ArrayList<>();
                for (int j = 0; j < catalogsReponsitory.findAll().size(); j++) {
                    Catalogs catalog = catalogsReponsitory.findAll().get(j);
                    if (catalog.isStatus()) { // Kiểm tra xem danh mục có đang hoạt động không
                        activeCatalogs.add(catalog);
                        System.out.println((activeCatalogs.size()) + "." + catalog.getCatalogName());
                    }
                }

                if (activeCatalogs.isEmpty()) {
                    System.out.println("Không có danh mục hoạt động mời bạn tạo danh mục hoạt động trước.");
                    return;
                }

                while (true) {
                    int choice = Validate.validateInt();
                    if (choice >= 1 && choice <= activeCatalogs.size()) {
                        products.setCategoryId(activeCatalogs.get(choice - 1));
                        break;
                    } else {
                        System.out.println("Lựa chọn không hợp lệ. Mời bạn lựa chọn lại.");
                    }
                }

                System.out.println("Nhập mô tả sản phẩm :");
                products.setDescription(Validate.validateString());
                while (true) {
                    System.out.print("Nhập giá sản phẩm: ");
                    String input = Validate.validateString();

                    try {
                        double unitPrice = Double.parseDouble(input);
                        if (unitPrice > 0) {
                            products.setUnitPrice(unitPrice);
                            break;
                        } else {
                            System.out.println("Giá sản phẩm không được âm. Vui lòng nhập lại.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Giá sản phẩm phải là một số. Vui lòng nhập lại.");
                    }
                }

                while (true) {
                    System.out.print("Nhập số lượng sản phẩm: ");
                    int stock = Validate.validateInt();

                    if (stock > 0) {
                        products.setStock(stock);
                        break;
                    } else {
                        System.out.println("Số lượng sản phẩm phải lớn hơn 0. Vui lòng nhập lại.");
                    }
                }
                productReponsitory.save(products);
            }
            System.out.println("Thêm mới sản phẩm thành công");

        }


        private void showProduct () {
            List<Products> products = productReponsitory.findAll();
            if (products.isEmpty()) {
                System.out.println("Danh sách sản phẩm rỗng");
            } else {
                System.out.println("Tất cả sản phẩm");
                Validate.ValidateToString();
                for (Products productsList : products) {
                    System.out.println(productsList.toString());
                }
            }
        }
    }
