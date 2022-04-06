/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com3101project;
import java.util.*;
import java.io.*;
/**
 *
 * @author kenny
 */
public class testslot {
    HashMap<Integer,ArrayList<String>> slots = new HashMap();
    ArrayList<String> slotData= new ArrayList();
    
    public void testing(){
    slotData.add("hkh");
    slotData.add("$2000");
    slotData.add("0");
    for(int i = 0; i<=23;i++){
    slots.put(i, slotData);
    }
    System.out.println(slots);
    save(slots);
    }
    
    public void save(HashMap<Integer,ArrayList<String>> map) {
    //write to file : "fileone"
    try {
        File fileTwo=new File("slot.txt");
        FileOutputStream fos=new FileOutputStream(fileTwo);
        PrintWriter pw=new PrintWriter(fos);

        for(Map.Entry<Integer,ArrayList<String>> m :map.entrySet()){
            pw.println(m.getKey()+"="+m.getValue());
        }

        pw.flush();
        pw.close();
        fos.close();
    } catch(Exception e) {}
        }
}
