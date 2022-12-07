/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.TaiSan;
import sql.Sql;

/**
 *
 * @author thaidaovan
 */
public class TaiSanDAO extends DAO {
    private Sql sql;
    public TaiSanDAO() {
        sql = new Sql();
    }
    public boolean them(TaiSan ts) {
        try {
            PreparedStatement ps1 = (PreparedStatement) connect.prepareStatement(sql.FIND_TAISAN_IN_PHONG);
            ps1.setString(1, ts.getTen());
            ps1.setString(2, ts.getTenPhong());
            ResultSet rs1 = (ResultSet) ps1.executeQuery();
            if (rs1.next()) {
                return false;
            } else {
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.INSERT_TAISAN);
                ps.setString(1, ts.getTen());
                ps.setString(2, ts.getLoaiTS());
                ps.setString(3, ts.getViTri());
                ps.setFloat(4, ts.getGiaTri());
                ps.setString(5, ts.getTenPhong());
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean sua(TaiSan ts) {
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.FIND_TAISAN_BY_ID);
            ps.setInt(1, ts.getId());
            ResultSet rs = (ResultSet) ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps1 = (PreparedStatement) connect.prepareStatement(sql.UPDATE_TAISAN);
                ps1.setString(1, ts.getTen());
                ps1.setString(2, ts.getLoaiTS());
                ps1.setString(3, ts.getViTri());
                ps1.setFloat(4, ts.getGiaTri());
                ps1.setString(5, ts.getTenPhong());
                ps1.setInt(6, ts.getId());
                ps1.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean xoa(int id) {
        try {
            PreparedStatement ps1 = (PreparedStatement) connect.prepareStatement(sql.FIND_TAISAN_BY_ID);
            ps1.setInt(1, id);
            ResultSet rs1 = (ResultSet) ps1.executeQuery();
            if (rs1.next()) {
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.DELETE_TAISAN);
                ps.setInt(1, id);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<TaiSan> getAllTaiSan() {
        List<TaiSan> list = new ArrayList<>();
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.GET_ALL_TAISAN);
            ResultSet rs = (ResultSet) ps.executeQuery();
            while (rs.next()) {
                TaiSan ts = new TaiSan();
                ts.setId(rs.getInt("ID"));
                ts.setTen(rs.getString("Ten"));
                ts.setLoaiTS(rs.getString("LoaiTaiSan"));
                ts.setViTri(rs.getString("ViTri"));
                ts.setGiaTri(rs.getFloat("GiaTri"));
                ts.setTenPhong(rs.getString("TenPhong"));
                list.add(ts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<TaiSan> searchTaiSan(String str) {
        List<TaiSan> list = new ArrayList<>();
        try {
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(sql.SEARCH_TAISAN + "'%" + str + "%'");
            System.out.println(sql.SEARCH_TAISAN + "'%" + str + "%'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TaiSan ts = new TaiSan();
                ts.setId(rs.getInt("ID"));
                ts.setTen(rs.getString("Ten"));
                ts.setLoaiTS(rs.getString("LoaiTaiSan"));
                ts.setViTri(rs.getString("ViTri"));
                ts.setGiaTri(rs.getFloat("GiaTri"));
                ts.setTenPhong(rs.getString("TenPhong"));
                list.add(ts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
