#/bin/bash

OPTIND=1
output_file=""
input_file=""
command="./vigenere.bin"
key="password"
mode="e"

while getopts "h?dek:o:i:" opt; do
    case "$opt" in
    h|\?)
        cat help
        exit 0
        ;;
    o)  exec 1>$OPTARG
        ;;
    i)  exec <$OPTARG
        ;;
    k)  key=$OPTARG
        ;;
    d)  mode="d"
        ;;
    e)  mode="e"
        ;;
    esac
done

$command $key $mode





