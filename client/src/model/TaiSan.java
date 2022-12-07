/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author thaidaovan
 */
public class TaiSan implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    private int id;
    private String ten;
    private String loaiTS;
    private String viTri;
    private float giaTri;
    private String tenPhong;

    public TaiSan(int id, String ten, String loaiTS, String viTri, float giaTri, String tenPhong) {
        this.id = id;
        this.ten = ten;
        this.loaiTS = loaiTS;
        this.viTri = viTri;
        this.giaTri = giaTri;
        this.tenPhong = tenPhong;
    }

    public TaiSan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoaiTS() {
        return loaiTS;
    }

    public void setLoaiTS(String loaiTS) {
        this.loaiTS = loaiTS;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public float getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(float giaTri) {
        this.giaTri = giaTri;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
    
    public Object[] toObject(){
        return new Object[]{
            id, ten, loaiTS, viTri, giaTri, tenPhong
        };
    }
}
