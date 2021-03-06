
package frequentpattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class FPGrowth {
    
    int threshold;
    String Pattern="";
    String DB_user = "postgres";
    String DB_password = "tiger";
    int[] freq = new int[6];
    String dataObjects[] = {"A","B","C","D","E","0"}; //dont ask why extra 0. it works like that way!
    List<String> TSet = new ArrayList<String>();
    List<Item> fObj = new ArrayList<Item>();
    List<String> OrdItems = new ArrayList<String>();
        Item root = new Item();        
        Item child = new Item(); 
          
    
    FPGrowth(int threshold){
         root.itemAtNode="Root";
        this.threshold = threshold;
        int j,i=1;
        Connection c = null;
               
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost/jdbc",DB_user,DB_password);
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
                        freq[j-1]++;
                        trans += dataObjects[j-1]; 
                    }
                }
                TSet.add(trans);
                System.out.println("\tTransaction["+i+"]\t->\t"+TSet.get(i-1));
              i++;  
            }
            
            System.out. println("\n\n Support Table:\t");

        }catch(Exception e){
            System.out.println("Error retrieving the value!");
            e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
        }
          
         
         for(i=0; i<dataObjects.length; i++){
             Item item = new Item();
             if(freq[i] >= threshold ){
                   
                    item.itemAtNode = dataObjects[i];
                    item.supportAtNode = freq[i];
                    fObj.add(item);
             } 
         }
         
         Collections.sort(fObj, new Item());  // paramters are arrayList and comparator. new Item() here is referring to comparator
        //merge sort in Collections.sort         
        
        for(i=0; i<fObj.size(); i++){
            Pattern+=fObj.get(i).itemAtNode;
             System.out.println("\t"+fObj.get(i).itemAtNode+"  -> "+fObj.get(i).supportAtNode);
         }
        System.out.println("\n\tPattern: "+Pattern);
        
        createOrderedItemset();
    }  

    private void createOrderedItemset() {
        int i;
        System.out.println("\t\nTID\tItemSets\tOrdered Items: ");
        System.out.println("-------------------------------------");
       
        for(i=0; i<TSet.size(); i++){
        
        char[] characters = Pattern.toCharArray();
        String oItem = "";
        for (char ch: characters){
            if (TSet.get(i).contains(String.valueOf(ch))){ 
                        oItem+=String.valueOf(ch);
            }                
        }
        
            System.out.println("TID"+i+"\t"+TSet.get(i)+"\t\t"+oItem);
            OrdItems.add(oItem);
        }
        
        
        for(i=0; i<OrdItems.size(); i++){           
            System.out.println("\t\nAt Ordered set  "+ OrdItems.get(i)+"");
            createFPTree(OrdItems.get(i), root);
        }
    }

    public void createFPTree(String Itemset, Item node){
            int i,j;
          node = root; 
          String prefix = "Root"; 
        for(i=0;i<Itemset.length();i++){
            int flag = 0;
            System.out.print("At node: "+node.itemAtNode+" with Item "+ Itemset.charAt(i)+ ": \t");
            for(j=0; j<node.nextItem.size(); j++){
                child = node.nextItem.get(j);
                System.out.println("\t\t\tchild item node: "+child.itemAtNode + " compared with  itemset value: "+String.valueOf(Itemset.charAt(i)));
                 
                if(child.itemAtNode.equals(String.valueOf(Itemset.charAt(i)))){  //
                    prefix += "--> "+String.valueOf(Itemset.charAt(i));
                    flag = 1;
                   
                    node = node.nextItem.get(j);
                    node.supportAtNode++;
                    if(!node.itemAtNode.equals("Root")){
                        System.out.println("Increasing support count of "+node.itemAtNode);
                    }                    
                    break;
                }
            }
            
            if(flag == 0) {
                System.out.println("\t\t\t: Branching at "+node.itemAtNode+" node. \n Prefix being: "+prefix);
                Item sample = new Item();
                sample.itemAtNode = String.valueOf(Itemset.charAt(i));
                sample.supportAtNode = 1;
                node.nextItem.add(sample);
                prefix= prefix+" --> "+sample.itemAtNode;
                node = node.nextItem.get(0);
            }
        }
    }

class Item implements Comparator {
    String itemAtNode;
    int supportAtNode;
    ArrayList<Item> nextItem = new ArrayList<Item>();    

    @Override
    public int compare(Object o1, Object o2) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Item i1 = (Item)o1;
        Item i2 = (Item)o2;

        return Integer.valueOf(i2.supportAtNode).compareTo(i1.supportAtNode);
    }
}
}
