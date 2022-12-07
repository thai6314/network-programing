/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author thaidaovan
 */
public class DAO {
    public static Connection connect;

    public DAO() {
        String dbUrl = "jdbc:mysql://localhost:3306/qltaisan";
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            connect = DriverManager.getConnection(dbUrl, "root", "thai0211");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
