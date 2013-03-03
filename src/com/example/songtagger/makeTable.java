package com.example.songtagger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package javaapplication1;
import java.util.*;


/**
 *
 * @author nilu,vipul
 */ 
public class makeTable {
    static int noOfSongs = 10;
    static int noOfFrames= 256;
    static int nSubands =  25;
     static Hasher Songs[];
     static boolean hashtable[][][];
     static boolean hash[][];
     makeTable(){
       Songs = new Hasher[noOfSongs];
       hashtable= new boolean[noOfSongs][noOfFrames][nSubands];
       hash= new boolean[noOfFrames][nSubands];
       
    }
     
 /*    
   static String getmeta(int i){
	String ans="adsfkj";
	switch(i){
		case 0: ans = "zero";
				break;
		case 1: ans = "one";
				break;
		case 2: ans = "two";
				break;
		case 3: ans = "three";
				break;
		case 4: ans = "four";
				break;
		case 5: ans = "five";
				break;
		case 6: ans = "six";
				break;
			
		case 7: ans = "seven";
				break;
		case 8: ans = "eight";
				break;
		case 9: ans = "nine";
				break;
				
	}
	return ans;
}	
*/ 

     
     static String getmeta(int i){
    	 String temp=""+i+"";
    	 return temp;
     }
     
     
     static int getSong(String meta){
    	 return Integer.parseInt(meta);
     }
  
   
/*   
  static int getSong(String meta){
	//String ans="adsfkj";
      int ans=91;
      switch(meta){
		case "zero": ans = 0;
				break;
		case "one": ans = 1;
				break;
		case "two": ans = 2;
				break;
		case  "three": ans =3;
				break;
		case  "four" : ans = 4;
				break;
		case  "five" : ans = 5;
				break;
		case "six": ans = 6;
				break;
			
		case  "seven": ans =7;
				break;
		case "eight": ans = 8;
				break;
		case "nine": ans = 9;
				break;
				
	}
	return ans;
}  
   
*/   
   
   
   
   
   
     
 
    public static void main(){
        Map<String, String> table = new HashMap<String, String>();

        for(int i=0 ; i< noOfSongs ; i++){
            String songname = "song" + i + ".wav"; 
//            hashtable[i] =Songs[i].getHashString(songname);
            String temp1="hh";
            for(int j= 0; j< noOfFrames ; j++)
            {
              temp1="";
                for(int k= 0; k< nSubands ; k++)
                  temp1+= hashtable[i][j][k]? "1" : "0";
              table.put(temp1,getmeta(i));
            }
            
          }
        
    //searching for the song now
       String trySong = "song0.wav"; 
       Hasher tryhasher = null;
//       hash =tryhasher.getHashString(trySong);
       int[] number=new int[10];  //noOfSongs
       for (int w=0;w<noOfSongs;w++)
       {
           number[w]=0;
       } 
            for(int j= 0; j< noOfFrames ; j++)
            {
               String temp1="";
                for(int k= 0; k< nSubands ; k++)
                  temp1+= hash[j][k]? "1" : "0";
               if(table.containsKey(temp1))
               {
                   String meta=table.get(temp1);
                   int t = getSong(meta);
                   number[t]++;
               }
            }
    }
}
            
            
   
        
        
        
        
        
        
        
