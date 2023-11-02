package ra.reponsitory;

import ra.model.Catalogs;

import java.util.List;

public interface CatalogsReponsitory extends Responsitory<Catalogs>{
    List<Catalogs> findByName(String catalogName);
    boolean checkCatalog(String catalogName);
}
