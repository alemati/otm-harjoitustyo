/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    T findByUsername(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    T save(T object) throws SQLException;
    void delete(K key) throws SQLException;
}