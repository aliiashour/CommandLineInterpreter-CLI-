/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;
import java.io.File;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


/**
 *
 * @author ALI
 */

public class Terminal {
    Parser parser;
    public Terminal(Parser p){
        parser = p ; 
    }
    public Terminal(){
        parser = null ; 
    }
    public void setParser(Parser p){
        parser = p ; 
    }
    //Implement each command in a method, for example:
    public String pwd(){
        return System.getProperty("user.dir");
    } 
    
    public String echo(){
        String res = "" ; 
        
        String[] args = parser.getArgs() ;
        
        for (int i = 1; i < parser.getArgs().length; i++) {
            res = res.concat(args[i] + " ") ;
        }
        
        res = res.substring(0, res.length()-1) ;
        
        return res; 
    }
    
    public void mkdir(){
        String[] args = parser.getArgs() ;
        String path = "" ; 
        boolean bool = false ; 
        for(int i = 1 ; i < args.length ; i++ ){
            if( args[i].contains("\\") ){
                path  = args[i] ; 
                File file = new File(path);
                bool = file.mkdirs();
            }else{
                //create here
                path = System.getProperty("user.dir") ;                 
                path = path + "\\"+args[i] ; 
                File file = new File(path);
                bool = file.mkdir();
            }
            
            if(bool) {
               System.out.println("Directory " + args[i] +" created successfully:D");
            }else {
               System.out.println("Sorry couldnt create specified directory");
            }
        }

    }

    public void rmdir(){
        String[] args = parser.getArgs() ;
        String path = "" ; 
        boolean bool = false ; 
        for(int i = 1 ; i < args.length ; i++ ){
            if( args[i].equals("*")){
                path = System.getProperty("user.dir") ;                 
                File file = new File(path);
                for (File f : file.listFiles()) 
                {
                    if( f.isDirectory()){
                        bool = f.delete();
                        if(bool)
                            System.out.println(f.getName() + " Succcessfully deleted");
                    }
                }
            }else if( args[i].contains("\\") ){
                path  = args[i] ; 
                File file = new File(path);
                if( file.isDirectory()){
                    bool = file.delete();
                    if(bool)
                        System.out.println("Directory " + file.getName() + " Succcessfully deleted");
                }
            }
            if(!bool){
               System.out.println("Sorry couldnt delete specified directory");
            }
        }

    } 
    
    public void touch(){
        String[] args = parser.getArgs() ;
        String path = "" ; 
        boolean bool = false ; 
        for(int i = 1 ; i < args.length ; i++ ){
            if( args[i].contains("\\") ){
                path  = args[i] ; 
                File file = new File(path);
                try{  
                    bool = file.createNewFile();  //creates a new file  
                    
                    if(bool)      // test if successfully created a new file  
                        System.out.println("file created "+file.getCanonicalPath()); //returns the path string  
                    else  
                        System.out.println("File already exist at location: "+file.getCanonicalPath());  
                }catch (IOException e){  
                    e.printStackTrace();    //prints exception if any  
                }
            }else{
                path = System.getProperty("user.dir")+ "\\" +args[i] ;
                File file = new File(path);
                try{  
                    bool = file.createNewFile();  //creates a new file  
                    
                    if(bool)      // test if successfully created a new file  
                        System.out.println("file created "+file.getCanonicalPath()); //returns the path string  
                    else  
                        System.out.println("File already exist at location: "+file.getCanonicalPath());  
                }catch (IOException e){  
                    e.printStackTrace();    //prints exception if any  
                }
            }
            
            
        }

    }
    
    public void ls(){
        String path = "" ; 
        path = System.getProperty("user.dir") ;                 
        File file = new File(path);
        String[] files = file.list();
        System.out.println(parser.getArgs().length);
        if( parser.getArgs().length > 1){
            //revere
            for(int i = files.length-1 ; i>= 0 ; i--)
                System.out.println(files[i]);
        }else{
            for(String f : files )
               System.out.println(f);

        }
    }
    
    public void cp() 
            throws Exception 
    {
        String[] args = parser.getArgs() ;
        File a = new File(args[1]) ; 
        File b = new File(args[2]) ; 
        FileInputStream in = new FileInputStream(a);
        FileOutputStream out = new FileOutputStream(b);
  
        try {
  
            int n;
  
            // read() function to read the
            // byte of data
            while ((n = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(n);
            }
        }
        finally {
            if (in != null) {
  
                // close() function to close the
                // stream
                in.close();
            }
            // close() function to close
            // the stream
            if (out != null) {
                out.close();
            }
        }
        System.out.println("File Copied");
    }
    
    // ...
    //This method will choose the suitable command method to be called
    //public void chooseCommandAction(){...}
    //public static void main(String[] args){...}
        
}

