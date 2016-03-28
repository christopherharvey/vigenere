#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "shift.c"

int main(int argc,char* argv[],char* envp[])
{
    int opt;
    extern char *optarg;
    
    char *inputFileName;
    char *outputFileName;
    FILE *inputFile = stdin;
    FILE *outputFile = stdout;
	char *asciiKeyString = NULL;
    char mode = 'e';
    while((opt = getopt(argc, argv, ":edp:i:o:")) != -1){
        switch(opt){
            case 'p':
                asciiKeyString = optarg;
                break;
            case 'i':
                inputFileName = optarg;
                inputFile = fopen(inputFileName,"r");
                if(inputFile == NULL){
                    printf("File %s does not exist.",optarg);
                    return 1;
                }
                break;
            case 'o':
                outputFileName = optarg;
                outputFile = fopen(outputFileName,"w");
                break;
            case 'e':
                mode = 'e';
                break;
            case 'd':
                mode = 'd';
                break;
            default:
                fprintf(stderr,"Usage %s ",argv[0]);
                return 2;
        }
    }
	if(asciiKeyString == NULL){
		fprintf(stderr,"No password provided. Please use the -p option to designate a password.\n");
		return 3;
	}else{
		asciiKeyString = argv[1];
	}
	char numericKeyArray[2048];
	getNumericKeyArrayFromAsciiKeyString(asciiKeyString,numericKeyArray,mode);
	fprintf(stderr,"%s ",(mode == 'e'?"encrypting":"decrypting"));
	fprintf(stderr,"%s ",(inputFile == stdin?
                "stdin, use end of file to exit (CTRL +d)":inputFileName));
	fprintf(stderr,"to %s\n",(outputFile == stdout?"stdout":outputFileName));
	
    shiftFile(inputFile,outputFile,numericKeyArray);
	return 0;
}
