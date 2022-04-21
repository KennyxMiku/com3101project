
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
    /**
     * start a new game
     */
    public void newGame(){
        turn=1;
        players = new Player[5];
        players [1]=new Player("1",2000,true,0);
        players [2]=new Player("2",2000,true,0);
        players [3]=new Player("3",2000,true,0);
        players [4]=new Player("4",2000,true,0);
        slots = new Slot[23];
        activePlayer.add("1");
        activePlayer.add("2");
        activePlayer.add("3");
        activePlayer.add("4");
        load();
        updatePlayerPosition();
    }
   
    
    /**
     * player roll dice
     */
    public void rollDice(){
        Random rand = new Random();
        int moving = rand.nextInt((10 - 1) + 1) + 1;
        control.viewShowMessage("you roll "+moving);
        int position = players[turn].getPosition();
        if(position+moving >=23){
            position = (position+moving)%22-1;
            players[turn].setPosition(position);
            System.out.println(position);
            players[turn].add(2000);          
        }else{
            players[turn].setPosition(position+moving);
        }
        position = players[turn].getPosition();
        control.viewShowMessage(turn+" latest position "+players[turn].getPosition());
        if (checkLandStatus(position).equals(Integer.toString(turn))||position==0) { 
            
        }else{
        if (checkLandStatus(position).equals("0")) {
                buyLand(Integer.toString(turn),position);
            //buyland
            }else{
                payRentalFee(Integer.toString(turn),checkLandStatus(position),position);        
            } 
        }           
        nextTurn(turn);
        checkbankrupt();
        updatePlayerPosition();
        //not finish
        //function update to controller
    }
    /**
     * turn move to next player
     * @param nTurn player current turn
     */
    public void nextTurn(int nTurn){
        int pos=activePlayer.indexOf(Integer.toString(nTurn));
        int size=activePlayer.size();
        
        if(pos==size-1){
            turn=Integer. parseInt(activePlayer.get(0));
        }else{
            turn=Integer. parseInt(activePlayer.get(pos+1));
        } 
    }
    /**
     * update player data to view
     */ 
    public void updatePlayerPosition(){
        String nTurn=Integer.toString(turn);
        String postion1=Integer.toString(players[1].getPosition());
        String postion2=Integer.toString(players[2].getPosition());
        String postion3=Integer.toString(players[3].getPosition());
        String postion4=Integer.toString(players[4].getPosition());
        String balance1=Integer.toString(players[1].getBalance());
        String balance2=Integer.toString(players[2].getBalance());
        String balance3=Integer.toString(players[3].getBalance());
        String balance4=Integer.toString(players[4].getBalance());
        String status1;
        String status2;
        String status3;
        String status4;
            if(players[1].getStatus()==true){
                status1="Active";
            }else{
                status1="Bankrupt";
            }
            if(players[2].getStatus()==true){
                status2="Active";
            }else{
                status2="Bankrupt";
            }
            if(players[3].getStatus()==true){
                status3="Active";
            }else{
                status3="Bankrupt";
            }
            if(players[4].getStatus()==true){
                status4="Active";
            }else{
                status4="Bankrupt";
            }
            control.viewUpdate(postion1,balance1,status1,postion2,balance2,status2,postion3,balance3,status3,postion4,balance4,status4,nTurn);
        // need to be done
    }
    /**
     * ask player buy the slot or not
     * @param playerId current turn player id
     * @param slotId the slot id ask to buy
     */
    public void buyLand(String playerId, int slotId){   
        boolean choice=control.buy(slotId, slots[slotId].getPrice());
        // ask player buy or not   call view()
        if(choice==true){
            int player=Integer.parseInt(playerId);
            int price=Integer.parseInt(slots[slotId].getPrice());
            if(players[player].getBalance()-price>=0){
                players[player].deduct(price);
                players[player].addSlot(Integer.toString(slotId));
                slots[slotId].setOwner(playerId);
                control.viewShowMessage("You buy "+slotId);  
        }else{
                control.viewShowMessage("Not have enough money");
            //send msg no money
            }      
        }     
    }
    /**
     * check current slot have a owner or not
     * @param slot the slot id need to check
     * @return owner id
     */
    public String checkLandStatus(int slot){
       String owner= slots[slot].getOwner();
       return owner;
    }
    
    public void bankrupt(String playerId){
        int id= Integer. parseInt(playerId);
        players[id].setStatus(false); 
        for (int i = 0; i < 23; i++) {
            if(slots[i].getOwner().equals(playerId)){
                slots[i].setOwner("0");
            }   
        }
        players[id].clear();
        activePlayer.remove(playerId);       
        control.viewShowMessage(playerId+" is bankrupt");
        checkbankrupt();
       
        //send message
        //not finish
    }
    /**
     * check player win
     */
    public void checkbankrupt(){
    if (activePlayer.size()==1) {
             control.viewShowMessage(activePlayer.get(0)+" win !");
             newGame();
        }
    }
    
    public void payRentalFee(String playerId, String ownerId, int slotId){
        int player= Integer. parseInt(playerId);
        int owner= Integer. parseInt(ownerId);
        int price=Integer. parseInt(slots[slotId].getPrice());
        int pBalance=players[player].getBalance();
        int oBalance=players[owner].getBalance();
         control.viewShowMessage("This slot"+slotId+" owned by "+ownerId+" need to pay "+price*0.1);
        if(pBalance-(price*0.1)>=0){
            players[player].deduct((int) (price*0.1));
            players[owner].add((int) (price*0.1));
            //send message
        }else{
            bankrupt(playerId);
        }
        
        //not finish
    }
    
    public String showSlotOwned(){ 
        String display="";
        
        for(int i=0;i<23;i++){
            String temp=("Slot: "+String.format("%1$-2s", slots[i].getId())+" Owner: "+slots[i].getOwner()+" Price: $"
                    +String.format("%1$-10s", slots[i].getPrice())+" Name: "+String.format("%1$-25s", slots[i].getName())+"\n");
            display += temp;
        }
        
        return display;
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
        
        if (name.equals("")) {
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
                        pWriter.print(ID + "," + oldName + "," + price+ "\n");
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
        }else{
            if (price.equals("")) {
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
                            pWriter.print(ID + "," + name + "," + oldPrice+ "\n");
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
            }else{
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
            }
        }
        
         
            load();
            updatePlayerPosition();
            control.viewShowMessage("edit is done!");
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
           updatePlayerPosition();
           //send message
        }   
    }
    
   public void editPlayer(String p1Pos, String p1Balance, String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, String p3Status, String p4Pos, String p4Balance, String p4Status, String nTurn)
    {       //update player 1
            players[1].setPosition(Integer.parseInt(p1Pos));
            players[1].setBalance(Integer.parseInt(p1Balance));
            if(p1Status.equals("Active")){
                if (players[1].getStatus()==true) {
                        players[1].setStatus(true);
                    }else{
                        players[1].setStatus(true);
                        activePlayer.add("1");
                        Collections.sort(activePlayer);
                    } 
            }
            else{
            bankrupt("1");
            }  
            //update player 2
            players[2].setPosition(Integer. parseInt(p2Pos));
            players[2].setBalance(Integer. parseInt(p2Balance));
            if(p2Status.equals("Active")){
                if (players[2].getStatus()==true) {
                        players[2].setStatus(true);
                    }else{
                        players[2].setStatus(true);
                        activePlayer.add("2");
                        Collections.sort(activePlayer);
                    } 
            }
            else{
            bankrupt("2");
            }  
            //update player 3
            players[3].setPosition(Integer. parseInt(p3Pos));
            players[3].setBalance(Integer. parseInt(p3Balance));
            if(p3Status.equals("Active")){
                if (players[3].getStatus()==true) {
                        players[3].setStatus(true);
                    }else{
                        players[3].setStatus(true);
                        activePlayer.add("3");
                        Collections.sort(activePlayer);
                    } 
            }
            else{
            bankrupt("3");
            } 
            //update player 4
            players[4].setPosition(Integer. parseInt(p4Pos));
            players[4].setBalance(Integer. parseInt(p4Balance));
            if(p4Status.equals("Active")){
                if (players[4].getStatus()==true) {
                        players[4].setStatus(true);
                    }else{
                        players[4].setStatus(true);
                        activePlayer.add("4");
                        Collections.sort(activePlayer);
                    } 
            }
            else{
            bankrupt("4");
            } 
            //update turn
            turn=Integer. parseInt(nTurn);
            updatePlayerPosition();
            control.viewShowMessage("edit is done!");
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
