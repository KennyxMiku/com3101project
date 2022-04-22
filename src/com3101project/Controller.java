/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com3101project;

/**
 *
 * @author kenny
 */
public class Controller {
     private View view;
     private Model model;
     
     public void setView(View v) {
        this.view = v;        
    }

    public void setModel(Model m) {
        this.model = m;        
    }
    /**
     * call from view.editSlot() to model.editOwner()
     * @param id slot id 
     * @param name slot name 
     * @param price slot price 
     * @param owner player id for the new owner of the slot
     */
    public void updateSlot(String id, String name, String price, String owner){
        if (name.equals("")&&price.equals("")) {
            if (owner.equals("1")||owner.equals("2")||owner.equals("3")||owner.equals("4")||owner.equals("0")){
                model.editOwner(id, owner);
                }else{
                viewShowMessage("Invalid owner (should between 0-4, 0 equal to no one own the land.)");
            }
        }else{
            if(owner.equals("")){
                model.editSlot(id, name, price);
            }else{
                model.editSlot(id, name, price);
                if (owner.equals("1")||owner.equals("2")||owner.equals("3")||owner.equals("4")||owner.equals("0")){
                model.editOwner(id, owner);
                }else{
                viewShowMessage("Invalid owner (should between 0-4, 0 equal to no one own the land.)");
            }
            } 
        }
    }
    /**
     * call from view.editPlayer() to model.editPlayer()
     * @param p1Pos player 1 position
     * @param p1Balance player 1 balance
     * @param p1Status player 1 status
     * @param p2Pos player 2 position
     * @param p2Balance player 2 balance
     * @param p2Status player 2 status
     * @param p3Pos player 3 position
     * @param p3Balance player 3 balance
     * @param p3Status player 3 status
     * @param p4Pos player 4 position
     * @param p4Balance player 4 balance
     * @param p4Status player 4 status
     * @param nTurn current player turn
     */
    public void playerUpdate(String p1Pos, String p1Balance, 
            String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, 
            String p3Status, String p4Pos, String p4Balance, 
            String p4Status, String nTurn){
            model.editPlayer(p1Pos, p1Balance, p1Status, p2Pos, p2Balance, 
                    p2Status, p3Pos, p3Balance, p3Status, p4Pos, p4Balance, p4Status, nTurn);
    }
    
    /**
     * point to rollDice() in model
     */
    public void rollDice(){
        model.rollDice();
    }
    
    /**
     * call from model.updatePlayerPosition() to view.update()
     * @param p1Pos player 1 position
     * @param p1Balance player 1 balance
     * @param p1Status player 1 status
     * @param p2Pos player 2 position
     * @param p2Balance player 2 balance
     * @param p2Status player 2 status
     * @param p3Pos player 3 position
     * @param p3Balance player 3 balance
     * @param p3Status player 3 status
     * @param p4Pos player 4 position
     * @param p4Balance player 4 balance
     * @param p4Status player 4 status
     * @param nTurn current player turn
     */
    public void viewUpdate(String p1Pos, String p1Balance, 
            String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, 
            String p3Status, String p4Pos, String p4Balance, 
            String p4Status, String nTurn){
            view.update(p1Pos, p1Balance, p1Status, p2Pos, p2Balance, p2Status, 
                    p3Pos, p3Balance, p3Status, p4Pos, p4Balance, p4Status, nTurn);
        
    }
    
    /**
     * print massage
     * @param msg massage
     */
    public void viewShowMessage(String msg){
        view.showMessage(msg);
    }
    
    /**
     * show the slots are owned by which player
     */
    public void lookForSlotOwned(){
        String slot=model.showSlotOwned();
        view.showMessage(slot);
    }

    /**
     * call from model.buyLand() to view.buy()
     * @param slotId slot id
     * @param price slot price
     * @param name slot name
     * @return the choice from view to model
     */
    public boolean buy(int slotId, String price, String name){
        boolean choice=view.buy(slotId, price, name);
        return choice;  
    }
    
    /**
     * call from view.tradeBuy() and view.tradeSell()
     * @param buyer player id for buyer
     * @param seller player id for seller
     * @param slotId slot id of trading
     * @param price price of the slot in this trading 
     */
    public void trade(String buyer, String seller, String slotId, String price){
        model.trade(buyer,seller,slotId,price);
    }
    
}
