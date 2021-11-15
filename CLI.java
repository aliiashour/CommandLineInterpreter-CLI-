/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;
import java.util.Scanner;
import java.util.Arrays;

/**
 *
 * @author ALI
 */
public class CLI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);  
        Parser p = new Parser() ; 
        Terminal t = new Terminal() ; 
        boolean exitLoop = false ; 
        while (true){
            exitLoop = false ; 
            System.out.print("Enter U Command: ");
            String input  = sc.nextLine() ;
            if ( p.parse(input) ){
                t.setParser(p);
                String commandName = t.parser.getCommandName() ; 
                switch ( commandName ){
                    case "echo":
                        System.out.println(t.echo());
                        break ;
                    case "pwd":
                        System.out.println(t.pwd());
                        break ;
                    case "mkdir":
                        t.mkdir();
                        break ;
                    case "rmdir":
                        t.rmdir();
                        break ;
                    case "touch":
                        t.touch();
                        break ;
                    case "ls":
                        t.ls();
                        break ;
                    case "cp":
                        t.cp();
                        break ;
                    case "cat":
                        System.out.println("CAT COMMAND :eee)");
                        break ;
                    case "exit":
                        System.out.println("Good Bye :)");
                        exitLoop = true ; 
                        break ;
                    default:
                        System.out.println("ERROR command::D");
                        break ; 
                }
            }else{
                System.out.println("ERROR Command::D");
            }
            if(exitLoop)
                break;
        }
        
        
        
    }
    
}
