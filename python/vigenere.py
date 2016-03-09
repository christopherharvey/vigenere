


def shiftForward(shift,letter):
    shift_num = ord(shift)
    letter_num = ord(letter)
    shifted_num = letter_num
    if letter_num>31 and letter_num<127:
        shifted_num = (letter_num + shift_num)
        while shifted_num > 126:
            gap = shifted_num - 126
            shifted_num = gap + 31
    return chr(shifted_num)
    
def shiftBackward(shift,letter):
    shift_num = ord(shift)  
    letter_num = ord(letter)
    shifted_num = letter_num
    if letter_num>31 and letter_num<127:
        shifted_num = (letter_num - shift_num)
        while shifted_num < 32:
            gap = 32 - shifted_num
            shifted_num = 127 - gap
    return chr(shifted_num)
    
def vigenere(key,text,decrypt=None):
    if decrypt == None:
        decrypt = False
    output = "";
    key_len = len(key)
    for i,t in enumerate(text):
        kp = i%key_len;
        c = key[kp];
        if decrypt :
            o = shiftBackward(c,t)
        else:
            o = shiftForward(c,t)
        output += o;
    return output;

def __test(p,t):
    e = vigenere(p,t,False)
    d = vigenere(p,e,True)
    print("password:"+p)
    print("text:"+t)
    print("encrypted:"+e)
    s = (d==t)
    print("--PASS--" if s else "--FAIL--"+d)
    return s

def __test0():
    password = "abcd"
    text = "abcdefghijklmnopqrstuvwxyz"
    return __test(password,text)
def __test1():
    password = "1243"
    text = "1234567890\nABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
    return __test(password,text)
    
if __name__ == "__main__":
    import argparse
    import sys
        
    parser = argparse.ArgumentParser(description='Encrypt and decrypt using viginere cypher.')
    parser.add_argument('-d', dest='decrypt', default=False, action='store_true',
        help="Use this flag to decrypt text, will encrypt by defualt.")
    parser.add_argument('-e', dest='encrypt', default=True, action='store_true',
        help="The encrypt flag is set by default.")
    parser.add_argument('-t', dest='text', default="",
        help="Use this option to input text.")
    parser.add_argument('-i', dest='input_file_name', default=None,
        help="Use this option to input text from a file, if both t and i options are used, the text input is proritized.")
    parser.add_argument('-o', dest='output_file_name', default=None,
        help="Use this option to output text to a file, if not used output is sent to standard out.")
    parser.add_argument('-p', dest='password', default=None,
        help="Password used to shift text. If none given, the user will be prompted.")
 
    args = parser.parse_args()
    text = args.text;
    if text == "":
        if args.input_file_name == None:
            # prompt for text
            sys.stdout.write("Please input line of text to encrypt:")
            text = sys.stdin.readline().strip('\n')
        else:
            # get text from file
            input_file = open(args.input_file_name,"r")
            text = input_file.read()
    
    output_file = sys.stdout
    if args.output_file_name != None:
        output_file = open(args.output_file_name,"w")
       
    if args.password == None:
        sys.stdout.write("Please enter password:")
        password = sys.stdin.readline().strip('\n')
    else:
        password = args.password

    output = vigenere(password,text,args.decrypt)

    output_file.write(output)
    if args.output_file_name == None:
        print("")
