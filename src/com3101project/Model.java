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
    public static Scanner scan ;
    
    public void setController(Controller c) {
        this.control = c;
    }
    
    public void newGame(){
        players = new Player[5];
        players [1]=new Player("1",2000,false,0);
        players [2]=new Player("2",2000,false,0);
        players [3]=new Player("3",2000,false,0);
        players [4]=new Player("4",2000,false,0);
        slots = new Slot[23];
        load();
        testPrintSlot();
        editSlot("1","Central","1000","0");
        testPrintSlot();
    }
    //
    public void load(){ 
        String line = "";
        String splitBy = ",";
        int slotNum=-1;
        try {
            BufferedReader br = new BufferedReader(new FileReader("slot.csv"));
            while ((line = br.readLine()) != null)
            {
                String[] slot = line.split(splitBy);
                if(slotNum>=0){
                    slots[slotNum]= new Slot(slot[0],slot[1],slot[2]);}      
                slotNum++;
            }
            br.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    }
    
    public void rollDice(){
        Random rand = new Random();
        int moving = rand.nextInt((10 - 1) + 1) + 1;
        int position = players[turn].getPosition();
    
        if(position+moving >23){
            position = position%22;
            players[turn].setPosition(position);
        }else{
            players[turn].setPosition(position+moving);
        }
    
        if(turn!=4)
            turn++;
        else
            turn=0;
    
    }
    
    public void updatePlayerBalance(String playerId, int amount){
        players[Integer.parseInt(playerId)].setBalance(amount);
    }
    
    public void editSlot( String id, String name, String price, String owner){
        
        String path = "slot.csv";
        String temp="temp.csv";
        File oldFile= new File(path);
        File newFile= new File(temp);
        String ID=""; String oldName=""; String oldPrice="";
        try
        {   
            FileWriter fWriter = new FileWriter(temp,true);
            BufferedWriter bWriter = new BufferedWriter (fWriter);
            PrintWriter pWriter = new PrintWriter(bWriter);
            scan = new Scanner(new File(path));
            scan.useDelimiter("[,\n]");
            
            while(scan.hasNext()){
                ID=scan.next();
                oldName=scan.next();
                oldPrice=scan.next();

                if(ID.equals(id)){
                    pWriter.print(ID + "," + name + "," + price+ "\n");
                }
                else{
                    pWriter.print(ID + "," + oldName + "," + oldPrice+ "\n");
                }
            }
            scan.close();
            pWriter.flush();
            pWriter.close();
            bWriter.close();
            fWriter.close();
            oldFile.delete();
            File file = new File(path);
            newFile.renameTo(file);
            newFile.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
        load();
        for(int i=0;i<23;i++){
            if(slots[i].getId().equals(id)){
                slots[i].setOwner(owner);
            }
        }
    }
    
    public void testPrintPlayer(){
        for(int i=1;i<=4; i++){
            System.out.println(players[i].getId()+" "+players[i].getBalance()+" "+players[i].getStatus()+" "+players[i].getPosition()+" "+players[i].getSlotOwned().toString());
        }   
    }
    public void testPrintSlot(){ 
        for(int i=0;i<23;i++){
            System.out.println(slots[i].getId()+" "+slots[i].getName()+" "+slots[i].getPrice()+" owner "+slots[i].getOwner());
        }
    }
    
    
}
