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
    
    public void newGame(){
        model.newGame();
    }
    
    public void updateSlot(String id, String name, String price, String owner){
        model.editSlot(id, name, price);
        if(owner.equals("")){
        }else{
            if (owner.equals("1")||owner.equals("2")||owner.equals("3")||owner.equals("4")||owner.equals("0")){
                model.editOwner(id, owner);
                }else{
                viewShowMessage("invalid owner (0-4, 0 equal to no one own the land.)");
            }
        }  
    }
    
    public void playerUpdate(String p1Pos, String p1Balance, 
            String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, 
            String p3Status, String p4Pos, String p4Balance, 
            String p4Status, String nTurn){
            model.editPlayer(p1Pos, p1Balance, p1Status, p2Pos, p2Balance, p2Status, p3Pos, p3Balance, p3Status, p4Pos, p4Balance, p4Status, nTurn);
    }
    
    public void rollDice(){
        model.rollDice();
    }
    
    public void viewUpdate(String p1Pos, String p1Balance, 
            String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, 
            String p3Status, String p4Pos, String p4Balance, 
            String p4Status, String nTurn){
            view.update(p1Pos, p1Balance, p1Status, p2Pos, p2Balance, p2Status, p3Pos, p3Balance, p3Status, p4Pos, p4Balance, p4Status, nTurn);
        
    }
    
    public void viewShowMessage(String msg){
        view.showMessage(msg);
    }
    
    public void lookForSlotOwned(){
        String slot=model.showSlotOwned();
        view.showMessage(slot);
    }

    public boolean buy(int slotId, String price){
        boolean choice=view.buy(slotId, price);
        return choice;  
    }
}
