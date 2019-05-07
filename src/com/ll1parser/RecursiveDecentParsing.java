package com.ll1parser;

import java.io.*;

/*
This is a solution to Exercise 2 from Assignment 3 for the Software Construction
Assignment 3.
 */

public class RecursiveDecentParsing {

    private String token = " ";  //This will store the value of the current token.
    private BufferedReader br;

    /*
    Recursive Decent Parser constructor which takes a file as its parameter.
    */
    public RecursiveDecentParsing(File inputFile){

        try {

            br = new BufferedReader(new FileReader(inputFile));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    Reads the next line in the file as the next token.
     */
    public String getNextToken(){
        String line = "";

        try {

            line = br.readLine();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(line!=null){
            return line;
        } else return "";
    }


    /**
     This method starts the parsing of the file.
     */
    public boolean parse(){

        token = getNextToken();

        //This processes the non-terminal "program" and the checks if the last token is $ which signifies the end of the stream.
        if (!program() || !token.equals("$")){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Processes the following grammar rule:
        <program> ::= begin <statement_list> end
     * Returns true if the corresponding grammar rule is respected and false otherwise
     */
    public boolean program(){

        //According to the VSPL grammar given, the non-terminal "program" must start with the terminal begin.
        if(token.equals("begin")) {
            //If we have a match, get the next token and check if it satisfies the non-terminal "statement_list"
            token = getNextToken();
            if(!statementList()){
                //If there's a syntax error while processing statement_list, return false.
                return false;
            }
            else{
                if (token.equals("end")){
                    //If there is no syntax error after processing statement_list, the next terminal must be "end"
                    token = getNextToken();
                    return true;
                }
                else {
                    //If the terminal "end" is not there, return false.
                    return false;
                }
            }
        }
        else {
            //If the terminal "begin" is not there in the first place, return false.
            return false;
        }
    }



    /**
     * Processes the following grammar rule:
     * <statement_list> ::= <statement> ; <statement_list’>
     * Returns true if the corresponding grammar rule is respected and false otherwise
     */
    public boolean statementList(){

        // process the statement non-terminal
        if(!statement()){

            // somewhere while processing the statement non-terminal, we had a syntax error
            return false;
        }
        else if(token.equals(";")){
            // we have a match, get next token
            token = getNextToken();

            // process the statment_list' non-terminal
            return statementListPrime();
        }
        else{
            // we are missing the ; terminal
            return false;
        }
    }


    /**
     * Processes the following grammar rules:
     * <statement_list’> ::= <statement_list>
     * <statement_list’> ::= epsilon
     * Returns true if the corresponding grammar rules are respected and false otherwise
     */
    public boolean statementListPrime(){

        if(token.equals("end")){


            return true;
        }

        // process the statment_list non-terminal
        else return statementList();

    }

    /**
     * Processes the following grammar rule:
     * <statement> ::= id = <expression>
     * Returns true if the corresponding grammar rule is respected and false otherwise
     */
    public boolean statement(){

        // The statement rule must start with an id terminal
        if(token.equals("id")){

            token = getNextToken();

            if(token.equals("=")){

                token = getNextToken();

                return expression();
            }
            else {
                //Returns false if the = terminal is missing.
                return false;
            }
        }
        else {

           //Returns false if the id terminal is missing.
            return false;
        }
    }


    /**
     * Processes the following grammar rule:
     * <expression> ::= <factor> <expression’>
     * Returns true if the corresponding grammar rule is respected and false otherwise
     */
    public boolean expression(){

        if(!factor()){

            return false;
        }
        else{
            return expressionPrime();
        }
    }


    /**
     * Processes the following grammar rules:
     * <expression’> ::= + <factor>
     * <expression’> ::= - <factor>
     * <expression’> ::= epsilon
     * Returns true if the corresponding grammar rules are respected and false otherwise
     */
    public boolean expressionPrime(){

        if(token.equals("+")){

            token = getNextToken();

            return factor();
        }
        else if(token.equals("-")){

            token = getNextToken();

            return factor();
        }
        else if (token.equals(";")){

            // Returns true since we have an epsilon rule
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Process the following grammar rules:
     * <factor> ::= id
     * <factor> ::= num
     * Returns true if the corresponding grammar rules are respected and false otherwise
     */
    public boolean factor(){

        // the factor rule must start with the id or num
        if (token.equals("id")) {
            // we have a match, get next token
            token = getNextToken();
            return true;
        }
        else if (token.equals("num")) {
            // we have a match, get next token
            token = getNextToken();
            return true;
        }
        else{
            //Returns false if both the id and num terminals are missing.
            return false;
        }
    }


    public static void main(String [] args){

        File file = new File("C:\\Users\\emeka\\Desktop\\Education\\3rd Year\\SEG2106 - Software Construction\\Assignment 3\\input1.txt");

        if (file.isFile()){
            RecursiveDecentParsing parser = new RecursiveDecentParsing(file);

            // Parse the input file
            boolean success = parser.parse();

            // Display results
            if (success){
                System.out.println("SUCCESS: the code has been successfully parsed");
            }
            else {
                System.out.println("ERROR: the code contains a syntax mistake");
            }
        }

    }
}
