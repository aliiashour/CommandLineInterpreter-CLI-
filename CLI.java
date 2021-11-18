/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author ALI
 */
public class CLI {


    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        Parser myParser = new Parser();
        Terminal myTerminal = new Terminal();
        while (true) {
            System.out.print(myTerminal.getCurrPath()+ ">");
            String input = in.nextLine();
            if (myParser.parse(input))
            {
                myTerminal.setParser(myParser);
                String commandName = myTerminal.parser.getCommandName();
                myTerminal.chooseCommandAction(commandName);
            }
            else
            {
                System.out.println(myParser.getCommandName() + " is not recognized as an internal or external command,");
            }
        }
    }
    
}
