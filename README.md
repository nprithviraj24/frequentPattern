# Frequent Patterns

Frequent Pattern algorithms are used in important subfield of data mining that is called frequent pattern mining or simply, pattern mining. Pattern mining consists of using as well as developing data mining algorithms to discover interesting, unexpected and useful patterns in databases. This project includes two popular algorithms Apriori and FP Growth.

# Getting Started

Clone the project into your-workspace/project-folder.

## Prerequisites

   - Since project is written in JAVA, you need Java SE Development Kit in order to run it, of course we can have other versions too but this is the fundamental kit. Personally I'd recommend to use any IDE.
   - Object-relational database management system (ORDBMS) so that we can obtain data from it inorder to find unique pattern.

  ## Running the tests
    
   Initially select any Database, since I've worked with Postgres; screenshot of sample database.
   ![Database](/database.png?raw=true "Database Example")

  ### Assumptions
        There are five items: A, B, C, D, and E. 
        "1" signifies that item is bought at that transaction, 0 for otherwise.
        Six transactions are recorded.

  ## Procedure

    1. Set the path of the database accordingly.
    2. Run the main class that is FrequentPattern.java.
    3. Threshold in this case can vary upto 1 to 4 for best results.
    4. Select either "0" for Apriori, anything but "0" for FP Growth.

# Author

~ Prithvi Raj
