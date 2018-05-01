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
import domain.User;

public class UserDao implements Dao<User, String> { 
    
    private String dataBaseName;
    
    
    /**
     * Method tries to find user from database by user's name.
     * 
     * 
     * @param dataBaseName Name of database to which connection is wanted to be done
     */
    public UserDao(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }
    
    /**
     * Method tries to find user from database by user's name.
     * 
     * 
     * @param key Username of user that needs to be found
     * @return either wanted user or null (if there is no such user in database)
     */
    
    @Override
    public User findByUsername(String key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE name = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        User palautus = new User(rs.getString("name"), rs.getString("pass"));
        stmt.close();
        rs.close();
        conn.close();
        return palautus;
    }
    
    /**
     * Method delete certain user from database.
     * 
     * @return List of all users in database
     */

    @Override
    public List<User> findAll() throws SQLException {
        ArrayList<User> lista = new ArrayList();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User lisataan = new User(rs.getString("name"), rs.getString("pass"));
            lista.add(lisataan);
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }
    
    /**
     * Method saves certain user into database if there is no already user with same name in database.
     * 
     * 
     * @param object User that needs to be saven into database
     * @return null
     */
    @Override
    public User save(User object) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        List<User> list = findAll();
        for (User user : list) {
            if (user.getName().equals(object.getName())) {
                return null;
            }
        }
        PreparedStatement stmt
                = conn.prepareStatement("INSERT INTO User (name, pass) VALUES (?,?)");
        stmt.setString(1, object.getName());
        stmt.setString(2, object.getPass());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return null;
    }
    
    /**
     * Method delete certain user from database.
     * 
     * 
     * @param key Username of user that needs to be deleted
     */
    @Override
    public void delete(String key) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE name = ?");
        stmt.setString(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    /**
     * Method delete all User data from database.
     * 
     * 
     */
    public void deleteAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + this.dataBaseName);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User");
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
}
