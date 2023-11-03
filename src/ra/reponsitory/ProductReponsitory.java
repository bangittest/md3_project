package ra.reponsitory;

import ra.model.Catalogs;
import ra.model.Products;

import java.util.List;

public interface ProductReponsitory extends Responsitory<Products> {
    List<Products> findByName(String productName);
    boolean checkProductName(String productName);
    List<Products> findByCategoryId(int categoryId);
    void updateStock(int productId, int newStock);
}
