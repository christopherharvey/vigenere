#include <stdio.h>
#include <string.h>

#include "shift.c"

int main(int argc,char* argv[],char* envp[])
{
	char *asciiKeyString;
	char mode;
	if(argc<2)
	{
		fprintf(stderr,"no key");
		return 1;
	}
	else
	{
		asciiKeyString = argv[1];
	}
	if(argc<3)
	{//e means encrypt
		mode = 'e';
	}
	else
	{//d means decrypt
		mode = *argv[2];
	}
	if(mode!='e'&&mode!='d')
	{
		mode = 'e';
	}
	char numericKeyArray[2048];
	getNumericKeyArrayFromAsciiKeyString(asciiKeyString,numericKeyArray,mode);
	if(mode=='d')
	{
		fprintf(stderr,"decrypting with key %s: \n",asciiKeyString);
	}
	else
	{
		fprintf(stderr,"encrypting with key %s: \n",asciiKeyString);
	}
	shiftFile(stdin,stdout,numericKeyArray);
	return 0;
}







