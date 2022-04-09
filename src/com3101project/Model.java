package com3101project;
import java.io.*;
import java.net.URL;
import java.util.*;
/**
 *
 * @author kenny
 */
public class Model {
    Controller control;
    int turn=1;
    Player[ ] players;
    Slot[] slots;
    
    public void setController(Controller c) {
        this.control = c;
    }
    
    public void newGame(){
    players = new Player[5];
    players [1]=new Player("1",2000,false,0);
    players [2]=new Player("2",2000,false,0);
    players [3]=new Player("3",2000,false,0);
    players [4]=new Player("4",2000,false,0);
    load();
    testPrint();
    }
  
    public void load(){
    slots = new Slot[23];
    String line = "";
    String splitBy = ",";
    int slotNum=-1;
    try {
        URL url = getClass().getResource("slot.csv");
      BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
      while ((line = br.readLine()) != null)
      {
        String[] slot = line.split(splitBy);
        if(slotNum>=0){
        slots[slotNum]= new Slot(slot[0],slot[1],slot[2]);
        }
        slotNum++;
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    }

    public void testPrint(){
    for(int i=1;i<=4; i++){
        System.out.println(players[i].getId()+players[i].getBalance()+players[i].getStatus()+players[i].getPosition()+players[i].getSlotOwned().toString());
    }   
    for(int i=0;i<23;i++){
        System.out.println(slots[i].getId()+slots[i].getName()+slots[i].getPrice()+slots[i].getOwner());
    }
    }
    
    
}
