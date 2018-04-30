/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Book;
import domain.User;

/**
 *
 * @author AM
 */
public class BookDao implements Dao<Book, Integer>{

    private String dataBaseName;
    
    public BookDao(String dataBaseName ) {
        this.dataBaseName = dataBaseName;
    }
    
    public int idOfLastBook() throws SQLException {
        List<Book> lista = findAll();
        if(lista.isEmpty()) {
          return -1;  
        }
        return lista.get(lista.size() - 1).getId();
    }
    
    @Override
    public Book findByUsername(Integer key) throws SQLException {
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
    
    public List<Book> findAllBorrowed() throws SQLException {
        List<Book> lista = findAll();
        List<Book> list = new ArrayList<>();
        lista.stream().forEach(k -> {
            if(!k.getOmistaja().equals("admin")) {
                list.add(k);
            }
        });
        return list;
    }
    
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
    
//    public long howManyFreeCopiesOf(Book book) {
//        List all = new ArrayList();
//        try {
//            all = findAllFree();
//        } catch (SQLException ex) {
//            Logger.getLogger(BookDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return all.stream().filter(k -> k.equals(book)).count();
//    }
    
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

    @Override
    public Book save(Book uusiKirja) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        List<Book> list = findAll();
        for (Book book : list) {
            if(book.equals(uusiKirja)) {
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

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Book WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void changeOwner(Book book, User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET owner=? WHERE id=?");
        stmt.setString(1, user.getName());
        stmt.setInt(2, book.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void returnBook(Book book) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET owner='admin' WHERE id=?");
        stmt.setInt(1, book.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void deleteAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Book");
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
