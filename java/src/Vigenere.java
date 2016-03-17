
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by postmordum on 6/29/15.
 */
public class Vigenere {
    String shiftString(String key,String text,char mode){
        int kp = 0;
        String encrypt = "";
        for(int i=0;i<text.length();i++){
            if(kp >= key.length()){
                kp = 0;
            }
            char t = text.charAt(i);
            char c = key.charAt(kp++);
            char e = this.shiftChar(c,t,mode);
            encrypt += e;
        }
        return encrypt;
    }
    private int getOffset(char input)
    {
	    int offset = -1;
	    if(input>='A'&&input<='Z'){
		    offset = 'A';
	    }else if(input>='a'&&input<='z'){
		    offset = 'a';
	    }else if(input>='0'&&input<='9'){
		    offset = '0';
	    }
	    return offset;
    }
    private int getModulo(char input){
	    int modulo = -1;
	    if(input>='A'&&input<='Z'){
		    modulo = 26;
	    }else if(input>='a'&&input<='z'){
		    modulo = 26;
	    }else if(input>='0'&&input<='9'){
		    modulo = 10;
	    }
	    return modulo;
    }
    private char shiftChar(char shift, char ltr,char mode){
        int offset = this.getOffset(ltr);
        int modulo = this.getModulo(ltr);
        if(offset > 0){
            int a = ltr - offset;
            int s = shift - this.getOffset(shift);
            if(mode == 'd'){
                s *= -1;
            }
            int r = (a+s)%modulo;
		    if(r<0)
			    r += modulo;
			return (char) (r + offset);
        }else{
            return ltr;
        }
    }
}

