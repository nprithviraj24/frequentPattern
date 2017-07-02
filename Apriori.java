
package frequentpattern;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Apriori {
    
    String dataObjects[] = {"A","B","C","D","E", "0"}; //dont ask why extra 0. it works like that way!

    List<String> TSet = new ArrayList<String>();
    int minThreshold;
    
    Apriori(int threshold){
        int j,i=1;
        Connection c = null;
        this.minThreshold = threshold;
        
        try{
            Class.forName("org.postgresql.Driver");
            
            //fill those ? accordingly
            c = DriverManager.getConnection("?","?","?");
            
        }catch(Exception e){
            System.out.println("Unable to load driver!");
            System.exit(0);
        }
        
        try{
            Statement select = c.createStatement();
            ResultSet s;
            s = select.executeQuery("SELECT * FROM apriori;");
            ResultSetMetaData rsmd = s.getMetaData();

            int col = rsmd.getColumnCount();
            while(s.next()){

                String trans = "";
                for(j=1; j<col; j++){  // j=1 because s.getInt(0) doesnt make any sense 

                  if(s.getInt(j) == 1){

                        trans += dataObjects[j-1]; 

                    }
 
                }
                TSet.add(trans);

                System.out.println("\tTransaction["+i+"]\t->\t"+TSet.get(i-1));
              i++;  
            }
            for(i=0; i<5; i++){

                recursion(dataObjects[i],"",i); //initially lookahead is null.
                
            }
        }catch(Exception e){
            System.out.println("Error retrieving the value!");
            e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
        }   
    
    }
 
    
    public void recursion(String orig, String lookahead, int k){
       
        if(k<5){ // extra 0 in the dataobjects because of this.
           int freq = checkFreq(orig+lookahead);
           if( freq < minThreshold){
                recursion(orig,dataObjects[k+1],k+1);
                // Superset wont have enough frequency when the set doesnt have enough frequency.
                return;  
           }
           else {
               orig = orig + lookahead;
               System.out.print("\t Itemset: "+orig+" : "+freq);
               recursion(orig,dataObjects[k+1],k+1);
               return;
           }
        } else {
            System.out.print("\n");
            return;
        }  
    }
    
    public int checkFreq(String current){
        int freq=0;
        for(int i=0; i<TSet.size(); i++){
        int flag = 0;
        char[] characters = current.toCharArray();
            for (char c: characters){
                    if (!((TSet.get(i)).contains(String.valueOf(c)))){ 
                     flag=1;
                     break;                    
                 }                
            }
            if(flag==0){
                freq++;
            }
        }    
        return freq;
    }

}
