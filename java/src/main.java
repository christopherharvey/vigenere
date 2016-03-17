
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class main{
    public static void main(String [ ] args)
    {
        boolean encrypt = false;
        boolean decrypt = false;
        char mode = 'e';
        String password = null;
        String input_text = null;
        String input_file_name = null;
        String output_file_name = null;
        for(int i=0;i<args.length;i++){
            String option = args[i];
            switch(option){
                case "-e":
                    encrypt = true;
                    mode = 'e';
                    break;
                case "-d":
                    decrypt = true;
                    mode = 'd';
                    break;
                case "-p":
                    i = i+1;
                    password = args[i];
                    break;
                case "-i":
                    i = i+1;
                    input_file_name = args[i];
                    break;
                case "-t":
                    i = i+1;
                    input_text = args[i];
                    break;
                case "-o":
                    i = i+1;
                    output_file_name = args[i];
                    break;
                case "-h":
                case "--help":
                    System.out.println("Options:");
                    System.out.println("\t-e        : Encrypt input. This flag is set by default.");
                    System.out.println("\t-d        : Decrypt input.");
                    System.out.println("\t-p        : Password. If none is given password is pulled from input.");
                    System.out.println("\t-i        : Input file name. If no input is used text is pulled from standard input.");
                    System.out.println("\t-o        : Output file name. If no output is used text is printed.");
                    System.out.println("\t-h,--help : Output file name.");
                    return;
                case "--test":
                    String alphabet = "";
                    alphabet += "abcdefghijklmnopqrstuvwxyz\nABCDEFGHIJKLMNOPQRSTUVWXYZ\n1234567890\n!@#$%^&*()";
                    main.test0("hi",alphabet);
                    main.test0("frog duck horse potatoe","the quik brown fox jumps over the white fence.");
                    return;
                default:
                    System.out.println("Invalid option '"+option+"'");
                    return;
            }
        }
        if(password == null){
            System.err.println("Please enter a password, then press enter.");
            Scanner scanner = new Scanner(System.in);
            password = scanner.next();
        }
        if(input_file_name != null){
            if(Files.notExists(Paths.get(input_file_name))){
                System.out.println("File "+input_file_name+" does not exist.");
                return;
            }else{
                try{
                    List<String> lines = Files.readAllLines(Paths.get(input_file_name),StandardCharsets.UTF_8);
	                Iterator<String> iterator = lines.iterator();
	                input_text = "";
                    while(iterator.hasNext()){
                        input_text += iterator.next();
                        if(iterator.hasNext()){
                            input_text += "\n";
                        }
                    }
                }catch(IOException exception){
                    //TODO handle IO exceptions
                }
            }
        }
        if(input_text == null){
            System.err.println("Please enter a text, then press enter. Enter an empty line to quit.");
            Scanner scanner = new Scanner(new BufferedInputStream(System.in));
            input_text = "";
            String tmp;
            while((tmp = scanner.nextLine()) != null && tmp.length()!=0){
                input_text += tmp+'\n';
            }
        }
        String output = "";
        Vigenere vigenere = new Vigenere();
        output = vigenere.shiftString(password,input_text,mode);
        if(output_file_name == null){
            System.out.println(output);
        }else{
            if(Files.notExists(Paths.get(output_file_name))){
                try{
                    Files.createFile(Paths.get(output_file_name));
                    Files.write(Paths.get(output_file_name),output.getBytes());
                }catch(IOException exception){
                    //TODO handle IO exceptions
                }
            }
        }
    }
    public static void test0(String key,String text){
        Vigenere vigenere = new Vigenere();
        String e = vigenere.shiftString(key,text,'e');
        String d = vigenere.shiftString(key,e,'d');
        System.out.println("TEST ENCRYPT:"+e);
        System.out.println((text.equals(d)?"PASS::"+d:"FAIL"+":SHOULD BE-"+text+":IS-"+d));
    }
}
