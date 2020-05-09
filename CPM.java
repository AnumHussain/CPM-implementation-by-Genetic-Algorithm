/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Iqra Akhtar
 */
public class CPM {

    static ArrayList<ArrayList<Integer>> InitialPopulation=new ArrayList<>();
  static int path_length=8;
  static  int mat_Size=12;
 static  int totalPath=4;
    public static void ReadPathFromFile(){ 
        try{ 
           BufferedReader br = new BufferedReader(new FileReader("input-data.csv")); 
  
            String st; 
            int i=0;
          
            while ((st = br.readLine()) != null){
                ArrayList<Integer> path=new ArrayList<>();
               
                String[] split=st.split(",");
               
                path.add(Integer.parseInt(split[0]));
                path.add(Integer.parseInt(split[1]));
                path.add(Integer.parseInt(split[2]));
                path.add(Integer.parseInt(split[3]));
                path.add(Integer.parseInt(split[4]));
                path.add(Integer.parseInt(split[5]));
                path.add(Integer.parseInt(split[6]));
                path.add(Integer.parseInt(split[7]));
                 path.add(Integer.parseInt(split[8]));
                InitialPopulation.add(path);
            
            }
            br.close();
        }catch (IOException e){
      
        e.printStackTrace();
        }
    }   

    public static int[][] ReadProcessingTimeMatrix()
    {
         int matrix[][]=new int[mat_Size][mat_Size];
     try{ 
           BufferedReader br2 = new BufferedReader(new FileReader("input-data2.csv")); 
  int X=0;
            String st2; 
    
            while ((st2 = br2.readLine()) != null){
               String[] splited=st2.split(",");
                for(int l = 0; l < mat_Size; l++)
                {
           matrix[X][l]= Integer.parseInt(splited[l]);
                }
                    X++;

            }
            br2.close();
        }catch (IOException e){
      
        e.printStackTrace();
        }
    return matrix;
    }

    
    public static void calculateInitialPopulationFitness(ArrayList<ArrayList<Integer>> list,int mat[][])
    {  
        ArrayList<Integer> fitnessList=new ArrayList<>();
        
     for (int z=0;z<totalPath;z++)
     {
     int j=0;
     int fitness=0;
        for(int i=0; i<path_length-1 ;i++)
         {
       int p=list.get(z).get(j);
       int q=list.get(z).get(++j);
       int s=mat[--p][--q];       
       fitness=fitness+s;
   
         }
  
     list.get(z).set(8, fitness);
      fitnessList.add(fitness);
 
 
         }
  
     //  System.out.println("Fitness list: 1\n"+fitnessList); 
    
    }
    public static ArrayList<ArrayList<Integer>> calcFitness(ArrayList<ArrayList<Integer>> list,int mat[][])
    {
        
        
     for (int z=2;z<totalPath;z++)
     {
     int j=0;
     int fitness=0;
        for(int i=0; i<path_length-1 ;i++)
         {
       int p=list.get(z).get(j);
       int q=list.get(z).get(++j);
       int s=mat[--p][--q];       
       fitness=fitness+s;
   
         }
  
     list.get(z).set(8, fitness);
         }
   
    return list;
    }
    
    public static void sorting( ArrayList<ArrayList<Integer>> pop){
    Comparator<ArrayList<Integer>> myComparator = new Comparator<ArrayList<Integer>>() {
        @Override
        public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
            return o1.get(path_length).compareTo(o2.get(path_length));
        }
    };
        Collections.sort(pop, myComparator.reversed());
    
    }

    
    public static  ArrayList<ArrayList<Integer>> parentSelection(ArrayList<ArrayList<Integer>> generation)
    {
        ArrayList<ArrayList<Integer>> ParentList=new ArrayList<>();
        ParentList.add(generation.get(0));
        ParentList.add(generation.get(1));
        return ParentList;
      
    }
    
      public static  ArrayList<ArrayList<Integer>> crossover(ArrayList<ArrayList<Integer>> parnt,int[][] mat)
    {
        
       
        ArrayList<ArrayList<Integer>> newList=new ArrayList<>();
     
        ArrayList<Integer> chromosome1=new ArrayList<>();
        ArrayList<Integer> chromosome2=new ArrayList<>();
        
         chromosome1.addAll(parnt.get(0));
        chromosome2.addAll(parnt.get(1));

        newList.add(chromosome1);
        newList.add(chromosome2);
       
        
        int CrossPoint=getRandom()+1;
        System.out.println("crossover point is "+CrossPoint);
        
        ArrayList<Integer> tempCH1=new ArrayList<>();
        ArrayList<Integer> tempCH2=new ArrayList<>();

        for (int i = CrossPoint; i < 9; i++) {
            tempCH1.add(newList.get(0).get(i));
            tempCH2.add(newList.get(1).get(i));
        }
        
        for (int i = 0,j=CrossPoint; i <=tempCH1.size()&& j<9;j++, i++) {
           newList.get(0).set(j, tempCH2.get(i));
           newList.get(1).set(j, tempCH1.get(i));           
        }
        
      //  System.out.println("children"+newList);
       
        ArrayList<ArrayList<Integer>> nextGeneration=new ArrayList<>();   
     
       nextGeneration.add(parnt.get(0));
       nextGeneration.add(parnt.get(1));
       nextGeneration.add(newList.get(0));
       nextGeneration.add(newList.get(1));
       
      //  System.out.println(nextGeneration);
        
    ArrayList<ArrayList<Integer>> nextGenerationFinal=calcFitness(nextGeneration,mat);
      //  System.out.println("Generation :2\n"+nextGenerationFinal);
        
      return nextGenerationFinal ;    
    }
      
       public static int getRandom(){
        Random random1=new Random();
        int max=6;
        int crossoverPoint=random1.nextInt((max - 1)) + 1;
        return crossoverPoint;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        ReadPathFromFile();
  
       
        int matret[][]=new int[12][12];
        matret=ReadProcessingTimeMatrix();
//for(int x=0;x<12;x++)
//{
//    for (int s=0;s<12;s++)
//    {
//            System.out.println("\n\n\n\n"+matret[x][s]); 
//    }
//}


calculateInitialPopulationFitness(InitialPopulation,matret);
sorting(InitialPopulation);
 System.out.println("Sorted Initial Population \n Generation: 1\n "+InitialPopulation);
 ArrayList<ArrayList<Integer>> parent= parentSelection(InitialPopulation);
 System.out.println("\nParents Selected : "+parent);
 
 int i=2;
 while(true)
 {

    ArrayList<ArrayList<Integer>> newgeneration =   crossover(parent,matret);
       sorting(newgeneration);
        System.out.println("\nGeneration : "+i+"\n" +newgeneration);
        
        int totalfitness=newgeneration.get(0).get(8)+newgeneration.get(1).get(8)+newgeneration.get(2).get(8)+newgeneration.get(3).get(8);
        double avg=totalfitness/4;
        System.out.println("Average Fitness:"+avg);
        i++;
        if(avg==newgeneration.get(0).get(8))
        {
            System.out.println("\n\nCRITICAL PATH : \n\t\t"+newgeneration.get(0).get(0)+" --> "+newgeneration.get(0).get(1)+" --> "+newgeneration.get(0).get(2)+" --> "+newgeneration.get(0).get(3)+" --> "+newgeneration.get(0).get(4)+" --> "+newgeneration.get(0).get(5)+" --> "+newgeneration.get(0).get(6)+" --> "+newgeneration.get(0).get(7)+" = "+newgeneration.get(0).get(8));
            System.out.println("\nCRITICAL ACTIVITIES : \n\t\t "+newgeneration.get(0).get(0)+" \n\t\t "+newgeneration.get(0).get(1)+" \n\t\t "+newgeneration.get(0).get(2)+" \n\t\t "+newgeneration.get(0).get(3)+" \n\t\t "+newgeneration.get(0).get(4)+" \n\t\t "+newgeneration.get(0).get(5)+" \n\t\t "+newgeneration.get(0).get(6)+" \n\t\t "+newgeneration.get(0).get(7));
            System.out.println("\nPROJECT COMPLETION DURATION : "+newgeneration.get(0).get(8)+" Weeks");
            break;
        }
    parent= parentSelection(newgeneration);
    System.out.println("\nParents Selected : "+parent);
 
    
 }
        
    }
  
}