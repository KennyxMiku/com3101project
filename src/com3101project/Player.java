/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com3101project;
import java.util.*;
/**
 *
 * @author kenny
 */
public class Player {
    
    private String id;
    private int balance;
    private boolean status;
    private int position;
    private ArrayList slotOwned;
    
    public Player(String id,int balance, boolean status, int position, ArrayList slot){
    this.id=id;
    this.balance=balance;
    this.status=status;
    this.position=position;
    slotOwned=slot;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList getSlotOwned() {
        return slotOwned;
    }

    public void setSlotOwned(ArrayList slotOwned) {
        this.slotOwned = slotOwned;
    }
   
}
