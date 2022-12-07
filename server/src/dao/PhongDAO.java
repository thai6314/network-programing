/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Phong;
import sql.Sql;
/**
 *
 * @author thaidaovan
 */
public class PhongDAO extends DAO{
    private Sql sql;
    public PhongDAO(){
        sql = new Sql();
    }
    public boolean them(Phong p){
        try {
            PreparedStatement ps1 = (PreparedStatement) connect.prepareStatement(sql.FIND_PHONG_BY_NAME);
            ps1.setString(1, p.getTen());
            ResultSet rs1 = (ResultSet)ps1.executeQuery();
            if(rs1.next()){
                return false;
            }
            else{
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.INSERT_PHONG);
                ps.setString(1, p.getTen());
                ps.setString(2, p.getMoTa());
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    } 
    public boolean sua(Phong p){
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.FIND_PHONG_BY_ID);
            ps.setInt(1,p.getId());
            ResultSet rs = (ResultSet) ps.executeQuery();
            if(rs.next()){
                PreparedStatement ps1 = (PreparedStatement) connect.prepareStatement(sql.UPDATE_PHONG);
                ps1.setString(1, p.getTen());
                ps1.setString(2, p.getMoTa());
                ps1.setInt(3, p.getId());
                ps1.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean xoa(int id){
        try {
            PreparedStatement ps1 = (PreparedStatement) connect.prepareStatement(sql.FIND_PHONG_BY_ID);
            ps1.setInt(1, id);
            ResultSet rs1 = (ResultSet)ps1.executeQuery();
            if(rs1.next()){
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.DELETE_PHONG);
                ps.setInt(1, id);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Phong> getAllPhong(){
        List<Phong> list = new ArrayList<>();
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.GET_ALL_PHONG);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while(rs.next()){
                Phong p = new Phong();
                p.setId(rs.getInt("ID"));
                p.setTen(rs.getString("Ten"));
                p.setMoTa(rs.getString("MoTa"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Phong> searchPhong(String str){
        List<Phong> list = new ArrayList<>();
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.SEARCH_PHONG+"'%"+str+"%'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Phong p = new Phong();
                p.setId(rs.getInt("ID"));
                p.setTen(rs.getString("Ten"));
                p.setMoTa(rs.getString("MoTa"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
