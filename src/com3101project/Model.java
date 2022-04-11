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
    int turn;
    Player[ ] players;
    Slot[] slots;
    public static Scanner scan ;
    ArrayList<String> activePlayer = new ArrayList<String>();
    
    
    public void setController(Controller c) {
        this.control = c;
    }
    
    public void newGame(){
        turn=1;
        players = new Player[5];
        players [1]=new Player("1",2000,false,0);
        players [2]=new Player("2",2000,false,0);
        players [3]=new Player("3",2000,false,0);
        players [4]=new Player("4",2000,false,0);
        slots = new Slot[23];
        activePlayer.add("1");
        activePlayer.add("2");
        activePlayer.add("3");
        activePlayer.add("4");
        load();
        editOwner("6","4");
        editOwner("5","4");
        editOwner("3","2");
        editOwner("2","4");
        editOwner("5","2");
        editOwner("22","2");
        editOwner("5","1");
        editOwner("14","3");
        editOwner("5","3");
        editOwner("22","1");        
        editplayer("10","300","bankrupt","1","25000","bankrupt","16","7000","active","20","35000","active","2");
        testPrintPlayer();
        testPrintSlot();
        //editSlot("1","Central","1000");
        //testPrintSlot();
    }
    //
    
    
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
        position = players[turn].getPosition();
        if (checkLandStatus(position)=="0") {
            //buyland
        }else{
            payRentalFee(Integer.toString(turn),checkLandStatus(position),position);        
        }       
        nextTurn(turn);
        //not finish
        //function update to controller
    }
    
    public void nextTurn(int nTurn){
        int pos=activePlayer.indexOf(Integer.toString(nTurn));
        int size=activePlayer.size();
        
        if(pos==size-1){
            turn=Integer. parseInt(activePlayer.get(0));
        }else{
            turn=Integer. parseInt(activePlayer.get(pos+1));
        } 
    }
    
    public void updatePlayerBalance(String playerId, int amount){
        players[Integer.parseInt(playerId)].setBalance(amount);
    }
    
    public void updatePlayerPosition(){
        // need to be done
    }
    
    public void buyLand(String playerId, int slotId){
        int choice = 0;
        // ask player buy or not   call view()
        if(choice == 0 ){
            int player=Integer.parseInt(playerId);
            int price=Integer.parseInt(slots[slotId].getPrice());
            if(players[player].getBalance()-price>=0){
                players[player].deduct(price);
                players[player].addSlot(Integer.toString(slotId));
        }else{
            //send msg no money
            }      
        }     
    }
    
    public String checkLandStatus(int slot){
       String owner= slots[slot].getOwner();
       return owner;
    }
    
    public void bankrupt(String playerId){
        int id= Integer. parseInt(playerId);
        players[id].setStatus(false);
        players[id].clear();
        activePlayer.remove(playerId);
        //send message
        //not finish
    }
    
    
    public void payRentalFee(String playerId, String ownerId, int slotId){
        int player= Integer. parseInt(playerId);
        int owner= Integer. parseInt(ownerId);
        int price=Integer. parseInt(slots[slotId].getPrice());
        int pBalance=players[player].getBalance();
        int oBalance=players[owner].getBalance();
        
        if(pBalance-(price*0.1)>=0){
            players[player].deduct((int) (price*0.1));
            players[owner].add((int) (price*0.1));
            //send message
        }else{
            bankrupt(playerId);
        }
        
        //not finish
    }
    
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
    
    public void editSlot( String id, String name, String price){
        
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
        //send message
    }
    
    public void editOwner(String slot, String owner){
        int slotNum=Integer. parseInt(slot);
        int player=Integer. parseInt(owner);
        if(slots[slotNum].getOwner().equals("0")){
            slots[slotNum].setOwner(owner);
            players[player].addSlot(slot);
        }else{ 
           int playerNum=Integer. parseInt(slots[slotNum].getOwner());
           slots[slotNum].setOwner(owner);
           players[playerNum].removeSlot(slot);
           players[player].addSlot(slot);
           //send message
        }   
    }
    
    public void editplayer(String p1Pos, String p1Balance, String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, String p3Status, String p4Pos, String p4Balance, String p4Status, String nTurn)
    {       //update player 1
            players[1].setPosition(Integer. parseInt(p1Pos));
            players[1].setBalance(Integer. parseInt(p1Balance));
            if(p1Status.equals("active"))
                players[1].setStatus(true);
            else
                players[1].setStatus(false);
            //update player 2
            players[2].setPosition(Integer. parseInt(p2Pos));
            players[2].setBalance(Integer. parseInt(p2Balance));
            if(p2Status.equals("active"))
                players[2].setStatus(true);
            else
                players[2].setStatus(false);
            //update player 3
            players[3].setPosition(Integer. parseInt(p3Pos));
            players[3].setBalance(Integer. parseInt(p3Balance));
            if(p3Status.equals("active"))
                players[3].setStatus(true);
            else
                players[3].setStatus(false);
            //update player 4
            players[4].setPosition(Integer. parseInt(p4Pos));
            players[4].setBalance(Integer. parseInt(p4Balance));
            if(p4Status.equals("active"))
                players[4].setStatus(true);
            else
                players[4].setStatus(false);
            //update turn
            turn=Integer. parseInt(nTurn);
            //send message
    }
    
    public void testPrintPlayer(){
        for(int i=1;i<=4; i++){
            System.out.println(players[i].getId()+" "+players[i].getBalance()+" "+players[i].getStatus()+" "+players[i].getPosition()+" "+players[i].getSlotOwned().toString());
        }   
    }
    
    public void testPrintSlot(){ 
        for(int i=0;i<23;i++){
            System.out.println("Slot: "+slots[i].getId()+" Name: "+slots[i].getName()+" Price: $"+slots[i].getPrice()+" Owner "+slots[i].getOwner());
        }
    }
    
    
}
