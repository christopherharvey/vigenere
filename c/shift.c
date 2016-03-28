

#ifndef SHIFT_C
#define SHIFT_C
#define EOK 27
char getOffset(char input)
{
	char offset = -1;
	if(input>='A'&&input<='Z')
	{
		offset = 'A';
	}
	else if(input>='a'&&input<='z')
	{
		offset = 'a';
	}
	else if(input>='0'&&input<='9')
	{
		offset = '0';
	}
	return offset;
}
int getModulo(char input){
	char modulo = -1;
	if(input>='A'&&input<='Z')
	{
		modulo = 26;
	}
	else if(input>='a'&&input<='z')
	{
		modulo = 26;
	}
	else if(input>='0'&&input<='9')
	{
		modulo = 10;
	}
	return modulo;
}
char shiftChar(char input,char numericKey)
{
	char ascii = input;
	char offset = getOffset(input);
	int modulo = getModulo(input);
	if(offset>0)
	{
		char alpha = ascii - offset;
		char sum = (alpha+numericKey)%modulo;
		if(sum<0)
			sum += modulo;
		ascii = sum+offset;
	}
	return ascii;
}
char getNumericKeyFromAscii(char asciiKey)
{
	char offset = getOffset(asciiKey);
	return asciiKey - offset;
}
void getNumericKeyArrayFromAsciiKeyString(char *asciiKeyString,char *numericKeyArray,char encryptORdecrypt)
{
	char invert = (encryptORdecrypt=='d')?-1:1;
	while(*asciiKeyString!='\0')
	{
		(*(numericKeyArray++))=invert*getNumericKeyFromAscii(*(asciiKeyString++));
	}
	(*(numericKeyArray))=EOK;
}
void shiftFile(FILE* in,FILE* out, char *numericKeyArray)
{
	char *currentKey = numericKeyArray;
	char input = ' ';
	while((input = fgetc(in)) != EOF){
		if(*currentKey==EOK)
			currentKey = numericKeyArray;
		char output = shiftChar(input,*(currentKey++)); 
        fputc(output,out);
    }
}
#endif


