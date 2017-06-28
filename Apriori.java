
package frequentpattern;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Apriori {
    
//    int[][] data = new int[6][6];
//    int[] freq = new int[6];
    String dataObjects[] = {"A","B","C","D","E", "0"}; //dont ask why extra 0. it works like that way!
//    String[] transactions = new String[6];
    List<String> TSet = new ArrayList<String>();
    int minThreshold;
    
    Apriori(int threshold){
        int j,i=1;
        Connection c = null;
        this.minThreshold = threshold;
        
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost/jdbc","postgres","tiger");
            
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
//                transactions[i-1]= ""; //without this.. transactions[i]= nullABC
//                TSet.add("");
                String trans = "";
                for(j=1; j<col; j++){  // j=1 because s.getInt(0) doesnt make any sense 
//                    data[i-1][j-1]= s.getInt(j);  // a-> 3, b-> 4, c-> 5, etc
//                    System.out.print("\t I"+j+": "+data[i-1][j-1]);  //j-1 because it'll show array index out of bound exception
                  if(s.getInt(j) == 1){
//                        freq[j-1]++;
//                        transactions[i-1] += dataObjects[j-1];
                        trans += dataObjects[j-1]; 
//                        TSet.get(i-1).add(dataObjects[j-1]);
                    }
//                    System.out.println("\t I"+i+": "); 
                }
                TSet.add(trans);
//                System.out.println("\tTransaction["+i+"]\t->\t"+transactions[i-1]);
                System.out.println("\tTransaction["+i+"]\t->\t"+TSet.get(i-1));
              i++;  
            }
            for(i=0; i<5; i++){
//                System.out.print("  \t"+freq[i]);
//                System.out.print("\n\t");
                recursion(dataObjects[i],"",i); //initially lookahead is null.
                
            }
        }catch(Exception e){
            System.out.println("Error retrieving the value!");
            e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
        }   
    
    }
    
    
//    public void Candidate(){
//        System.out.println("\n\n Candidate tables are: ");
//        int k=0,i;
//        while(k<5){
//            String current="";
//             String previous="";
//             String additional="";
////            System.out.println("inside cabdidate  while");
//            System.out.print("\n\t");
//        for(int j=k; j<5;j++){
//          previous+=dataObjects[j];
//            for(i=j+1; i<5;i++){
//                current = previous;
////                System.out.println("Current: "+current);
//                int freq = checkFreq(current+additional);
////                System.out.println("Current: "+current+" its freq: "+freq);
//                if( freq < minThreshold){
//                    InvalidSet.add(current);
////                    System.out.println("current: "+ current);                    
////                    break; // Superset wont have enough frequency when the set doesnt have enough frequency.
//                } else {
//                    additional+=dataObjects[i];
//                    System.out.print("Itemset "+current+" : "+freq+" \t");                   
//                }
//            }           
//            previous = current;
//        }        
//         k++;
//        }
//        System.out.println("\n\n");
//    }
    
    public void recursion(String orig, String lookahead, int k){
       
//        for(int i=k;i<5; i++){
//           String obj = dataObjects[i];
        if(k<5){ // extra 0 in the dataobjects because of this.
           int freq = checkFreq(orig+lookahead);
           if( freq < minThreshold){
                recursion(orig,dataObjects[k+1],k+1);
                // Superset wont have enough frequency when the set doesnt have enough frequency.
                return;
//            return orig+dataObjects[k+1];   
           }
           else {
               orig = orig + lookahead;
               System.out.print("\t Itemset: "+orig+" : "+freq);
               recursion(orig,dataObjects[k+1],k+1);
               return;
//           print
           }
//        }
        } else {
            System.out.print("\n");
            return;
        }  
    }
    
    public int checkFreq(String current){
        int freq=0;
////        for(int i=0; i<InvalidSet.size();i++){
////            if( (InvalidSet.get(i)).matches("(.*)"+current+"(.*)")){
////                   System.out.print("Rejected set: "+current);
////                return 0;
////            }
////        }    
//        for(int i=0; i<transactions.length; i++){
//            if(transactions[i].matches("(.*)"+current+"(.*)"))  //same as finding sub string in a string.
//            {
//                freq++;
//            }
//        }        
//        return freq;
//    for(int i=0; i<transactions.length; i++){
        for(int i=0; i<TSet.size(); i++){
        int flag = 0;
        char[] characters = current.toCharArray();
            for (char c: characters){
//                 if (!transactions[i].contains(String.valueOf(c))){
                    if (!((TSet.get(i)).contains(String.valueOf(c)))){ 
                     flag=1;
//                                 System.out.print("\n"+ current+" is not part of "+ TSet.get(i));
                     break;                    
                 }                
            }
            if(flag==0){
                freq++;
            }
//            System.out.println("exit the normal loop too.");
        }    
        return freq;
    }

}
