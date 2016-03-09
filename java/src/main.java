
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class main{
    static boolean verbose = false;
    public static void main(String [ ] args)
    {
        boolean encrypt = false;
        boolean decrypt = false;
        String password = null;
        String input_text = null;
        String input_file_name = null;
        String output_file_name = null;
        for(int i=0;i<args.length;i++){
            String option = args[i];
            switch(option){
                case "-e":
                    encrypt = true;
                    break;
                case "-d":
                    decrypt = true;
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
                    System.out.println("\t-p        : Password. If none is given password is pulled from input.");
                    System.out.println("\t-i        : Input file name. If no input is used text is pulled from input.");
                    System.out.println("\t-t        : Input text as a string. If both input file name and input text are used, input file takes precedence If no input is used text is pulled from input.");
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
        while(!encrypt && !decrypt){
            System.out.println("Please choose encrypt(e) or decrypt(d).");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.next();
            if(response.equals("e")||response.equals("E")){
                encrypt = true;
            }else if(response.equals("d")||response.equals("D")){
                decrypt = true;
            }
        }
        if(password == null){
            System.out.println("Please enter a password, then press enter.");
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
            System.out.println("Please enter a text, then press enter. For multi line text please use an input file with the -i option.");
            Scanner scanner = new Scanner(System.in);
            input_text = scanner.next();
        }
        String output = "";
        if(encrypt){
            Vigenere vigenere = new Vigenere();
            output = vigenere.encrypt(password,input_text);
        }else if(decrypt){
            Vigenere vigenere = new Vigenere();
            output = vigenere.decrypt(password,input_text);
        }
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
        String e = vigenere.encrypt(key,text);
        if(verbose)
            System.out.println(e);
        String d = vigenere.decrypt(key,e);
        System.out.println((text.equals(d)?"PASS::"+d:"FAIL"+":SHOULD BE-"+text+":IS-"+d));
    }
}
