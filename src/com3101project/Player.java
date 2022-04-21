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
    private ArrayList<String> slotOwned = new ArrayList<>();
    
    public Player(String pId,int pBalance, boolean pStatus, int pPosition){
    id=pId;
    balance=pBalance;
    status=pStatus;
    position=pPosition;
    }
    /**
     * remove slot from the slot array
     * @param slot 
     */
    public void removeSlot(String slot){
        slotOwned.remove(slot);
    }
    /**
     * add slot to the slot array
     * @param slot 
     */
    public void addSlot(String slot){
        slotOwned.add(slot);
    }
    /**
     * increacse player balance
     * @param money money need to add
     */
    public void add(int money){
        balance= balance+money;
    }
    /**
     * deduct player balance
     * @param money money need to deuctt
     */
    public void deduct(int money){
        balance= balance-money;
    }
    /**
     * clear the slot array
     */
    public void clear(){
        slotOwned.clear();
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

    public boolean getStatus() {
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
