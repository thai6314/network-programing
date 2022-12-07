/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import view.LoginFrm;

/**
 *
 * @author thaidaovan
 */
public class ClientControl {
    private Socket socket;
    public ClientControl() {
    }
    public void check(){
        System.out.println(socket.getInetAddress()+ "  "+socket.getPort());
    }
    public void openConnect(){
        try {
            socket = new Socket("localhost", 6314);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeConnect(){
        try{
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessage(Message message) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Message receiveMessage() {
        Message message = new Message();
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
