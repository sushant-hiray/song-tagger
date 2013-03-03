package com.example.songtagger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nilesh
 */



//package javaapplication1;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author nilesh,vipul
 */
public class Hasher {

    /**
     * @param args the command line arguments
     */
        /* defining class variables*/
    
        double frameLength= 0.37;
        double frameShift = 0.03125; // 1/32
        double nSubands = 25;
        double spacing = frameLength*frameShift;
        double noOfFrames=256;
        
       
        
        
        
        
        // int frameSize = (int) (frameLength/spacing);
        int frameSize = 512;
        int bandScale[]={20,100,200,300,400,510,630,770,920,
                            1080,1270,1480,1720,2000,2320,2700,
                            3150,3700,4400,5300,6400, 
                            7700, 9500, 12000,15500, 20000};
        
        //Map<String, String>  = new HashMap<String, String>();    
       // Map<String, Int>  = new HashMap<String,Int>();    
        
        /* arrays to be used by my hash */
        
        boolean hashString[][];
        double E[][];
        double ED[][];
        double myarray[];
	double  curr_frame_freq_domain[][];
	double temp[][];  //to store the current frame, 2 because nilu wanted it that way
	
        Hasher()
        {
           hashString = new boolean[(int)noOfFrames][(int)nSubands]; 
           ED = new double[(int)noOfFrames][(int)nSubands]; 
           E = new double[(int)noOfFrames][(int)nSubands]; 
           myarray= new double[frameSize];
           curr_frame_freq_domain=new double[frameSize][2];
           temp= new double[frameSize][2];
        }
        
        
        float get_freq_from_coeff(int index)
        {   
            return (float)( ((2 * 3.14) * index )/ frameLength);   //simplify later
        }
        int get_coeff_from_freq(float freq)
        {
            return (int)((freq * frameLength)/ (2 * 3.14));   //simplify later
        }
        
        double nilu_ka_norm(double[] a)
        {
             return (a[0]*a[0] + a[1] * a[1] );
         }
          
          int max(int a , int b){
              if(a>b) return a;
              else  
                  return b;
                    
          }
          
          int min(int a , int b){
              if(a<b) return a;
              else  
                  return b;
                    
          }
         
          double getEnergy (double  frame1[][], int subBandNo)
          {
	
	//float frequencies[frameSize];
	//float frequency;
            	float lowerBoundFreq = bandScale[subBandNo];
                float upperBoundFreq = bandScale[subBandNo + 1];
        	double energy=0.0;
	
	
                int lower_coeff =  get_coeff_from_freq(lowerBoundFreq);
	
        	int upper_coeff = get_coeff_from_freq(upperBoundFreq);
	
                lower_coeff= max(0,lower_coeff);

                upper_coeff=min(frameSize,upper_coeff);

                for(int i= lower_coeff ;i< upper_coeff ;i++){
                    energy =energy + nilu_ka_norm(frame1[i])/100;     	
                 }

            return energy;
          }

          
        pair  getFrame(int i)
        {
            pair p = null;
            p.first = (int) ((i * (double)frameShift) * frameSize);
	    p.second= p.first + frameSize;
            return p;
        }

          

    
       void dft(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
        int n = inreal.length;
        for (int k = 0; k < n; k++) {  // For each output element
            double sumreal = 0;
            double sumimag = 0;
            for (int t = 0; t < n; t++) {  // For each input element
                sumreal +=  inreal[t]*Math.cos(2*Math.PI * t * k / n) + inimag[t]*Math.sin(2*Math.PI * t * k / n);
                sumimag += -inreal[t]*Math.sin(2*Math.PI * t * k / n) + inimag[t]*Math.cos(2*Math.PI * t * k / n);
            }
            outreal[k] = sumreal;
            outimag[k] = sumimag;
        }
    }
 
    void getPowerSpectrum() {
        int n = frameSize;
        for (int k = 0; k < n; k++) {  // For each output element
            double sumreal = 0;
            double sumimag = 0;
            for (int t = 0; t < n; t++) {  // For each input element
                sumreal +=  temp[t][0]*Math.cos(2*Math.PI * t * k / n) + temp[t][1]*Math.sin(2*Math.PI * t * k / n);
                sumimag += -temp[t][0]*Math.sin(2*Math.PI * t * k / n) + temp[t][1]*Math.cos(2*Math.PI * t * k / n);
            }
            curr_frame_freq_domain[k][0] = sumreal;
            curr_frame_freq_domain[k][1] = sumimag;
        }
    }
 
       
    void hash_song(String file , double input_data[][])  
   {
	//double input_data[][] = getAudioData(file);
  //      double input_data[][]=new double [10][10];
	pair p=null;
	
	//************just initializing************************
           // kar dia upar intialise
        //end of initialization**********
	

	int i=0,j;
	
	
	
	//noOfFrames no of frames required for hashing
	while (i<noOfFrames)
         {
    		p=getFrame(i);   //will return the initial and final position of the frame in the main array
		
		
		
		//cout<<p.first<<" "<<p.second<<endl;
		for(j = (int)p.first;j < (int) p.second; j++)
		{
			//cout<<input_data[j][0]<<"here"<<j<<":"<<i<<" : "<<p.first<<endl;
			temp [j-p.first][0]=input_data[j][0];  //note : p.second = p.first + frameSize  , storing the current frame
			temp [j-p.first][1]=input_data[j][1];
		}

		
		
		
		
		getPowerSpectrum() ; 
		
                        
                for(j=0; j< (int)nSubands; j++)
		{
			//calculate energy for this particular subband
			 double energy;
			 energy = getEnergy(curr_frame_freq_domain , j);
			 E[i][j]=energy;
		}
		i++;
	}

	//Now calculate ED values and hash(key) values
	
	
	
	
	

	for(i=0;i<noOfFrames;i++)
	{
		for(j=0;j<nSubands-1;j++)
		{
		  if(i==0)
		  {
			  ED[i][j]= E[i][j]-E[i][j+1];
		  }
		  else
			  ED[i][j]= (E[i][j]-E[i][j+1])   -   (E[i-1][j]-E[i-1][j+1]);


          hashString[i][j]=(ED[i][j] >= 0);
          System.out.print(hashString[i][j]);
          System.out.print(" \n");	
	}
		
	if(i==0){
            ED[i][j]=E[i][j];
        }
        else{
             ED[i][j]=E[i][j] - E[i-1][j];
        }

        hashString[i][j]= (ED[i][j] >= 0);
	System.out.println(hashString[i][j] + " ");
                

	}

	
	
	/*
	for(i=0; i < noOfFrames; i++)
	{
		p1.first = hash[i];
		table->insert(p1);
	}
	*/
    
    //cout<<"here "<<file<<endl;

}

    public boolean[][] getHashString(String file,double input_data[][])
    {
       hash_song(file,input_data);
       return hashString;
    }
        
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.print("Hello!! here we go");
    }
    
    
}
