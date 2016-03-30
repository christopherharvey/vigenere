
def getOffset(charNum):
    offset = -1
    if charNum>=ord('A') and charNum<=ord('Z'):
        offset = ord('A')
    elif charNum>=ord('a') and charNum<=ord('z'):
        offset = ord('a')
    elif charNum>=ord('0') and charNum<=ord('9'):
        offset = ord('0')

    return offset
    
def getModulo(charNum):
    modulo = -1
    if charNum>=ord('A') and charNum<=ord('Z'):
        modulo = 26
    elif charNum>=ord('a') and charNum<=ord('z'):
        modulo = 26
    elif charNum>=ord('0') and charNum<=ord('9'):
        modulo = 10

    return modulo
    
def shiftChar(numericKey,ascii):
    shifted = ascii;
    offset = getOffset(ascii)
    modulo = getModulo(ascii)
    if offset>0:
        alpha = ascii - offset
        s = (alpha+numericKey)%modulo
        if s<0:
            s += modulo
        shifted = s+offset

    return shifted
    
def vigenere(key,text,decrypt=None):
    if decrypt == None:
        decrypt = False
    output = "";
    key_len = len(key)
    for i,t in enumerate(text):
        kp = i%key_len;
        c = ord(key[kp]);
        if decrypt :
            c = c*-1
        o = shiftChar(c,ord(t))
        output += chr(o);
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
    import time
        
    parser = argparse.ArgumentParser(description='Encrypt and decrypt using viginere cypher.')
    parser.add_argument('-d', dest='decrypt', default=False, action='store_true',
        help="Use this flag to decrypt text, will encrypt by defualt.")
    parser.add_argument('-e', dest='encrypt', default=True, action='store_true',
        help="The encrypt flag is set by default.")
    parser.add_argument('-i', dest='input_file_name', default=None,
        help="Use this option to input text from a file.")
    parser.add_argument('-o', dest='output_file_name', default=None,
        help="Use this option to output text to a file, if not used output is sent to standard out.")
    parser.add_argument('-p', dest='password', default=None,
        help="Password used to shift text. If none given, the user will be prompted.")   
    parser.add_argument('--test', dest='test', default=False, action='store_true',
        help=argparse.SUPPRESS)
 
    args = parser.parse_args()
    
    if args.test:
        __test0()
        __test1()
    else:
        if args.password == None:
            sys.stderr.write("Please enter password:")
            sys.stderr.flush()
            password = sys.stdin.readline().strip('\n')
        else:
            password = args.password
            
        if args.input_file_name == None:
            # prompt for text
            sys.stderr.write("Please input text to encrypt. Use end of file to stop(CTRL d):\n")
            sys.stderr.flush()
            text = sys.stdin.read()
        else:
            # get text from file
            input_file = open(args.input_file_name,"r")
            text = input_file.read()
        
        output_file = sys.stdout
        if args.output_file_name != None:
            output_file = open(args.output_file_name,"w")

        output = vigenere(password,text,args.decrypt)

        output_file.write(output)
        
        if args.output_file_name == None:
            print("")
