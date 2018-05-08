/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.domain.Book;
import library.domain.User;


public class BookDao implements FileBookDao<Book, Integer> { 

    private String dataBaseName;
    
    public BookDao(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }
    
    /**
     * Method return id of last book in database (highest id).
     * 
     * @return int highest id in database for book
     */
    public int idOfLastBook() throws SQLException {
        List<Book> lista = findAll();
        if (lista.isEmpty()) {
            return -1;  
        }
        return lista.get(lista.size() - 1).getId();
    }
    
    /**
     * Method tries to find book by book's id.
     * 
     * @param key id of a book
     * @return either wanted book or null (if there is no book with such id in database)
     */
    @Override
    public Book findById(Integer key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        Book palautus = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("writer"), rs.getInt("year"));
        palautus.setOmistaja(rs.getString("owner"));
        stmt.close();
        rs.close();
        conn.close();
        return palautus;
    }
    
    /**
     * Method returns list of all available (free) books.
     * 
     * @return List of available (free) books in database.
     */
    public List<Book> findAllFree() throws SQLException {
        List<Book> lista = new ArrayList();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE owner='admin'");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Book lisataan = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("writer"), rs.getInt("year"));
            lisataan.setOmistaja(rs.getString("owner"));
            lista.add(lisataan);
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }
    
    /**
     * Method returns list of all borrowed books.
     * 
     * @return List of borrowed books in database.
     */
    public List<Book> findAllBorrowed() throws SQLException {
        List<Book> lista = findAll();
        List<Book> list = new ArrayList<>();
        lista.stream().forEach(k -> {
            if (!k.getOmistaja().equals("admin")) {
                list.add(k);
            }
        });
        return list;
    }
    /**
     * Method returns list of books borrowed by certain user.
     * 
     * @param user User whose books are searched for.
     * @return List of all books borrowed by certain user.
     */
    public ArrayList<Book> findAllWhichBelongsTo(User user) throws SQLException {
        ArrayList<Book> lista = new ArrayList();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book WHERE owner=?");
        stmt.setString(1, user.getName());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Book lisataan = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("writer"), rs.getInt("year"));
            lisataan.setOmistaja(user.getName());
            lista.add(lisataan);
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }
    
    
    /**
     * Method returns list of all books in database.
     * 
     * @return List of all books in database.
     */
    @Override
    public ArrayList<Book> findAll() throws SQLException {
        ArrayList<Book> lista = new ArrayList();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Book");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Book lisataan = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("writer"), rs.getInt("year"));
            lisataan.setOmistaja(rs.getString("owner"));
            lista.add(lisataan);
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }
    
    
    /**
     * Method tries to save new book into database.
     * 
     * @param uusiKirja Book that needs to be saved in database.
     * @return null
     */
    @Override
    public Book save(Book uusiKirja) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        List<Book> list = findAll();
        for (Book book : list) {
            if (book.equals(uusiKirja)) {
                return null;
            }
        }
        PreparedStatement stmt
                = conn.prepareStatement("INSERT INTO Book (name, writer, year, owner) VALUES (?,?,?,?)");
        stmt.setString(1, uusiKirja.getNimi());
        stmt.setString(2, uusiKirja.getKirjailija());
        stmt.setInt(3, uusiKirja.getVuosi());
        stmt.setString(4, uusiKirja.getOmistaja());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return null;
    }
    
    /**
     * Method deletes book from database.
     *
     * @param key id of a book that needs to be deleted
     */
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Book WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    /**
     * Method sets the user to be an owner of the book.
     * 
     * 
     * @param book Book that goes to another owner
     * @param user User to whom book is going to belong
     */
    public void changeOwner(Book book, User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET owner=? WHERE id=?");
        stmt.setString(1, user.getName());
        stmt.setInt(2, book.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    
    /**
     * Method makes book that was borrowed available.
     * 
     * 
     * @param book Book that needs to become available (free)
     */
    public void returnBook(Book book) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET owner='admin' WHERE id=?");
        stmt.setInt(1, book.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    /**
     * Method delete all Book data from database.
     * 
     */
    public void deleteAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Book");
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
