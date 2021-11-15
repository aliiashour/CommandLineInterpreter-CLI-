/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;
import java.util.Arrays;



/**
 *
 * @author ALI
 */
public class Parser {
    String commandName;
    String[] allCommands = {"echo", "pwd", "ls", "ls -r", "mkdir", "rmdir", "touch",
                            "cp", "cp -r", "rm", "cat", ">", ">>", "exit"} ; 
    String[] args;

    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user 
    //if the string is falid will return true else it return false
    public boolean parse(String input){
        args = input.split(" ") ; 
        String command = args[0] ; 
        boolean correctCommand = false ; 
        for(String i : args ){
            if(i.equals(command)){
                correctCommand = true  ; 
                break ; 
            }
        }
        commandName = command ;         
        return correctCommand ;
    } 
    
    public String getCommandName(){
        return commandName ; 
    }
    
    public String[] getArgs(){
        return args ; 
    }
}
