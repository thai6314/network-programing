/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ClientControl;
import controller.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Phong;
import model.TaiSan;

/**
 *
 * @author thaidaovan
 */
public class QuanLyFrm extends javax.swing.JFrame implements ActionListener{
    DefaultTableModel model, modelTS;
    private ClientControl clientCtr;
    public QuanLyFrm() {
        initComponents();
        setLocationRelativeTo(null);
        txtMa.setEditable(false);
        txtMaTS.setEditable(false);
        model = (DefaultTableModel) jTable1.getModel();
        modelTS = (DefaultTableModel) jTable2.getModel();

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSearchPhong.addActionListener(this);
        
        btnThemTS.addActionListener(this);
        btnSuaTS.addActionListener(this);
        btnXoaTS.addActionListener(this);
        btnLamMoiTS.addActionListener(this);
        btnSearchTaiSan.addActionListener(this);
        
        sapXep.removeAllItems();
        sapXep.addItem("Giá trị giảm dần");
        sapXep.addItem("Giá trị tăng dần");
        
        btnSapXep.addActionListener(this);
    }

    public void sapXep(){
        int row = jTable2.getRowCount();
        List<TaiSan> listTS = new ArrayList<>();
        for(int i=0;i<row;i++){
            int id = Integer.parseInt(jTable2.getValueAt(i, 0).toString());
            String ten = jTable2.getValueAt(i, 1).toString();
            String loaiTS = jTable2.getValueAt(i, 2).toString();
            String viTri = jTable2.getValueAt(i, 3).toString();
            Float giaTri = Float.parseFloat(jTable2.getValueAt(i, 4).toString());
            String tenPhong = jTable2.getValueAt(i, 5).toString();
            TaiSan ts = new TaiSan(id, ten, loaiTS, viTri, giaTri, tenPhong);
            listTS.add(ts);
        }
        if(sapXep.getSelectedIndex() == 0){
            Collections.sort(listTS, new Comparator<TaiSan>(){
                @Override
                public int compare(TaiSan o1, TaiSan o2) {
                    if(o1.getGiaTri() < o2.getGiaTri()){
                        return 1;
                    }else if(o1.getGiaTri() == o2.getGiaTri()){
                        return 0;
                    }
                    else return -1;
                }
            });
            showListTaiSan(listTS);
        }
        if(sapXep.getSelectedIndex() == 1){
            Collections.sort(listTS, new Comparator<TaiSan>(){
                @Override
                public int compare(TaiSan o1, TaiSan o2) {
                    if(o1.getGiaTri() < o2.getGiaTri()){
                        return -1;
                    }else if(o1.getGiaTri() == o2.getGiaTri()){
                        return 0;
                    }
                    else return 1;
                }
            });
            showListTaiSan(listTS);
        }
    }   
    public void comboBox(List<Phong>listP){
        String[] selections = {};
        List<String>listTenPhong = new ArrayList<>();
        jComboBox1.removeAllItems();
        for(Phong p : listP){
            listTenPhong.add(p.getTen());
            jComboBox1.addItem(p.getTen());
        }
    }
    public void showListPhong(List<Phong> list){
        model.setRowCount(0);
        for(Phong p : list){
            model.addRow(p.toObject());
        }
    }
    public void showListTaiSan(List<TaiSan> list){
        modelTS.setRowCount(0);
        for(TaiSan ts : list){
            modelTS.addRow(ts.toObject());
        }
    }
    public void showMessage(String str){
        JOptionPane.showMessageDialog(rootPane, str);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnThem)){              
            if(txtTen.getText().length() != 0 && txtMoTa.getText().length() != 0){
                Phong phong = new Phong();
                phong.setTen(txtTen.getText());
                phong.setMoTa(txtMoTa.getText());
                clientCtr.check();
                clientCtr.sendMessage(new Message("THEM_PHONG",phong));
                
                Message message = clientCtr.receiveMessage();
                if(message.getMessage().equals("ERROR")){
                    JOptionPane.showMessageDialog(rootPane, "Tên phòng đã tồn tại!");
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Thêm phòng thành công!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Tên, mô tả không được để trống!");
            }
        }
        if(e.getSource().equals(btnSua)){            
            if(txtTen.getText().length() != 0 && txtMoTa.getText().length() != 0){
                Phong phong = new Phong();
                phong.setId(Integer.parseInt(txtMa.getText()));
                phong.setTen(txtTen.getText());
                phong.setMoTa(txtMoTa.getText());
                clientCtr.sendMessage(new Message("SUA_PHONG",phong));
                
                Message message = clientCtr.receiveMessage();
                if(message.getMessage().equals("ERROR_ID")){
                    JOptionPane.showMessageDialog(rootPane, "Phòng không tồn tại!");
                }
                else JOptionPane.showMessageDialog(rootPane, "Sửa phòng thành công!");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Tên, mô tả không được để trống!");
            }
        }
        if(e.getSource().equals(btnXoa)){
            if(txtTen.getText().length() != 0 && txtMoTa.getText().length() != 0){
                Phong phong = new Phong();
                phong.setId(Integer.parseInt(txtMa.getText()));
                phong.setTen(txtTen.getText());
                phong.setMoTa(txtMoTa.getText());
                clientCtr.sendMessage(new Message("XOA_PHONG",phong));
                
                Message message = clientCtr.receiveMessage();
                if(message.getMessage().equals("ERROR_ID")){
                    JOptionPane.showMessageDialog(rootPane, "Phòng không tồn tại!");
                }else JOptionPane.showMessageDialog(rootPane, "Xoá phòng thành công!");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Chọn phòng cần xoá!");
            }
        }
        if(e.getSource().equals(btnLamMoi)){
            clientCtr.sendMessage(new Message("GET_LIST_PHONG",null));
            
            Message message = clientCtr.receiveMessage();
            List<Phong> list = message.getList();
            model.setRowCount(0);
            for(Phong p : list){
                model.addRow(p.toObject());
            }
            comboBox(list);
            txtSearchPhong.setText("");
        }
        if(e.getSource().equals(btnSearchPhong)){
            String s = txtSearchPhong.getText().trim();
            if(s.length() != 0){
                clientCtr.sendMessage(new Message("SEARCH_PHONG",s));
                
                List<Phong> list = clientCtr.receiveMessage().getList();
                System.out.println(list.get(0).getTen());
                if(list != null){
                    showListPhong(list);
                }
                else{
                    showMessage("Không có phòng này!");
                }
            }
        }
        if(e.getSource().equals(btnThemTS)){
            String id = txtMaTS.getText();
            String ten = txtTenTS.getText();
            String loaiTS = txtLoaiTS.getText();
            String viTri = txtViTri.getText();
            String giaTri = txtGiaTri.getText();
            String tenPhong = (String)jComboBox1.getSelectedItem();
            if(ten.length() != 0 && loaiTS.length() != 0 && viTri.length() != 0
                   && viTri.length() != 0 && giaTri.length() != 0){
                TaiSan ts = new TaiSan();
                ts.setTen(ten);
                ts.setLoaiTS(loaiTS);
                ts.setViTri(viTri);
                ts.setGiaTri(Float.parseFloat(giaTri));
                ts.setTenPhong(tenPhong);
                clientCtr.sendMessage(new Message("THEM_TAISAN",ts));
                
                Message message = clientCtr.receiveMessage();
                if(message.getMessage().equals("ERROR")){
                    JOptionPane.showMessageDialog(rootPane, "Tài sản đã tồn tại!");
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Thêm tài sản thành công!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Các trường thông tin không được để trống!");
            }
        }
        if(e.getSource().equals(btnSuaTS)){
            String id = txtMaTS.getText();
            String ten = txtTenTS.getText();
            String loaiTS = txtLoaiTS.getText();
            String viTri = txtViTri.getText();
            String giaTri = txtGiaTri.getText();
            String tenPhong = (String)jComboBox1.getSelectedItem();
            if(ten.length() != 0 && loaiTS.length() != 0 && viTri.length() != 0
                   && viTri.length() != 0 && giaTri.length() != 0){
                TaiSan ts = new TaiSan();
                ts.setId(Integer.parseInt(id));
                ts.setTen(ten);
                ts.setLoaiTS(loaiTS);
                ts.setViTri(viTri);
                ts.setGiaTri(Float.parseFloat(giaTri));
                ts.setTenPhong(tenPhong);
                clientCtr.sendMessage(new Message("SUA_TAISAN",ts));
                
                Message message = clientCtr.receiveMessage();
                if(message.getMessage().equals("ERROR_ID")){
                    JOptionPane.showMessageDialog(rootPane, "Tài sản không tồn tại!");
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Sửa tài sản thành công!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Các trường thông tin không được để trống!");
            }
        }
        if(e.getSource().equals(btnXoaTS)){
            String id = txtMaTS.getText();
            String ten = txtTenTS.getText();
            String loaiTS = txtLoaiTS.getText();
            String viTri = txtViTri.getText();
            String giaTri = txtGiaTri.getText();
            String tenPhong = (String)jComboBox1.getSelectedItem();
            if(ten.length() != 0 && loaiTS.length() != 0 && viTri.length() != 0
                   && viTri.length() != 0 && giaTri.length() != 0){
                TaiSan ts = new TaiSan();
                ts.setId(Integer.parseInt(id));
                ts.setTen(ten);
                ts.setLoaiTS(loaiTS);
                ts.setViTri(viTri);
                ts.setGiaTri(Float.parseFloat(giaTri));
                ts.setTenPhong(tenPhong);
                clientCtr.sendMessage(new Message("XOA_TAISAN",ts));
                
                Message message = clientCtr.receiveMessage();
                if(message.getMessage().equals("ERROR_ID")){
                    JOptionPane.showMessageDialog(rootPane, "Tài sản không tồn tại!");
                }else JOptionPane.showMessageDialog(rootPane, "Xoá tài sản thành công!");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Chọn tài sản cần xoá!");
            }
        }
        if(e.getSource().equals(btnLamMoiTS)){
            System.out.println("click lam moi tai san");
            clientCtr.sendMessage(new Message("GET_LIST_TAISAN",null));
            
            Message message = clientCtr.receiveMessage();
            List<TaiSan> list = message.getList();
            modelTS.setRowCount(0);
            for(TaiSan ts : list){
                modelTS.addRow(ts.toObject());
            }
            txtSearchTS.setText("");
            title.setText("Danh sách tài sản");
        }
        if(e.getSource().equals(btnSearchTaiSan)){
            String s = txtSearchTS.getText().trim();
            if(s.length() != 0){
                clientCtr.sendMessage(new Message("SEARCH_TAISAN",s));
                
                List<TaiSan> list = clientCtr.receiveMessage().getList();
                title.setText("Danh sách tài sản: "+list.get(0).getTenPhong());
                if(list != null){
                    showListTaiSan(list);
                    jComboBox1.setSelectedItem(list.get(0).getTenPhong());
                }
                else{
                    showMessage("Không có tài sản này!");
                }
            }
        }
        if(e.getSource().equals(btnSapXep)){
            sapXep();
        }
    }   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtSearchPhong = new javax.swing.JTextField();
        btnSearchPhong = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMaTS = new javax.swing.JTextField();
        txtTenTS = new javax.swing.JTextField();
        txtLoaiTS = new javax.swing.JTextField();
        btnThemTS = new javax.swing.JButton();
        btnSuaTS = new javax.swing.JButton();
        btnXoaTS = new javax.swing.JButton();
        btnLamMoiTS = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtViTri = new javax.swing.JTextField();
        txtGiaTri = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtSearchTS = new javax.swing.JTextField();
        btnSearchTaiSan = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        sapXep = new javax.swing.JComboBox<>();
        btnSapXep = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel6.setText("Mô tả");

        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        txtTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenActionPerformed(evt);
            }
        });

        txtMoTa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMoTaActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoá");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel2.setText("Danh sách phòng");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Mô tả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Phòng");

        jLabel4.setText("Mã");

        btnLamMoi.setText("Làm mới");

        jLabel5.setText("Tên");

        txtSearchPhong.setText("Nhập tên phòng");
        txtSearchPhong.setName(""); // NOI18N

        btnSearchPhong.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(txtSearchPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearchPhong)
                .addContainerGap(296, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchPhong))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(147, 147, 147))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(108, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMoTa, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtMa)
                                            .addComponent(txtTen)))))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnXoa))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLamMoi)
                            .addComponent(btnSua)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phòng", jPanel1);

        jLabel7.setText("Loại tài sản");

        txtMaTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaTSActionPerformed(evt);
            }
        });

        txtTenTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenTSActionPerformed(evt);
            }
        });

        txtLoaiTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoaiTSActionPerformed(evt);
            }
        });

        btnThemTS.setText("Thêm");

        btnSuaTS.setText("Sửa");
        btnSuaTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTSActionPerformed(evt);
            }
        });

        btnXoaTS.setText("Xoá");

        btnLamMoiTS.setText("Làm mới");

        title.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        title.setText("Danh sách tài sản");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Loại tài sản", "Vị trí hiện tại", "Giá trị", "Phòng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel9.setText("Tài sản");

        jLabel10.setText("Mã");

        jLabel11.setText("Tên");

        jLabel12.setText("Vị trí");

        txtViTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViTriActionPerformed(evt);
            }
        });

        txtGiaTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTriActionPerformed(evt);
            }
        });

        jLabel13.setText("Giá trị");

        txtSearchTS.setName(""); // NOI18N

        btnSearchTaiSan.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(txtSearchTS, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchTaiSan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchTaiSan))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Phòng");

        sapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSapXep.setText("Sắp xếp");
        btnSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(title)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(97, 97, 97))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel10)
                                                .addComponent(jLabel11))
                                            .addComponent(jLabel7))
                                        .addGap(9, 9, 9)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLoaiTS)
                                            .addComponent(txtTenTS)
                                            .addComponent(txtMaTS)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel1))
                                        .addGap(42, 42, 42)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnLamMoiTS, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(41, 41, 41))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(btnThemTS, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnXoaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnSuaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(43, 43, 43))
                                            .addComponent(txtGiaTri, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtViTri)
                                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSapXep)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sapXep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtMaTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLoaiTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaTS)
                            .addComponent(btnThemTS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaTS)
                            .addComponent(btnLamMoiTS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSapXep))
                        .addGap(7, 7, 7))))
        );

        jTabbedPane1.addTab("Tài sản", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void txtTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenActionPerformed

    private void txtMoTaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMoTaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMoTaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        txtMa.setText(jTable1.getValueAt(row, 0).toString());
        txtTen.setText(jTable1.getValueAt(row, 1).toString());
        txtMoTa.setText(jTable1.getValueAt(row, 2).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtGiaTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTriActionPerformed

    private void txtViTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtViTriActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();
        txtMaTS.setText(jTable2.getValueAt(row, 0).toString());
        txtTenTS.setText(jTable2.getValueAt(row, 1).toString());
        txtLoaiTS.setText(jTable2.getValueAt(row, 2).toString());
        txtViTri.setText(jTable2.getValueAt(row, 3).toString());
        txtGiaTri.setText(jTable2.getValueAt(row, 4).toString());
        jComboBox1.setSelectedItem(jTable2.getValueAt(row, 5).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void btnSuaTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaTSActionPerformed

    private void txtLoaiTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoaiTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoaiTSActionPerformed

    private void txtTenTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenTSActionPerformed

    private void txtMaTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaTSActionPerformed

    private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSapXepActionPerformed

    public void setClientCtr(ClientControl clientCtr) {
        this.clientCtr = clientCtr;
    }

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyFrm().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiTS;
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnSearchPhong;
    private javax.swing.JButton btnSearchTaiSan;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaTS;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemTS;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaTS;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> sapXep;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtLoaiTS;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaTS;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSearchPhong;
    private javax.swing.JTextField txtSearchTS;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenTS;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables

}
