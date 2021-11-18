package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

public class Terminal {
    String currPath = System.getProperty("user.dir");
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


    //Implement each command in a method
    public void pwd(){
        System.out.println(currPath);
    }

    public void echo(){
        String res = "" ;

        String[] args = parser.getArgs() ;

        for (int i = 1; i < parser.getArgs().length; i++) {
            res += args[i] + " ";
        }


        System.out.println(res);
    }

    public void mkdir(){
        String[] args = parser.getArgs() ;
        String path = "" ;
        File file;
        for(int i = 1 ; i < args.length ; i++ ){
            if( args[i].contains("\\") ){
                path  = args[i] ;
                file = new File(path);
            }else{
                //create here
                path = currPath + "\\" + args[i] ;
                file = new File(path);
            }

            if(file.mkdir()) {
                System.out.println("Directory " + args[i] +" created successfully ");
            }else {
                System.out.println("failed to create this directory or it already exist");
            }
        }

    }

    public void rmdir() {
        String[] args = parser.getArgs() ;
        String path = "" ;

        if(args.length>1)
        {
            if( args[1].equals("*")){
                path = currPath ;
                File file = new File(path);
                for (File f : file.listFiles())
                {
                    if( f.isDirectory()){
                        if(f.delete())
                            System.out.println(f.getName() + " Successfully deleted");
                        else
                            System.out.println(f.getName() + " is not empty directory");

                    }
                    else
                        System.out.println(f.getName() + " is not Directory ");
                }
            }
            else if( args[1].contains("\\") ){

                path = args[1];
                File file = new File(path);

                if( file.isDirectory()){
                    if(file.delete())
                        System.out.println(file.getName() + " Successfully deleted");
                    else
                        System.out.println(file.getName() + " is not empty directory");

                }
                else
                    System.out.println(file.getName()+ " is not Directory ");


            }
        }
    }

    public void touch(){
        String[] args = parser.getArgs() ;

        String path  = "";

        if (args.length==2)
            path = currPath + "\\" +args[1];
        else
            path = args[1]+ "\\" +args[2];

        File file = new File(path);
        try{
            if(file.createNewFile())      // test if successfully created a new file
                System.out.println("file created: " + file.getCanonicalPath()); //returns the path string
            else
                System.out.println("File already exist at location: "+file.getCanonicalPath());
        }
        catch (IOException e){
            e.printStackTrace();    //prints exception if any
        }





    }

    public void ls() throws Exception{
        File file = new File(currPath);
        String[] files = file.list();
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
        if( args[1].equals("-r")){
            
            File a = new File(currPath + "\\" + args[2]) ;
            File b = new File(currPath  + "\\" +  args[3]) ;
            try{
                copyDirectory(a, b) ; 
            }catch(IOException ex){
                ex.printStackTrace() ; 
            }
        }else{
            File a = new File(currPath  + "\\" +  args[1]) ;
            File b = new File(currPath  + "\\" +  args[2]) ;
            FileInputStream in = new FileInputStream(a);
            FileOutputStream out = new FileOutputStream(b);

            try {

                int n;
                while ((n = in.read()) != -1) {
                    out.write(n);
                }
            }
            finally {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            }

            System.out.println("File Copied");
        }
    }

    //////////////////////////////////
    
    public static void copyDirectory(File sourceDir, File destDir)
        throws IOException {
        // creates the destination directory if it does not exist
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        // throws exception if the source does not exist
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("sourceDir does not exist");
        }

        // throws exception if the arguments are not directories
        if (sourceDir.isFile() || destDir.isFile()) {
            throw new IllegalArgumentException(
                    "Either sourceDir or destDir is not a directory");
        }

        copyDirectoryImpl(sourceDir, destDir);
}
    
    private static void copyDirectoryImpl(File sourceDir, File destDir)
        throws IOException {
        File[] items = sourceDir.listFiles();
        if (items != null && items.length > 0) {
            for (File anItem : items) {
                if (anItem.isDirectory()) {
                    // create the directory in the destination
                    File newDir = new File(destDir, anItem.getName());
                    System.out.println("CREATED DIR: "
                            + newDir.getAbsolutePath());
                    newDir.mkdir();

                    // copy the directory (recursive call)
                    copyDirectory(anItem, newDir);
                } else {
                    // copy the file
                    File destFile = new File(destDir, anItem.getName());
                    copySingleFile(anItem, destFile);
                }
            }
        }
    }
    
    private static void copySingleFile(File sourceFile, File destFile)
        throws IOException {
        System.out.println("COPY FILE: " + sourceFile.getAbsolutePath()
                + " TO: " + destFile.getAbsolutePath());
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {
            sourceChannel = new FileInputStream(sourceFile).getChannel();
            destChannel = new FileOutputStream(destFile).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        } finally {
            if (sourceChannel != null) {
                sourceChannel.close();
            }
            if (destChannel != null) {
                destChannel.close();
            }
        }
    }
    
    public void cd()
    {
        String[] args = parser.getArgs() ;
        String path = "";
        if(args.length>1)
        {
            if(args[1].contains("\\"))
            {
                path = args[1];
            }

            else if(args[1].equals(".."))
            {
                int index=0;
                for(int i=0;i<currPath.length();i++)
                {
                    if(currPath.charAt(i)=='\\')
                    {
                        index = i;
                    }
                }
                path = currPath.substring(0,index);
            }

            else
            {
                path = currPath+"\\"+args[1];
            }

            System.out.println("path changed from :" +  currPath + " to " + path);
            currPath = path;

        }
        else
        {
            String homePath = System.getProperty("user.home");
            System.out.println("path changed from :" +  currPath + " to " + homePath);
            currPath=homePath;

        }
    }


    public void rm()
    {
        String[] args = parser.getArgs();
        try {
            File f = new File(currPath +"\\"+ args[1]);
            if(f.delete())
            {
                System.out.println(args[1] + " deleted successfully ");
            }
            else
            {
                System.out.println("Failed to delete " + args[1]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


    }

    public void exit()
    {
        System.exit(0);
    }

    public void cat() throws Exception
    {
        try
        {
            String[] args = parser.getArgs();
                if (args.length==2)
                {
                    FileReader fileReader = new FileReader(currPath + args[1]);
                    BufferedReader br = new BufferedReader(fileReader);
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        System.out.println(line);
                    }
                    fileReader.close();
                }
                else
                {
                    File Result = new File(currPath + "Result.txt");
                    PrintWriter pw = new PrintWriter(Result);

                    // BufferedReader object for 1st file
                    BufferedReader br = new BufferedReader(new FileReader(currPath + args[1]));

                    String line = br.readLine();

                    // loop to copy each line of 1st file to result file
                    while (line != null)
                    {
                        pw.println(line);
                        line = br.readLine();
                    }

                    // BufferedReader object for 2nd file
                    br = new BufferedReader(new FileReader(currPath+ args[2]));

                    line = br.readLine();

                    // loop to copy each line of 2nd file to result file
                    while(line != null)
                    {
                        pw.println(line);
                        line = br.readLine();
                    }

                    pw.flush();

                    br = new BufferedReader(new FileReader(currPath + "Result.txt"));
                    line=br.readLine();

                    while (line!= null)
                    {
                        System.out.println(line);
                        line = br.readLine();
                    }

                    // closing resources , deleting result file

                    br.close();
                    pw.close();

                    Result.delete();


                }
        }

        catch (FileNotFoundException ex)
        {
            System.out.println("file is not found.");
        }
        catch (IOException ex)
        {
            System.out.println("i/o error.");
        }
    }

    public String getCurrPath ()
    {
        return currPath;
    }

    public void chooseCommandAction(String commandName ) throws Exception
    {
        switch (commandName) {
            case "echo":
                echo();
                break;
            case "pwd":
                pwd();
                break;
            case "mkdir":
                mkdir();
                break;
            case "rmdir":
                rmdir();
                break;
            case "touch":
                touch();
                break;
            case "ls":
                ls();
                break;
            case "cp":
                cp();
                break;
            case "cat":
                cat();
                break;
            case "exit":
                exit();
                break;
            case "cd":
                cd();
                break;
            case "rm":
                rm();
                break;
            default:
                System.out.println(parser.getCommandName() + " is not recognized as an internal or external command,");
        }
    }
}