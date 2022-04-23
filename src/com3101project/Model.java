
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
    public void rollDice()
    {
        if (checkPlayerStatus(turn)==true) {
            Random rand = new Random();
            int moving = rand.nextInt((10 - 1) + 1) + 1;
            int position = players[turn].getPosition();
            if(position+moving >=23){
                position = (position+moving)%22-1;
                players[turn].setPosition(position);
                players[turn].add(2000);          
            }else{
                players[turn].setPosition(position+moving);
            }
            position = players[turn].getPosition();
            control.viewShowMessage("Player "+turn+" roll "+moving+"\nPlayer"+turn+" latest position is "+players[turn].getPosition());
            updatePlayerPosition();
            if (checkLandStatus(position).equals(Integer.toString(turn))||position==0) { 
                // do nothing
            }else{
            if (checkLandStatus(position).equals("0")) {
                    buyLand(Integer.toString(turn),position);
                //buyland
                }else{
                    payRentalFee(Integer.toString(turn),checkLandStatus(position),position);        
                } 
            }
            nextTurn(turn);
        }else{
         control.viewShowMessage("This player already bankrupt! Please set turn to other active player. ");
        }
        checkWin();
        updatePlayerPosition();
    }
    /**
     * turn move to next player
     * @param nTurn player current turn
     */
    public void nextTurn(int nTurn){
        if(activePlayer.contains(Integer.toString(nTurn))){
            int pos=activePlayer.indexOf(Integer.toString(nTurn));
            int size=activePlayer.size();
            if(pos==size-1){
                turn=Integer. parseInt(activePlayer.get(0));
            }else{
                turn=Integer. parseInt(activePlayer.get(pos+1));
            } 
        }else{
         control.viewShowMessage("This player already bankrupt! Please set turn to other active player. ");
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
            control.viewUpdate(postion1,balance1,status1,postion2,balance2,status2,
                    postion3,balance3,status3,postion4,balance4,status4,nTurn);
    }
    /**
     * ask player buy the slot or not
     * @param playerId current turn player id
     * @param slotId the slot id ask to buy
     */
    public void buyLand(String playerId, int slotId){   
        boolean choice=control.buy(slotId, slots[slotId].getPrice(),slots[slotId].getName());
        // ask player buy or not call view()
        if(choice==true){
            int player=Integer.parseInt(playerId);
            int price=Integer.parseInt(slots[slotId].getPrice());
            if(players[player].getBalance()-price>=0){
                players[player].deduct(price);
                players[player].addSlot(Integer.toString(slotId));
                slots[slotId].setOwner(playerId);
                control.viewShowMessage("You buy "+slotId+" "+slots[slotId].getName());  
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
    /**
     * set player to bankrupt status
     * @param playerId bankrupt player id
     */
    public void bankrupt(String playerId){
        int id= Integer. parseInt(playerId);
        players[id].setStatus(false); 
        for (int i = 0; i < 23; i++) {
            if(slots[i].getOwner().equals(playerId)){
                slots[i].setOwner("0");
            }   
        }
        players[id].clear();
        if(id==turn){
            nextTurn(turn);
        }
        activePlayer.remove(playerId);       
        control.viewShowMessage("Player "+playerId+" is bankrupt");
        checkWin();
    }
    /**
     * check player win
     */
    public void checkWin(){
    if (activePlayer.size()==1) {
             control.viewShowMessage("Player "+activePlayer.get(0)+" win !");
             newGame();
        }
    }
    /**
     * check current Player still active or not
     * @param turn current Player
     * @return current Player status
     */
    public boolean checkPlayerStatus(int turn){
        return players[turn].getStatus();
    }
    /**
     * player pay rental fee to the land owner
     * @param playerId pay fee player id
     * @param ownerId  land owner id
     * @param slotId   land slot id
     */
    public void payRentalFee(String playerId, String ownerId, int slotId){
        int player= Integer. parseInt(playerId);
        int owner= Integer. parseInt(ownerId);
        int price=Integer. parseInt(slots[slotId].getPrice());
        int pBalance=players[player].getBalance();
        int oBalance=players[owner].getBalance();
         control.viewShowMessage("This slot "+slotId+" owned by Player "+ownerId+" you need to pay $"+price*0.1);
        if(pBalance-(price*0.1)>=0){
            players[player].deduct((int) (price*0.1));
            players[owner].add((int) (price*0.1));
            //send message
        }else{
            bankrupt(playerId);
        }
    }
    /**
     * output all slot data
     * @return all slot data by string
     */
    public String showSlotOwned(){ 
        String display="";
        
        for(int i=0;i<23;i++){
            String temp=("Slot: "+String.format("%1$-2s", slots[i].getId())+" Owner: "+slots[i].getOwner()+" Price: $"
                    +String.format("%1$-10s", slots[i].getPrice())+" Name: "+String.format("%1$-25s", slots[i].getName())+"\n");
            display += temp;
        }
        
        return display;
    }
    /**
     * load land data from csv file
     */
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
    /**
     * change land data
     * @param id slot id need to change 
     * @param name new slot name for change 
     * @param price new land price
     */
    public void editSlot( String id, String name, String price){
        
        String path = "slot.csv";
        String temp="temp.csv";
        File oldFile= new File(path);
        File newFile= new File(temp);
        String ID=""; String oldName=""; String oldPrice="";
        //if user only change slot price
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
            if (price.equals("")) {//if user only change slot name
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
            }else{// user change slot name and price
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
            control.viewShowMessage("Land slot edit is done!");
        //send message
    }
    /**
     * change land owner
     * @param slot slot id need to change 
     * @param owner the new owner for the land
     */
    public void editOwner(String slot, String owner){
        int slotNum=Integer. parseInt(slot);
        int player=Integer. parseInt(owner);
        if(slots[slotNum].getOwner().equals("0")){
            slots[slotNum].setOwner(owner);
            players[player].addSlot(slot);
        }else{ 
            int playerNum=Integer. parseInt(slots[slotNum].getOwner());
            if (owner.equals("0")) {
                slots[slotNum].setOwner(owner);
                players[playerNum].removeSlot(slot);
            }else{
                slots[slotNum].setOwner(owner);
                players[playerNum].removeSlot(slot);
                players[player].addSlot(slot);
            }
        }
        updatePlayerPosition();
        control.viewShowMessage("Edit land owner success! ");
    }
    /**
     * change player status
     * @param p1Pos player 1 position
     * @param p1Balance player 1 Balance
     * @param p1Status player 1 status
     * @param p2Pos player 2 position
     * @param p2Balance player 2 Balance
     * @param p2Status player 2 status
     * @param p3Pos player 3 position
     * @param p3Balance player 3 Balance
     * @param p3Status player 3 status
     * @param p4Pos player 4 position
     * @param p4Balance player 4 Balance
     * @param p4Status player 4 status
     * @param nTurn current player turn
     */
   public void editPlayer(String p1Pos, String p1Balance, String p1Status, String p2Pos, String p2Balance, 
            String p2Status, String p3Pos, String p3Balance, String p3Status, String p4Pos, String p4Balance, String p4Status, String nTurn)
    {       //update turn
            turn=Integer. parseInt(nTurn);
            //update player 1
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
            updatePlayerPosition();
            control.viewShowMessage("Player edit is done!");
            //send message
    }
   /**
    * trade slot between 2 players
    * @param buyer buyer id
    * @param seller seller id 
    * @param slotId slot id that waiting to trade
    * @param price trading price
    */
   public void trade(String buyer, String seller, String slotId, String price){
       int buy=Integer.parseInt(buyer);
       int sell=Integer.parseInt(seller);
       int slot=Integer.parseInt(slotId);
       int tradePrice=Integer.parseInt(price);
       if(players[buy].getBalance()-tradePrice>=0){
           if(checkLandStatus(slot).equals(seller)){
                players[buy].deduct(tradePrice);
                players[sell].add(tradePrice);
                slots[slot].setOwner(buyer);
                players[sell].removeSlot(slotId);
                players[buy].addSlot(slotId);
                updatePlayerPosition(); 
                control.viewShowMessage("Trading success! Land slot "+ slotId+" now own by player"+buyer);
           }else{
                control.viewShowMessage("Seller haven't own this land slot!");
           }
       }else{
           control.viewShowMessage("Buyer "+ buyer+" not have enough money!");
       }
   }
}
