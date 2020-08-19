import java.util.*;
import java.io.*;


class Reader{
    private  Scanner scan;
    public  void openFile(String FileName){
        try{
         scan = new Scanner(new File(FileName).getAbsoluteFile());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public  String[] readFile(){
        String[] policy  = new String[5];//store the parsing info in here
        int  i = 0; //count variable
        while(scan.hasNext()){
            policy[i] = scan.nextLine();//store the line in the ith index of policy
            i++;
        }
        return policy;//return the policy
    }

    public  void closeFile(){
        scan.close();
    }

    
    //this function will take in a string and parse the data into whatever type it is 
    public String[] parse_attributes(String line){
        line =  line.replace("ATTRS = ", "");//erase the first part of the line and remove the "ATTRS = " part
       line  = line.replaceAll("<|\\>|\\ ", "");//erase all of the <  || > || " " with 
       String[] attr = line.split(",|\\;"); //split the attributes by the delimiters , or ;
       return attr;
    }
    public String[] parse_perms(String line){
        line  = line.replace("PERMS = ", "");
        line  = line.replaceAll("<|\\>|\\ ", "");
        String[] perms = line.split(";");
        return perms;
    }
    public String parse_PA(String line){
        //TO DO
        return line;
    }
    public String[] parse_Entities(String line){
        line = line.replace("ENTITIES = ", "");//remove frst part of string
        line  = line.replaceAll("<|\\>","");//remove the brackets 
        String[] entities = line.split(";");
        return entities;
    }
    public String parse_AA(String line){
        //TO DO
        return line;
    }
}


class Container{//container class for our arrays we parsed in
    String[] s0;//holds all of the names and types of permissions
    String[] s1;// holds the permissions of the policy
    String s2;
    String[] s3; //hold the different entities in the policy
    String s4; // hold AA and will be parsed later on

}





