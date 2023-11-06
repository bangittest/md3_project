package ra.service;

import ra.config.Config;
import ra.model.Products;
import ra.reponsitory.ProductReponsitory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductService implements ProductReponsitory {
    static Config<List<Products>> config=new Config<>();
    public static List<Products>productsList;
    static {
        productsList=config.readFile(Config.URL_PRODUCTS);
        if (productsList==null){
            productsList=new ArrayList<>();
        }
    }
    @Override
    public List<Products> findAll() {
        return productsList;
    }

    @Override
    public void save(Products products) {
        if (findById(products.getId())==null){
            productsList.add(products);
            updateData();
        }else {
            productsList.set(productsList.indexOf(products),products);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        productsList.remove(findById(id));
        updateData();
    }

    @Override
    public Products findById(int id) {
        for (Products product: productsList) {
            if (product.getId()==id){
                return product;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax=0;
        for (Products product:
             productsList) {
            if (product.getId()>idMax){
                idMax= product.getId();
            }
        }
        return idMax+1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCTS,productsList);
    }

    @Override
    public List<Products> findByName(String productName) {
        List<Products> matchingProducts = new ArrayList<>();
        for (Products product : productsList) {
            if (product.getProductName().toLowerCase().contains(productName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }


    @Override
    public boolean checkProductName(String productName) {
        for (Products product:productsList) {
            if (product.getProductName().equalsIgnoreCase(productName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Products> findByCategoryId(int categoryId) {
        List<Products> productsUpdateId = new ArrayList<>();

        for (Products product : productsList) {
            if (product.getCategoryId().getId() == categoryId) {
                productsUpdateId.add(product);
            }
        }
        return productsUpdateId;
    }

    @Override
    public void updateStock(int productId, int newStock) {
        for (Products product : productsList) {
            if (product.getId() == productId) {
                product.setStock(newStock);
                updateData();
                break; // Dừng sau khi đã tìm thấy sản phẩm cần cập nhật
            }
        }
    }
}
