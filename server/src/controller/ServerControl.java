package controller;

import dao.AccountDAO;
import dao.PhongDAO;
import dao.TaiSanDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Phong;
import model.TaiSan;

/**
 *
 * @author thaidaovan
 */
public class ServerControl {

    protected ServerSocket serverSocket;
    private Socket socket;
    private AccountDAO accDAO;
    private PhongDAO phongDAO;
    private TaiSanDAO taiSanDAO;

    public ServerControl() {
        try {
            accDAO = new AccountDAO();
            phongDAO = new PhongDAO();
            taiSanDAO = new TaiSanDAO();
            serverSocket = new ServerSocket(6314);
            socket = serverSocket.accept();
            while (true) {
                Message message = receiveMessage();
                if (message != null) {
                    switch (message.getMessage()) {
                        case "LOGIN":
                            dangNhap((Account) message.getT());
                            break;
                        case "THEM_TAISAN":
                            themTaiSan((TaiSan) message.getT());
                            break;
                        case "SUA_TAISAN":
                            suaTaiSan((TaiSan) message.getT());
                            break;
                        case "XOA_TAISAN":
                            TaiSan ts = (TaiSan) message.getT();
                            xoaTaiSan(ts.getId());
                            break;
                        case "GET_LIST_TAISAN":
                            getListTaiSan();
                            break;
                        case "SEARCH_TAISAN":
                            String s = (String) message.getT();
                            searchTaiSan(s);
                            break;

                        case "THEM_PHONG":
                            themPhong((Phong) message.getT());
                            break;
                        case "SUA_PHONG":
                            suaPhong((Phong) message.getT());
                            break;
                        case "XOA_PHONG":
                            Phong p = (Phong) message.getT();
                            xoaPhong(p.getId());
                            break;
                        case "GET_LIST_PHONG":
                            getListPhong();
                            break;
                        case "SEARCH_PHONG":
                            String str = (String) message.getT();
                            seachPhong(str);
                            break;

                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
        }
    }

    public Message receiveMessage() {
        Message message = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();
            System.out.println(message.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public void dangNhap(Account acc) {
        if (acc == null || acc.getUsername().equals("") || acc.getPassword().equals("")) {
            sendMessage(new Message("ERROR", null));
        } else {
            Account account = accDAO.dangNhap(acc);
            if (account == null) {
                sendMessage(new Message("ERROR", null));
            } else {
                sendMessage(new Message("OK", acc));
            }
        }

    }

    //Phong
    public void themPhong(Phong phong) {
        if (phongDAO.them(phong)) {
            sendMessage(new Message("OK", null));

        } else {
            sendMessage(new Message("ERROR", null));
        }
    }

    public void xoaPhong(int idPhong) {
        if (phongDAO.xoa(idPhong)) {
            sendMessage(new Message("OK", null));
        } else {
            sendMessage(new Message("ERROR_ID", null));
        }
    }

    public void suaPhong(Phong phong) {
        if (phongDAO.sua(phong)) {
            sendMessage(new Message("OK", null));
        } else {
            sendMessage(new Message("ERROR_ID", null));
        }
    }

    public void getListPhong() {
        List<Phong> listPhong = phongDAO.getAllPhong();
        sendMessage(new Message("LIST_PHONG", listPhong));
    }

    public void seachPhong(String str) {
        List<Phong> listP = phongDAO.searchPhong(str);
        sendMessage(new Message("SEARCH_RESULT", listP));
        System.out.println(listP.get(0).getTen());
    }

    //Tai san
    public void themTaiSan(TaiSan taiSan) {
        if (taiSanDAO.them(taiSan)) {
            sendMessage(new Message("OK", null));

        } else {
            sendMessage(new Message("ERROR", null));
        }
    }

    public void xoaTaiSan(int idTaiSan) {
        if (taiSanDAO.xoa(idTaiSan)) {
            sendMessage(new Message("OK", null));
        } else {
            sendMessage(new Message("ERROR_ID", null));
        }
    }

    public void suaTaiSan(TaiSan taiSan) {
        if (taiSanDAO.sua(taiSan)) {
            sendMessage(new Message("OK", null));
        } else {
            sendMessage(new Message("ERROR_ID", null));
        }
    }

    public void getListTaiSan() {
        List<TaiSan> listTS = taiSanDAO.getAllTaiSan();
        sendMessage(new Message("LIST_TAISAN", listTS));
    }

    public void searchTaiSan(String str) {
        List<TaiSan> listTS = taiSanDAO.searchTaiSan(str);
        sendMessage(new Message("SEARCH_RESULT", listTS));
    }

}
