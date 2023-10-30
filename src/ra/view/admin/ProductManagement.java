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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductManagement {
    ProductReponsitory productReponsitory = new ProductService();
    CatalogsReponsitory catalogsReponsitory = new CatalogsService();
    private int pageSize = 5;
    private int currentPage = 1;
    String resetColor = "\u001B[0m"; // Đặt màu về màu mặc định

    Config<Users>config=new Config<>();
    Users users=config.readFile(Config.URL_USERS_LOGIN);

    public void menuProductManagement() {
        do {
            System.out.println("\u001B[33m"); // Màu vàng
            System.out.println("Xin Chào " + users.getFullName());
            System.out.println("\u001B[35m╔════════════════════════════ PRODUCT MANAGEMENT ════════════════════════════╗");
            System.out.println("\u001B[36m║                        1. Hiển thị tất cả sản phẩm                         ║");
            System.out.println("\u001B[36m║                        2. Thêm sản phẩm                                    ║");
            System.out.println("\u001B[36m║                        3. Sửa sản phẩm                                     ║");
            System.out.println("\u001B[36m║                        4. Xóa sản phẩm                                     ║");
            System.out.println("\u001B[36m║                        5. Sắp xếp giá sản phẩm                             ║");
            System.out.println("\u001B[36m║                        6. Tìm kiếm tên sản phẩm                            ║");
            System.out.println("\u001B[31m║                        0. Thoát                                            ║");
            System.out.println("\u001B[33m╚════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("\u001B[33mMời lựa chọn (0/1/2/3/4/5/6/): ");

            System.out.println(resetColor); // Reset màu
            switch (Validate.validateInt()) {
                case 1:
                    showProduct(currentPage, pageSize);
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
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void searchProductName() {
        System.out.println("Nhập tên sản phẩm cần tìm kiếm:");
        String searchKey = Validate.validateString();
        List<Products> searchProducts = productReponsitory.findByName(searchKey);
        if (searchProducts.isEmpty()) {
            System.out.println("Không tìm thấy tên danh mục,danh mục bị rỗng");
        } else {
            System.out.println("Tất cả các sản phẩm tìm thấy là:");
            System.out.println(searchProducts);
        }
    }

    private void sortPriceProduct() {
        System.out.println("Tất cả các sản phẩm sau khi sắp xếp:");
        productReponsitory.sortProduct();
        for (Products product : productReponsitory.findAll()) {
            System.out.println(product);
        }
    }

    private void deleteProduct() {
        System.out.println("Nhập ID cần xóa sản phẩm");
        int idDelete = Validate.validateInt();

        Products productToDelete = productReponsitory.findById(idDelete);
        if (productToDelete == null) {
            System.out.println("Sản phẩm cần xóa theo mã ID vừa nhập không tồn tại");
        } else {
            productReponsitory.delete(idDelete);
            System.out.println("Xóa thành công");
        }
    }


    private void editProduct() {
        System.out.println("Mời nhập ID sản phẩm cần sửa :");
        int idEdit = Validate.validateInt();
        Products productsEdit = productReponsitory.findById(idEdit);
        if (productsEdit == null) {
            System.out.println("Danh muc cần sửa theo mã ID vừa nhập không tồn tại");
        } else {
            System.out.println(productsEdit);
            System.out.println("1.Sửa tên sản phẩm");
            System.out.println("2.Sửa tên danh mục sản phẩm");
            System.out.println("3.Sửa mô tả sản phẩm");
            System.out.println("4.Sửa giá tiền sản phẩm");
            System.out.println("5.Sửa số lượng sản phẩm");
            System.out.println("6.Sửa trạng thái của sản phẩm");
            System.out.println("7.Quay lại");
            switch (Validate.validateInt()) {
                case 1:
                    System.out.println("Mời bạn nhập tên sản phẩm mới:");
                    productsEdit.setProductName(Validate.validateString());
                    productReponsitory.save(productsEdit);
                    System.out.println("Sửa thành công tên sản phẩm");
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

                    break;
                case 3:
                    System.out.println("Mời bạn nhập mô tả sản phẩm mới:");
                    productsEdit.setDescription(Validate.validateString());
                    productReponsitory.save(productsEdit);
                    System.out.println("Sửa thành công mô tả sản phẩm");
                    break;
                case 4:
                    System.out.println("Mời bạn nhập giá tiền mới:");
                    productsEdit.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));
                    productReponsitory.save(productsEdit);
                    System.out.println("Sửa thành công giá tiền");
                    break;
                case 5:
                    System.out.println("Mời bạn nhập số lượng sản phẩm mới:");
                    productsEdit.setStock(Validate.validateInt());
                    productReponsitory.save(productsEdit);
                    System.out.println("Sửa thành công số lượng sản phẩm");
                    break;
                case 6:
                    productsEdit.setStatus(!productsEdit.isStatus());
                    productReponsitory.save(productsEdit);
                    System.out.println("Sửa trạng thái sản phẩm thành công");
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại");
            }
        }

    }

    private void addProduct() {

        System.out.println("Nhập số lượng sản phẩm cần thêm :");
        int n = Validate.validateInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhập số lượng sản phẩm " + (i + 1));
            Products products = new Products();
            products.setId(productReponsitory.getNewId());
            System.out.println("Nhập tên sản phẩm");
            products.setProductName(Validate.validateString());
            System.out.println("Lựa chọn danh mục");
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
            System.out.println("Nhập giá sản phẩm:");
            products.setUnitPrice(Double.parseDouble(Config.scanner().nextLine()));
            System.out.println("Nhập số lượng sản phẩm");
            products.setStock(Validate.validateInt());
            System.out.println("Nhập trạng thái sản phẩm");
            products.setStatus(Boolean.parseBoolean(Config.scanner().nextLine()));
            productReponsitory.save(products);
        }
        System.out.println("Thêm mới sản phẩm thành công");

    }


    private void showProduct(int currentPage, int pageSize) {
        while (true) {
            List<Products> allProducts = productReponsitory.findAll();
            int totalProducts = allProducts.size();

            if (totalProducts == 0) {
                System.out.println("Danh mục sản phẩm rỗng");
                return; // Thoát khỏi vòng lặp nếu không có sản phẩm nào để hiển thị
            }

            // Đảo ngược thứ tự của sản phẩm
//            Collections.reverse(allProducts);

            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPages) {
                currentPage = totalPages;
            }

            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalProducts);

            System.out.println("Page " + currentPage + " of " + totalPages);
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(allProducts.get(i));
//                Products product = allProducts.get(i);
//                Catalogs catalog = product.getCategoryId();
//                if (catalog != null && !catalog.isStatus()) {
//                    System.out.println(product);
//                }
            }

            System.out.print("9. Trang trước | 0. Trang kế | Q. Thoát");
            System.out.println();
            System.out.print("Mời lựa chọn: ");
            String choice = Validate.validateString();
            if (choice.equals("Q") || choice.equals("q")) {
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
