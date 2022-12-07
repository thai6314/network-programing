package sql;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thaidaovan
 */
public class Sql {
    public String FIND_ACCOUNT = "select * from Account where Username=? and Password=?";
    //phong
    public String INSERT_PHONG = "insert into Phong(Ten, MoTa) value (?,?)";
    public String FIND_PHONG_BY_NAME = "select * from Phong where Ten=?";
    public String FIND_PHONG_BY_ID = "select * from Phong where ID = ?";
    public String GET_ALL_PHONG = "select * from Phong";
    public String UPDATE_PHONG = "update Phong set Ten=?, MoTa=? where ID=?";
    public String DELETE_PHONG = "delete from Phong where ID =?";
    
    //tai san
    public String INSERT_TAISAN = "insert into TaiSan(Ten, LoaiTaiSan, ViTri, GiaTri, TenPhong) value (?,?,?,?,?)";
    public String FIND_TAISAN_IN_PHONG = "select * from TaiSan where Ten=? and TenPhong=?";
    public String FIND_TAISAN_BY_ID = "select * from TaiSan where ID = ?";
    public String GET_ALL_TAISAN = "select * from TaiSan";
    public String UPDATE_TAISAN = "update TaiSan set Ten=?, LoaiTaiSan=?, ViTri=?, GiaTri=?, TenPhong=? where ID=?";
    public String DELETE_TAISAN = "delete from TaiSan where ID =?";

    // Tìm kiếm
    public String SEARCH_PHONG = "select * from Phong where Ten like ";
    public String SEARCH_TAISAN = "select * from TaiSan where TenPhong like ";
}
