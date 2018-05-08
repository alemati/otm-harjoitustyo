
package library.dao;

import java.sql.SQLException;
import java.util.List;
import library.domain.Book;
import library.domain.User;

public interface FileBookDao<T, K> {
    
    T findById(K key) throws SQLException;
    T save(T object) throws SQLException;
    int idOfLastBook() throws SQLException;
    List<T> findAllFree() throws SQLException;
    List<T> findAllBorrowed() throws SQLException;
    List<T> findAllWhichBelongsTo(User user) throws SQLException;
    List<T> findAll() throws SQLException;
    void delete(K key) throws SQLException;
    void changeOwner(Book book, User user) throws SQLException;
    void returnBook(Book book) throws SQLException;
    void deleteAll() throws SQLException;
}
