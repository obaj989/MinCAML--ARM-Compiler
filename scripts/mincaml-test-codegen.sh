#!/bin/sh
# ANSI color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'  # Yellow text
NC='\033[0m' # No Color

# Define the path to your Java compiler and your Java class
JAVA_COMPILER="java"
MAIN_CLASS="compiler.Main"

# Define the path to your input .ml files and the output directory
VALID_INPUT_DIRECTORY="../tests/gen-code/valid"
INVALID_INPUT_DIRECTORY="../tests/gen-code/invalid"
OUTPUT_DIRECTORY="../ARM"
VALID_RESULT_DIRECTORY="../tests/expected/valid"
INVALID_RESULT_DIRECTORY="../tests/expected/invalid"
AS=arm-none-eabi-as
LD=arm-none-eabi-ld

# Function to process a directory of .ml files
process_directory() {
  local input_directory="$1"
  local result_directory="$2"

  # Compile and run your code for each .ml file in the input directory
  for ml_file in "$input_directory"/*.ml
  do
    echo "Processing $ml_file"

    # Extract the file name without extension
    file_name=$(basename -- "$ml_file")
    file_name_without_extension="${file_name%.*}"
    ASML_FILE="$OUTPUT_DIRECTORY/${file_name_without_extension}.asml"  # Path to the ASML file
    ARM_FILE="$OUTPUT_DIRECTORY/${file_name_without_extension}.arm"    # Path to the ARM executable
    COMP_OUTPUT=$(mktemp)
    
    # Compile your Java code to generate the ASML file
    $JAVA_COMPILER -cp "target/classes:target/dependency/*" $MAIN_CLASS -o "$ml_file" "$OUTPUT_DIRECTORY/$file_name_without_extension.S" > $COMP_OUTPUT
    # Check if compilation was successful
    if [ $? -eq 0 ]; then
      echo "Compilation successful"

      # Run the generated .arm file using qemu-arm and redirect the output to a temporary file
      TEMP_OUTPUT=$(mktemp)
      qemu-arm $ARM_FILE > $TEMP_OUTPUT 

      # Compare the actual output with the expected output
      diff_output=$(diff -q $TEMP_OUTPUT "$result_directory/$file_name_without_extension.expected")

      # Check if the diff command produced no differences
      if [ $? -eq 0 ]; then
        echo "${GREEN}Test passed${NC}"
        rm -f $TEMP_OUTPUT  # Remove the temporary output file
      else
        echo "${RED}Test failed${NC}"
        echo "Differences:"
        echo "$diff_output"
        rm -f $TEMP_OUTPUT  # Remove the temporary output file
     #   exit 1  # Exit with a non-zero status code to indicate test failure
      fi
    else
      echo "${RED}Compilation failed${NC}"
      if [ "$input_directory" = "$INVALID_INPUT_DIRECTORY" ]; then
      echo "Invalid files are failing compilation as expected"      
      else
        diff_output=$(diff -q $COMP_OUTPUT "$result_directory/$file_name_without_extension.expected")
        echo "Differences:"
        echo "$diff_output"
        rm -f $COMP_OUTPUT  # Remove the temporary output file
      fi
    fi
  done
}

# Process valid and invalid directories
echo "${YELLOW}Processing valid files${NC}"
process_directory "$VALID_INPUT_DIRECTORY" "$VALID_RESULT_DIRECTORY"

echo "${YELLOW}Processing invalid files${NC}"
process_directory "$INVALID_INPUT_DIRECTORY" "$INVALID_RESULT_DIRECTORY"

# Clean up and remove .o files
rm -f "$OUTPUT_DIRECTORY"/*.o