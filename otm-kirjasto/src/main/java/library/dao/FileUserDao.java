
package library.dao;

import java.sql.SQLException;
import java.util.List;

public interface FileUserDao<T, K> {
    
    T findByUsername(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    T save(T object) throws SQLException;
    void delete(K key) throws SQLException;
    void deleteAll() throws SQLException;
}
