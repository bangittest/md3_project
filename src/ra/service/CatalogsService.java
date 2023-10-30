package ra.service;

import ra.config.Config;
import ra.model.Catalogs;
import ra.reponsitory.CatalogsReponsitory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogsService implements CatalogsReponsitory {
    static Config<List<Catalogs>>config=new Config<>();
    public static List<Catalogs>catalogsList;
    static {
        catalogsList=config.readFile(Config.URL_CATALOGS);
        if (catalogsList==null){
            catalogsList=new ArrayList<>();
        }
    }
    @Override
    public List<Catalogs> findAll() {
        return catalogsList;
    }

    @Override
    public void save(Catalogs catalogs) {
        if (findById(catalogs.getId())==null){
            catalogsList.add(catalogs);
            updateData();
        }else {
            catalogsList.set(catalogsList.indexOf(catalogs),catalogs);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        catalogsList.remove(findById(id));
        updateData();
    }

    @Override
    public Catalogs findById(int id) {
        for (Catalogs catalog : catalogsList) {
            if (catalog.getId()==id){
                return catalog;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax=0;
        for (Catalogs catalog:catalogsList) {
            if (catalog.getId()>idMax){
                idMax=catalog.getId();
            }
        }
        return idMax+1;
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATALOGS,catalogsList);
    }

    @Override
    public List<Catalogs> findByName(String catalogName) {
        List<Catalogs> matchingCatalogs = new ArrayList<>();
        for (Catalogs catalogs : catalogsList) {
            if (catalogs.getCatalogName().contains(catalogName)) {
                matchingCatalogs.add(catalogs);
            }
        }
        return matchingCatalogs;
    }

    @Override
    public void sortCatalog() {
        Collections.sort(catalogsList, new Comparator<Catalogs>() {
            @Override
            public int compare(Catalogs o1, Catalogs o2) {
                return o1.getCatalogName().compareTo(o2.getCatalogName());
            }
        });
    }
}
