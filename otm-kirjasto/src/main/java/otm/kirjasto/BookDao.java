/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otm.kirjasto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AM
 */
public class BookDao implements Dao<Book, Integer>{

    private String dataBaseName;
    
    public BookDao(String dataBaseName ) {
        this.dataBaseName = dataBaseName;
    }
    
    public int viimeisenKirjanId() throws SQLException {
        List<Book> lista = findAll();
        if(lista.isEmpty()) {
          return -1;  
        }
        return lista.get(lista.size() - 1).getId();
    }
    
    @Override
    public Book findOne(Integer key) throws SQLException {
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
    
    public List<Book> findAllVapaat() throws SQLException {
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
    
    public ArrayList<Book> findAllWhere(User user) throws SQLException {
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
    public Book saveOrUpdate(Book uusiKirja) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
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
    
    public void vaihdaOmistaja(Book book, User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET owner=? WHERE id=?");
        stmt.setString(1, user.getName());
        stmt.setInt(2, book.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void palauttaaKirja(Book book) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET owner='admin' WHERE id=?");
        stmt.setInt(1, book.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
