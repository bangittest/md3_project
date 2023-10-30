package ra.reponsitory;

import java.util.List;

public interface Responsitory<T> {
    List<T> findAll();

  void save(T t);
  void delete(int t);
  T findById(int id);
  int getNewId();
  void updateData();
}
