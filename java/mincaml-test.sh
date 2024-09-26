#!/bin/bash

CLASSPATH="target/classes:target/dependency/*"
TEST_SUITE="TypeTesterTest"


# ANSI color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
Magenta='\u001B[35m'
Yellow='\u001B[33m'
NC='\033[0m' # No Color

# Function to run the test for a file
run_test() {
    file=$1
    echo -n "Testing type checking on $file... "
    java -cp "$CLASSPATH" compiler.Main -t "$file"
    exit_status=$?

    if [ $exit_status -eq 0 ]; then
        echo -e "${GREEN}[Passed]${NC}"
    else
        echo -e "${RED}[Failed]${NC}"
    fi
}

# Function to process files in a directory
process_files() {
    directory=$1
    is_valid=$2

    for file in "$directory"/*
    do
        if [ -f "$file" ]; then
            run_test "$file"
        fi
    done
}


# Run tests for invalid cases
echo -e "${Magenta}<<<<<<<<<<<<<<  || Invalid files ||  >>>>>>>>>>>>>>>>>>>>>>${NC}"
process_files "../tests/typechecking/invalid" 0

# Run tests for valid cases
echo -e "${Magenta}<<<<<<<<<<<<<<  || Valid files ||  >>>>>>>>>>>>>>>>>>>>>>${NC}"
process_files "../tests/typechecking/valid" 1
