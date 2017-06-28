
package frequentpattern;

import java.util.Scanner;

public class FrequentPattern {    
    
    public static void main(String[] args) {
//        System.out.println("Select the type you want to do: ");
        System.out.print("Enter the threshold: ");
        Scanner scan = new Scanner(System.in);
        int threshold = scan.nextInt();
        System.out.println("\nEnter 0 for Apriori or 1 for FP");
        int option = scan.nextInt();
        if(option == 0){
                Apriori a = new Apriori(threshold);
//        a.Candidate();  
        } else if(option == 1){
            FPGrowth fptree = new FPGrowth(threshold);
        }
    
    }
    
}
