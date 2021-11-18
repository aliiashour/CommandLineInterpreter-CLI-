package cli;

import java.io.*;
import java.util.Scanner;

// Ahmed Gamal Ahmed 20190021 OS CS-S5
// Ali Ashour Mostafa 20190338 OS CS-S5


public class Parser {
    String commandName;
    String[] allCommands = {"echo", "pwd", "ls", "ls -r", "mkdir", "rmdir", "touch",
            "cp", "cp -r", "rm", "cat", "exit", "cd"} ;
    String[] args;

    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    //if the string is valid will return true else it returns false
    public boolean parse(String input){
        args = input.split(" ") ;
        String command = args[0] ;
        boolean correctCommand = false ;
        for(String i : allCommands ){
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
