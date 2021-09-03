import java.util.Scanner;
import java.util.*;
import java.io.*;

class Bank{
      static String Bold = "\033[0;1m";
      static String BoFF = "\033[0;0m";
      static String italic = "\033[3m";
      public static final String ANSI_RESET = "\u001B[0m";
      public static final String ANSI_RED = "\033[0;1m\u001B[31m";
      public static final String ANSI_GREEN = "\033[0;1m\u001B[32m";
      public static final String ANSI_YELLOW = "\033[0;1m\u001B[33m";
      public static final String ANSI_BLUE = "\u001B[34m";
      public static final String ANSI_WHITE = "\033[0;1m\u001B[37m";

      public static HashMap<String, String[]> Hash = new HashMap<String, String[]>();
      public static HashMap<String, String> Cus_Acc = new HashMap<String,String>();  
      public static ArrayList<Customer> SortBal = new ArrayList<Customer>();
      public static HashMap<String,LinkedList<String>> Pazh = new HashMap<String,LinkedList<String>>();

      static int Cus_Id;
      static int AccountNumber;
      public static Scanner sc = new Scanner(System.in); 
      /**
       * Writes Data to Files on Each call by using Customer ID
       * @param y
       */
      
      public void MyFile(String[] y)
      {
            try
            {
                  File x = new File("Transaction_History/"+y[0]+".txt");
                  File z = new File("Password_History/"+y[0]+".txt");
                  PrintWriter Ps = new PrintWriter(new FileWriter(z,true));
                  Ps.print(String.format(""));
                  Ps.close();


                  PrintWriter P = new PrintWriter(new FileWriter(x,true));
                  P.println("--------------------------------");
                  P.println(String.format("|%20s%10s|\n|%15s%-15s|\n|%15s%-15s|\n|%15s%-15s|","Account Statement","","Name -",y[2],"Account No -",y[1],"CustomerID -",y[0]));
                  P.println("--------------------------------");

                  P.println("---------------------------------------------------------------------");
                  P.println(String.format("|  %-15s%-25s%-15s%-10s%s","TransId","TransferType","Amount","Balance","|"));
                  P.println("---------------------------------------------------------------------");
                  P.flush();
                  P.close();
            }
            catch(Exception e)
            {
                  System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
            }
      }

      /**
       * Displays All the Customer Details From HashMap()
       */
      public void Display()
      {
            int size = Hash.size();
            for(int i=1;i<=size;i++)
            {
                  String[] sh = Hash.get(String.valueOf(i));
                  if(sh!=null){
                        System.out.printf("|  %-15s%-15s%-15s%-10s%s",sh[0],sh[1],sh[2],sh[3],"|");
                        System.out.println();   
                  }
               
            }
            System.out.println("-----------------------------------------------------------");
      }
      /**
       * Adding Customer Details & Assign Default Account Balance ,
       * Customer Id and AccountNumber. --->>
       * @throws Exception
       */

      public Boolean AddCustomers() throws IOException
      {

            String Name,Balance,Pass,repass;
            
            System.out.printf(ANSI_WHITE+"%-20s:\t","Enter Name");
            Name = sc.nextLine();
            Balance = String.valueOf(10000);
            System.out.printf(ANSI_WHITE+"%-20s:\t","Enter Password");
            Pass = sc.nextLine();
            System.out.printf(ANSI_WHITE+"%-20s:\t","Re-Type Password");
            repass = sc.nextLine();

            if(!Pass.equals(repass) )
            {
                  System.out.println(ANSI_RED+"Password Mismatch !!!"+ANSI_RESET);
                  AddCustomers();
            }
            else if(Pass.equals(repass))
            {
                  if(PassComx(Pass))
                  {
                        PrintWriter p = new PrintWriter(new FileWriter("zoho.txt",true));
                        Pass = encrypt(Pass);

                        Cus_Id++;AccountNumber++;
                        String a = String.format("%-15s%-15s%-15s%-15s%s",Cus_Id,AccountNumber,Name,Balance,Pass);

                        p.print("\n"+a);
                        p.flush();
                        p.close();
                        
                        String[] all = a.split("\\s+");
                        Hash.put(String.valueOf(Cus_Id),all);
                        Cus_Acc.put(all[1],all[0]);
                        MyFile(all);
                        return true;

                  }
                  else
                  {
                        System.out.println(ANSI_RED+"Too Weak Password !!!\n"+BoFF+ANSI_RESET);
                  }
            }
            return false;
            
      }
      /**
       * Encryption and Decryption Is Done Here
       * Constrains Are : Adding +1 to the Character on Encryption and -1 On Decryption ;
       * --->> --->>
       */
      public String encrypt(String x)
      {
            StringBuffer b = new StringBuffer();
            for(int i=0;i<x.length();i++)
            {
                  int y = (x.charAt(i)+1);
                  if(y==91){
                        y = 65;}
                  else if(y==58){
                        y=48;}
                  else if(y == 123){
                        y=97;}
                  b.append(String.valueOf((char)y));
            }
            String y = new String(b);
            return y;
      }

      public String Decrypt(String x){
            StringBuffer b = new StringBuffer();
            for(int i=0;i<x.length();i++)
            {
                  int y = (x.charAt(i)-1);
                  if(y==64){
                        y = 90;}
                  else if(y==47){
                        y=57;}
                  else if(y == 96){
                        y=122;}
                  b.append(String.valueOf((char)y));
            }
            String y = new String(b);
            return y;
      }


public Boolean PassComx(String Pass)
      {
            int CapCount=0,NumCount=0,SmallCount=0;
            for(int i=0;i<Pass.length();i++)
            {
                  if(Character.isUpperCase(Pass.charAt(i)))
                        CapCount++;
                  if(Character.isLowerCase(Pass.charAt(i)))
                        SmallCount++;
                  if(Character.isDigit(Pass.charAt(i)))
                        NumCount++;
            }
            if(Pass.length()>=6 && CapCount>=2 && NumCount>=2 && SmallCount >=2)
            {
                  return true;
            }
            return false;
      }
}

/**
 *     
 *    // ATM Constructor
 *    // ATM Withdrawal
 *    // Deposit to Current Account
 *    // Transfer Amount to Another Account
 *    // Transaction History
 *    // FilePrints() on each Activity 
 * 
 */
class ATM extends Bank{

      public ATM()
      {
            System.out.printf(ANSI_WHITE+"%-20s:\t","Enter CustomerID");
            String ID = sc.nextLine();
            System.out.printf(ANSI_WHITE+"%-20s:\t","Enter Password");
            String PassCheck = sc.nextLine();
            String[] ie = Hash.get(ID);
            int count=0;

            if(ie==null)
            {
                  System.out.println(ANSI_RED+"Invalid User ID !!!"+ANSI_RESET);
            }

            String de = Decrypt(ie[4]);
            if(PassCheck.equals(de))
            {
                  // Read And Takes the Count of Lines to Find 
                  // Transaction Count.
                  try{
                        File F = new File("Transaction_History/"+ID+".txt");
                        BufferedReader Read = new BufferedReader(new FileReader(F));
                        while(Read.readLine()!=null)
                        {
                              count++;
                        }
                        Read.close();
                  }catch(Exception e)
                  {
                        System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
                  }
                  Boolean bee;

                  // Force Password Change
                  if((count!=10) && ((count-10)%5==0)){
                        do{
                              bee =  ChangePassword(ID, ie);
                        }while(bee!=true);
                  }
                  if((count!=10) && (count-10)%10==0)
                  {
                        BankFee(ID,"Maintanence Fee",String.valueOf(100));

                  }
    
                  System.out.printf(Bold+ANSI_GREEN+"Welcome %s !\n",ie[2]);
                  System.out.println("---------------------------------");
                  System.out.println("|\t* ATM Withdrawal\t|");
                  System.out.println("|\t* Cash Deposit\t\t|");
                  System.out.println("|\t* Account Transfer\t|");
                  System.out.println("|\t* Transaction History\t|");
                  System.out.println("|\t* Change Password\t|");
                  System.out.println("---------------------------------"+ANSI_RESET);

                  System.out.printf(ANSI_WHITE+"%-25s:\t","ENTER YOUR OPTION"+BoFF);
                  String Choice = sc.nextLine();
                  switch(Integer.parseInt(Choice))
                  {
                        case 1:
                              ATMdraw(ID);
                              break;
                        case 2:
                              Deposit(ID);
                              break;
                        case 3:
                              Transfer(ID);
                              break;
                        case 4:
                              Transaction_History(ID);
                              break;
                        case 5:
                              ChangePassword(ID,ie);
                              break;
                        default :
                              System.out.println(ANSI_RED+"Invalid Option !!!"+BoFF+ANSI_RESET);

                  }
            }
            else if(!PassCheck.equals(de))
            {
                  System.out.println(ANSI_RED+"Wrong Password : "+PassCheck+ANSI_RESET+"\n"+ANSI_GREEN+"Correct Password : "+de +ANSI_RESET);
            }
      }
      /**
       * Function to Withdraw of Amount.
       * Constrain : Account Balance Must Be above 1000.
       * @param ID
       */
      public void ATMdraw(String ID)
      {
            System.out.printf("%-20s:\t","Withdrawal Amount");
            String Amt = sc.nextLine();
            String[] User = Hash.get(ID);
            int Balance = Integer.parseInt(User[3]);
            int At = Integer.parseInt(Amt);
            if(Balance>1000)
            {
                  int x = Balance-At;
                  if(x>1000)
                  {
                        User[3] = String.valueOf(x);
                        Hash.replace(ID,User);
                        FilePrints(User[0],"ATMWithdrawal",String.valueOf(At),String.valueOf(x));

                  }else if(x<1000)
                  {
                        System.out.println(ANSI_RED+"Sorry !!! Insufficient Balance");
                  }
            }
      }



      /**
       * Deposit to User Id and Pereform Addition Of Balance ;
       * @param ID
       */
      public void Deposit(String ID)
      {
            System.out.printf("%-20s\t","Amount To Deposit :");
            String Amt = sc.nextLine();
            String[] User = Hash.get(ID);
            int z = Integer.parseInt(User[3])+Integer.parseInt(Amt);
            User[3] = String.valueOf(z);
            Hash.replace(ID, User);
            FilePrints(User[0],"CashDeposit",Amt,String.valueOf(z));

      }

      /**
       * MyAccount = From;
       * ToAccount = To;
       * Transferring Money to the Beneficiery account ;
       * Before Transfer we are validating the Minimum Balance ;
       * @param From
       */
      public void Transfer(String From)
      {
            System.out.printf(ANSI_WHITE+"%-20s","Beneficiary's Account Number:\t");
            String To = sc.nextLine();
            System.out.printf("%-20s","Amount To Transfer :\t");
            String Amt = sc.nextLine();
            
            String[] FromAcc = Hash.get(From);
            String[] ToAcc = Hash.get(Cus_Acc.get(To));

            int Paise = (Integer.parseInt(FromAcc[3])) - (Integer.parseInt(Amt));
            if(Paise > 1000)
            {
                  if(Paise > 5000)
                  {
                        BankFee(FromAcc[0],"Operational Fee",String.valueOf(10));
                  }
                  FromAcc[3] = String.valueOf(Paise);
                  ToAcc[3] = String.valueOf(Integer.parseInt(ToAcc[3])+Integer.parseInt(Amt));
                  
                  Hash.replace(From,FromAcc);
                  Hash.replace(ToAcc[1],ToAcc);

                  String x = String.format("%s%s","Transfer To ",To);
                  String y = String.format("%s%s","Transfer From ",FromAcc[1]);
                  FilePrints(FromAcc[0],x,Amt,String.valueOf(Paise));
                  FilePrints(ToAcc[0],y,Amt,String.valueOf(ToAcc[3]));
            }
            else if(Paise < 1000)
            {
                  System.out.println(ANSI_RED+"Sorry !!! Insufficient Balance"+ANSI_RESET);
            }


      }
      
      /**
       * Maintains Transation History For Each and Every User
       * @param ID
       */
      public void Transaction_History(String ID)
      {
            try
            {
                  BufferedReader br = new BufferedReader(new FileReader("Transaction_History/"+ID+".txt"));
                  String Line;
                  while((Line=br.readLine())!=null)
                  {
                        System.out.println(Line);
                  }
                  System.out.println("---------------------------------------------------------------------");
                  br.close();
            }catch(Exception e)
            {
                  System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
            }
      }

      /**
       * Prints to user Activity on each and Every Activity.
       */
      public void FilePrints(String user ,String Type,String Amount,String Balance)
      {
            int TransCount = 0;
            
            try
            {
                  BufferedReader br = new BufferedReader(new FileReader("Transaction_History/"+user+".txt"));
                  while((br.readLine())!=null)
                  {
                        TransCount++;
                  }
                  PrintWriter P = new PrintWriter(new FileWriter(new File("Transaction_History/"+user+".txt"),true));
                  P.println(String.format("|  %-15s%-25s%-15s%-10s%s",String.valueOf(TransCount-9),Type,Amount,Balance,"|"));
                  P.flush();
                  P.close();
                  br.close();

            }
            catch(IOException e)
            {

                  System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
            }
      }
      /**
       * Writer to write on each and every Activity ..!!
       * @param ID
       */
      public Boolean ChangePassword(String ID,String[] ie)
      {
            System.out.printf(ANSI_WHITE+"%-20s","Enter New PassWord:\t");
            String NewPass = sc.nextLine();
            System.out.printf(ANSI_WHITE+"%-20s:\t","Re-Enter Password");
            String RePass = sc.nextLine();

            if(NewPass.equals(RePass))
            {
                  Boolean b = PassComx(NewPass);
                  if(b)
                  {
                        ie[4] = String.valueOf(encrypt(NewPass));
                        if(PassLink(ID,ie[4]))
                        {
                              System.out.println(ANSI_RED+"Password Reused !! Try New_Password ...");
                        }
                        else{
                              Hash.replace(ID,ie);
                              System.out.println(ANSI_GREEN+"Password Changed Succesfully..!!!"+ANSI_RESET);
                              return true;
                        }
                  }
                  else{
                        System.out.println(ANSI_RED+"Too Weak Password !!!\nPassword Not Changed..!!"+BoFF+ANSI_RESET);
                  }
            }
            else{
                  System.out.println(ANSI_RED+"Password Mismatch !!!"+ANSI_RESET);
            }
            return false;
      }

      /**
       * On Changing the Password Each Time it Stores in a Hashmap<String,LinkedList<String>>
       * on above 3 users it Removes the First Old Password and Adds new Password At Last
       * @param ID
       * @param NewPass
       */
      public Boolean PassLink(String ID , String NewPass)
      {

            //Checks For Password Reuse
            LinkedList<String> History = Pazh.get(ID);
            for(int i=0;i<History.size();i++)
            {
                  if(History.get(i).equals(NewPass))
                  {
                        return true;
                  }
            }

            //Replace NewPass when Password Not Matched PazhHistory
            if(History.isEmpty() || History.size()<3)
            {
                  History.add(NewPass);
                  Pazh.replace(ID,History);
            }
            else if(History.size()==3)
            {
                  History.removeFirst();
                  History.addLast(NewPass);
                  Pazh.replace(ID,History);
            }
            return false;
      }

      public void BankFee(String ID,String Msg ,String Amt)
      {
            String[] User = Hash.get(ID);
            int Balance = Integer.parseInt(User[3]);
            int At = Integer.parseInt(Amt);

            int x = Balance-At;
            
            User[3] = String.valueOf(x);
            Hash.replace(ID,User);
            FilePrints(User[0],Msg,String.valueOf(At),String.valueOf(x));
      }
}
 

class Customer{
  
      String Cus_Id;
      String AccountNo;
      String Name;
      Integer Balance;
      // constructor
      Customer(String Cus_Id,String AccountNo,String Name,Integer Balance)
      {
            this.Cus_Id = Cus_Id;
            this.AccountNo = AccountNo;
            this.Name = Name;
            this.Balance = Balance;
      }
}
    
  // creates the comparator for comparing Balance
class BalanceComparator implements Comparator<Customer> {
    
      // override the compare() method
      public int compare(Customer c1, Customer c2)
      {
          return -(c1.Balance.compareTo(c2.Balance));
      }
  }


public class Main{
      static Bank b = new Bank();
      static String Bold = "\033[0;1m";
      static String BoFF = "\033[0;0m";
      static String italic = "\033[3m";
      public static final String ANSI_RESET = "\u001B[0m";
      public static final String ANSI_RED = "\033[0;1m\u001B[31m";
      public static final String ANSI_GREEN = "\033[0;1m\u001B[32m";
      public static final String ANSI_YELLOW = "\033[0;1m\u001B[33m";
      public static final String ANSI_WHITE = "\033[0;1m\u001B[37m";
      public static final String ANSI_BLUE = "\u001B[34m";
      static Scanner st = new Scanner(System.in); 

      public Main()
      {
            File Folder = new File("Transaction_History");
            File PFolder = new File("Password_History");
            File LastAct = new File("LastAct.txt");
            Folder.mkdir();
            PFolder.mkdir();
            try {

                  //Customer Id & Account Number Remembering
                  BufferedReader bz = new BufferedReader(new FileReader(LastAct));
                  String[] Act = bz.readLine().split(" ") ;
                  Bank.Cus_Id = Integer.parseInt(Act[0]);
                  Bank.AccountNumber= Integer.parseInt(Act[1]);
                  bz.close();
                  
                  //Reads And Uploads to HashMap
                  BufferedReader br = new BufferedReader(new FileReader("zoho.txt"));
                  String Line;
                  while ((Line = br.readLine()) != null){
                        String[] y = Line.split("\\s+");
                        String CustomerId = y[0];

                        Bank.SortBal.add(new Customer(y[0], y[1], y[2], Integer.parseInt(y[3])));
                        Bank.Hash.put(CustomerId, y);
                        Bank.Hash.put(CustomerId, y);
                        Bank.Cus_Acc.put(y[1],CustomerId);
                        File Filest = new File("Transaction_History/"+y[0]+".txt");
                        if(!Filest.exists())
                        {
                              try
                              {
                                    File x = new File("Transaction_History/"+y[0]+".txt");
                                    PrintWriter P = new PrintWriter(new FileWriter(x,true));
                                    P.println("--------------------------------");
                                    P.println(String.format("|%20s%10s|\n|%15s%-15s|\n|%15s%-15s|\n|%15s%-15s|","Account Statement","","Name -",y[2],"Account No -",y[1],"CustomerID -",y[0]));
                                    P.println("--------------------------------");
                                    P.println();

                                    P.println("---------------------------------------------------------------------");
                                    P.println(String.format("|  %-15s%-25s%-15s%-10s%s","TransId","TransferType","Amount","Balance","|"));
                                    P.println("---------------------------------------------------------------------");
                                    P.flush();
                                    P.close();
                              }
                              catch(Exception e)
                              {
                                    System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
                              }
                        }
                        File PassF = new File("Password_History/"+y[0]+".txt");
                        if(!PassF.exists())
                        {
                              try{
                                    File z = new File("Password_History/"+y[0]+".txt");
                                    PrintWriter Pazz = new PrintWriter(new FileWriter(z));
                                    Pazz.println(y[4]);
                                    Pazz.flush();
                                    Pazz.close();
                              }
                              catch(Exception e)
                              {
                                    System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
                              }
                        }
                        BufferedReader pr = new BufferedReader(new FileReader(PassF));
                        LinkedList<String> xz = new LinkedList<String>();
                        String Paz;
                        while((Paz=pr.readLine())!=null)
                        {
                              xz.add(Paz);
                        }
                        Bank.Pazh.put(y[0],xz);
                        pr.close();

                  }
                  Collections.sort(Bank.SortBal,new BalanceComparator());
                  br.close();
            } 
            catch (Exception e) {
                  e.printStackTrace();
                  //System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
            }
      }
      public static void main(String[] args){
            new Main();            
            String ch;
            do
            {
                  System.out.println(Bold+ANSI_BLUE+"____ ___  _  _  ___    ___   _   _  _ _  __ ");
                  System.out.println("|_  / _ \\| || |/ _ \\  | _ ) /_\\ | \\| | |/ /");
                  System.out.println(" / / (_) | __ | (_) | | _ \\/ _ \\| .` | ' < ");
                  System.out.println("/___\\___/|_||_|\\___/  |___/_/ \\_\\_|\\_|_|\\_\\");
                  System.out.println("____ ___  _  _  ___    ___   _   _  _ _  __ "+ANSI_RESET);
                  System.out.println();

                  System.out.println(ANSI_GREEN+"---------------------------------");
                  System.out.println("|\t1. Add Customer\t\t|");
                  System.out.println("|\t2. Display Users\t|");
                  System.out.println("|\t3. ATM Auth\t\t|");
                  System.out.println("|\t4. Top Users\t\t|");
                  System.out.println("|\t0. Exit Program\t\t|");
                  System.out.println("---------------------------------"+ANSI_RESET);
                  System.out.printf(ANSI_WHITE+"%-20s:\t","ENTER YOUR OPTION"+BoFF);

                  ch = st.nextLine();
                  System.out.println();
                  try{
                        switch(Integer.valueOf(ch)){
                              case 1:
                                    System.out.println(ANSI_YELLOW+"-----------------------------------");
                                                System.out.println("╔═╗╔╦╗╔╦╗  ╔═╗╦ ╦╔═╗╔╦╗╔═╗╔╦╗╔═╗╦═╗");
                                                System.out.println("╠═╣ ║║ ║║  ║  ║ ║╚═╗ ║ ║ ║║║║║╣ ╠╦╝");
                                                System.out.println("╩ ╩═╩╝═╩╝  ╚═╝╚═╝╚═╝ ╩ ╚═╝╩ ╩╚═╝╩╚═");
                                    System.out.println(ANSI_YELLOW+"-----------------------------------"+ANSI_RESET);
                                    try{
                                          if(b.AddCustomers())
                                          {
                                                System.out.println("\n"+ANSI_GREEN+"CUSTOMER ADDED"+BoFF);
                                          }
                                          else{
                                                System.out.println(ANSI_RED+"CUSTOMER NOT ADDED"+BoFF);
                                          }
                                    }catch(IOException e){
                                          System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
                                    }
                                    break;
                              case 2:
      
                                    System.out.println(ANSI_YELLOW+"------------------------");
                                                System.out.println("╔═╗╔═╗╔═╗╔═╗╦ ╦╔╗╔╔╦╗╔═╗");
                                                System.out.println("╠═╣║  ║  ║ ║║ ║║║║ ║ ╚═╗");
                                                System.out.println("╩ ╩╚═╝╚═╝╚═╝╚═╝╝╚╝ ╩ ╚═╝");
                                                System.out.println("------------------------\n"+ANSI_RESET);
                                    System.out.println("-----------------------------------------------------------");
                                    System.out.printf(Bold+ANSI_GREEN+"|  %-15s%-15s%-15s%-10s%s\n"+BoFF+ANSI_RESET,"CustomerID","AccountNo","Name","Balance","|");
                                    System.out.println("-----------------------------------------------------------");
      
                                    b.Display();
                                    break;
                              case 3:
                                    System.out.println(ANSI_YELLOW+"----------");
                                                System.out.println("╔═╗╔╦╗╔╦╗");
                                                System.out.println("╠═╣ ║ ║║║");
                                                System.out.println("╩ ╩ ╩ ╩ ╩");
                                                System.out.println("----------"+ANSI_RESET);
                                    new ATM();
                                    break;

                              case 4:
                                    System.out.println(ANSI_YELLOW+"--------------------------");
                                                System.out.println("╔╦╗╔═╗╔═╗  ╦ ╦╔═╗╔═╗╦═╗╔═╗");
                                                System.out.println(" ║ ║ ║╠═╝  ║ ║╚═╗║╣ ╠╦╝╚═╗");
                                                System.out.println(" ╩ ╚═╝╩    ╚═╝╚═╝╚═╝╩╚═╚═╝");
                                                System.out.println("--------------------------"+ANSI_RESET);
                                    System.out.printf("%-20s",Bold+"No Of Users :\t");
                                    Scanner ie = new Scanner(System.in);
                                    String xy = ie.nextLine();
                                    Integer y = Integer.parseInt(xy);
                                    if(y<Bank.SortBal.size())
                                    {
                                          System.out.println("-----------------------------------------------------------");
                                          System.out.printf(Bold+ANSI_GREEN+"|  "+"%-15s%-15s%-15s%-10s%s\n"+BoFF+ANSI_RESET,"CustomerID","AccountNo","Name","Balance","|");
                                          System.out.println("-----------------------------------------------------------");
                                          for(int i=0;i<y;i++)
                                          {
                                                Customer c = Bank.SortBal.get(i);
                                                System.out.printf("|  %-15s%-15s%-15s%-10s%s\n",c.Cus_Id,c.AccountNo,c.Name,c.Balance,"|");
                                          }
                                          System.out.println("-----------------------------------------------------------");
                                    }
                                    else
                                    {
                                          System.out.println(ANSI_RED+"User Limit Exceeded");
                                    }
                                    break;
      
                              case 0:
                                    //Writes the Latest  Updations to File Before Closing
                                    File Fe = new File("zoho.txt");
                                    Fe.delete();
                                    try{
                                          Fe = new File("zoho.txt");
                                          Fe.createNewFile();
                                          PrintWriter pe = new PrintWriter(Fe);
                                          for(int i=0;i<Bank.Hash.size();i++)
                                          {
                                                String[] he = Bank.Hash.get(String.valueOf(i+1));
                                                if(he!=null)
                                                {
                                                      pe.println(String.format("%-15s%-15s%-15s%-15s%s",he[0],he[1],he[2],he[3],he[4]));
                                                }

                                          }
                                          pe.flush();
                                          pe.close();

                                    }catch(IOException e)
                                    {
                                          System.out.println(ANSI_RED+Bold+e.getMessage()+ANSI_RESET);
                                    }
                                    catch(Exception e)
                                    {
                                          e.printStackTrace();
                                    }


                                    //PassWord Writer :
                                    try{
                                          for(int j=0;j<Bank.Pazh.size();j++){
                                                LinkedList<String> History = Bank.Pazh.get(String.valueOf(j+1));
                                                PrintWriter P = new PrintWriter(new FileWriter(new File("Password_History/"+j+".txt")));
                                                for(int k=0;k<History.size();k++)
                                                {
                                                      P.println(History.get(k));
                                                }
                                                P.flush();
                                                P.close();

                                          }
                                    }catch(IOException e)
                                    {
                                          System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
                                    }
                                    catch(Exception e)
                                    {
                                          e.printStackTrace();
                                    }

                                    // LastAct Writer writer CustomerID and Account NUmber;
                                    try{
                                          PrintWriter Pz = new PrintWriter(new FileWriter("LastAct.txt"));
                                          Pz.print(String.format("%d %d",Bank.Cus_Id,Bank.AccountNumber));
                                          Pz.flush();
                                          Pz.close();
                                    }catch(IOException e)
                                    {
                                          System.out.println(ANSI_RED+e.getMessage()+ANSI_RESET);
                                    }
                                    catch(Exception e)
                                    {
                                          e.printStackTrace();
                                    }


                                    System.out.println(italic+ANSI_BLUE+"Bye Bye !!!");
                                    break;
                              default:
                                    System.out.println(ANSI_RED+"INVALID INPUT !!!"+ANSI_RESET);
                        }
                        System.out.println(ANSI_WHITE+"\n---------------------------------------------------------------------"+ANSI_RESET);

                  }
                  catch(Exception e){
                        System.out.println(ANSI_RED+"INVALID INPUT !!!"+ANSI_RESET);
                        ch="99";
                        System.out.println(ANSI_WHITE+"\n---------------------------------------------------------------------"+ANSI_RESET);
                  }
            }while(Integer.valueOf(ch)!=0);
            st.close();
      }
}