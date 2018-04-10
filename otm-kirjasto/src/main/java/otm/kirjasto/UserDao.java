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

public class UserDao implements Dao<User, String>{
    
    private String dataBaseName;

    public UserDao(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }
    
    @Override
    public User findOne(String key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE name = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        User palautus = new User( rs.getString("name"), rs.getString("pass"));
        stmt.close();
        rs.close();
        conn.close();
        return palautus;
    }

    @Override
    public List<User> findAll() throws SQLException {
        ArrayList<User> lista = new ArrayList();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User lisataan = new User( rs.getString("name"), rs.getString("pass"));
            lista.add(lisataan);
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }

    @Override
    public User saveOrUpdate(User object) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt
                = conn.prepareStatement("INSERT INTO User (name, pass) VALUES (?,?)");
        stmt.setString(1, object.getName());
        stmt.setString(2, object.getPass());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return null;
    }

    @Override
    public void delete(String key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE name = ?");
        stmt.setString(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
}
