/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author thaidaovan
 */
public class Message<T> implements Serializable {

    private String message;
    private T t;
    private List<T> list;

    public Message(String message, T t) {
        this.message = message;
        this.t = t;
    }

    public Message(String message, List<T> list) {
        this.message = message;
        this.list = list;
    }

    public Message() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

}
