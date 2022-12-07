/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import controller.ServerControl;
import sql.Sql;

/**
 *
 * @author thaidaovan
 */
public class AccountDAO extends DAO{
    private Sql sql;
    public AccountDAO(){
        sql = new Sql(); 
    }
     public Account dangNhap(Account account){
        try {
            PreparedStatement ps = connect.prepareStatement(sql.FIND_ACCOUNT);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = (ResultSet) ps.executeQuery();
            if(rs != null) return account;
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
