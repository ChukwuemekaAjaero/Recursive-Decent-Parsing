package com.ll1parser;

import java.io.*;
import java.util.ArrayList;

public class RecursiveDecentParsing {

    private static String token = ""; //Creates an empty string to store the values of the tokens.
    private static ArrayList<String> tokens;

    static {
        try {
            tokens = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int i;

    private static ArrayList<String> readFile() throws IOException {

        File input1 = new File("C:\\Users\\emeka\\Desktop\\Education\\3rd Year\\SEG2106 - Software Construction\\Assignment 3\\input3.txt");//This converts the text file into a form that Java can understand.
        BufferedReader br = new BufferedReader(new FileReader(input1));
        ArrayList<String> tokenList = new ArrayList<>();
        String st;

        while((st = br.readLine())!= null){//Scans through each line and adds it to the tokenList array list.
            tokenList.add(st);
        }

        try{
            File test = new File("C:\\Users\\emeka\\Desktop\\Education\\3rd Year\\SEG2106 - Software Construction\\Assignment 3\\input3.txt");
        } catch (Exception e){
            e.printStackTrace();
        }

        return tokenList;

    }

    public String getNextToken(){
        String line = "";

        try {

            line = br.readLine();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return line!=null?line: "";
        /*
        Note: return line!=null?line: ""; <- That is equivalent to:
        if(line!=null){
         return line;
        } else {
         return "";
        }
         */

    }

    public static void main(String[] args) throws IOException {//First pass of this method.
        // write your code here
/* Pseudo code for the main method.
Procedure: main ()
token  getNextToken();
if (expr() == ERROR || token != “$”) then
return ERROR;
else
return OK;
*/
tokens = readFile();

//Iterates through the entire array list.
for(int j = 0; j <tokens.size(); j++){
    i=j;
    token = tokens.get(i);

    if(expr() == false || token.equals("$") == false){

        System.out.println("Success");//Might run into the problem where it prints out multiple successes, but I'll cross that bridge when I get there.


    } else {

        System.out.println("Failure");
        return;
    }


}



    }

    public String getNextToken(){
        String line = "";

        try {

            line = br.readLine();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return line!=null?line: "";
    }

    private static boolean expr(){

            /*
Procedure: expr ()
if (term() == ERROR) then
return ERROR;
else return expr_prime();
*/

if(term() == false){
    return false;
} else{

    return exprPrime();
}
    }

    private static boolean exprPrime(){

        /*
Procedure: expr_prime ()
if (token == “+”) then
token  getNextToken();
return expr();
else if (token == “-”) then
token  getNextToken();
return expr();
// This is a special case where we need to process the epsilon
// by checking against the terminal in the FOLLOW(expr’) set
else if (token == “$”) then
return OK;
else return ERROR;
*/
        if(token.equals("+")){
            token = tokens.get(i);//Might need to delete this as i don't think it serves the right purpose. Either delete or use i+1;
            return expr();
        } else if(token.equals("-")){
            token = tokens.get(i);//Might need to delete this as i don't think it serves the right purpose. Either delete or use i+1;
            return expr();
        } else if (token.equals("$")){
            return true;
        } else {
            return false;
        }
    }

    private static boolean term(){

        /*
Procedure: term ()
if (factor() == ERROR) then
return ERROR;
else return term_prime();
*/

        if(factor() == false){
            return false;
        } else {
            return termPrime();
        }
    }

    private static boolean termPrime(){

        /*
Procedure: term_prime()
if (token == “*”) then
token  getNextToken();
return term();
else if (token == “/”) then
token  getNextToken();
return term();
// This is a special case where we need to process the epsilon
// by checking against the terminals in the FOLLOW(term’) set
else if (token == “$” || token==“+” || token==“-”) then
return OK;
else return ERROR;
*/

        if(token == "*"){
            token = tokens.get(i);//Might need to delete this as i don't think it serves the right purpose. Either delete or use i+1;
            return term();
        } else if (token.equals("/")){
            token = tokens.get(i);//Might need to delete this as i don't think it serves the right purpose. Either delete or use i+1;
            return term();
        } else if(token.equals("$") || token.equals("+") || token.equals("-")){
            return true;
        } else {

            return false;
        }
    }

    private static boolean factor(){

        /*
Procedure: factor ()
if (token == “num”) then
token  getNextToken();
return OK;
else if (token == “id”) then
token  getNextToken();
return OK;
else return ERROR;
*/
        if(token.equals("num")){
            token = getNextToken();//Might need to delete this as i don't think it serves the right purpose. Either delete or use i+1;
            return true;
        } else if(token.equals("id")){
            token = tokens.get(i);//Might need to delete this as i don't think it serves the right purpose. Either delete or use i+1;
            return true;
        } else {
            return false;
        }
    }

}
