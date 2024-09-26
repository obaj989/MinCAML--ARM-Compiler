# Compiler Project

## Overview

This project is a Java-based compiler designed to process source code files through a command-line interface (CLI). It offers a range of compilation options, allowing for flexible and efficient code processing.

## Prerequisites

- Java Development Kit (JDK), preferably version 11 or higher.
- Maven for building and managing the project.
- Git for cloning the repository.
- Gnu Arm Toolchain
- qemu-arm

### NOTE: The compiler works fully only on Linux. On Mac and Windows it has limited functionalities.

## Getting Started


### Cloning the Repository

1. Clone the repository to your local machine using the following command:

   \shell
   git clone https://gricad-gitlab.univ-grenoble-alpes.fr/infinite-loopers/compiler-project.git
   \

## Installations

This section describes the required features that you need to download on your workspace.

### Java

#### For All Operating Systems:

1. *Check Java*:
    - Open the terminal (Command Prompt on Windows).
    - Run java -version.

2. *Download and Set Path*:
    - Visit the Oracle JDK website.
    - Download the installer for your operating system (e.g., .dmg for macOS, .exe for Windows).
    - Follow the installation instructions.
    - Set PATH and JAVA_HOME variables if needed.

### Apache Maven

Apache Maven is a build automation and project management tool, necessary for using this compiler.

1. *Check Maven*:
    - Open the terminal or command prompt.
    - Run mvn -version.

2. *Download Maven*:
    - Visit [Apache Maven website](https://maven.apache.org).
    - Download the latest version suitable for your OS.
    - Follow the installation instructions provided on the website.

### Gnu Arm Toolchain

Gnu Arm Toolchain is required for compiling from .s to .arm. Install using the following command on Linux:

bash
sudo apt install gcc-arm-none-eabi
## qemu-arm

qemu-arm is essential for running the compiled ARM code and is crucial for complete testing.

- Install it on Linux using the command:
  bash
  apt-get install qemu-user-static

  *Note:* This is applicable only for Linux environments.

## Checking the Installation

When the installation is complete, it's important to verify that the compiler is functioning correctly.

1. *Build the Project:*
   Set up your project and then execute the following command to build it. This should return a success message. Cases of failure will be discussed in a later section.
   
   ``mvn clean package``

2. *Help Command:*
   To understand all the functionalities of the compiler, run the following command. The -help option will provide helper text to guide you through all the functions that the compiler can accomplish.
   
   ``java -cp "target/classes:target/dependency/*" compiler.Main -help``
        
3. *Options Available:*

```

  -o <input file> <output file>      : Compiles the input file and generates an output file.
  -help                              : Displays this help message.
  -v                                 : Displays version information.
  -t <file>                          : Performs type checking on the specified file.
  -p <file>                          : Parses the specified file only, without further processing.
  -asml <input file> <output file>   : Generates ASML code for the input file and outputs to the specified file.
  -b <output file>                   : Executes backend analysis and generates output to the specified file.
  -test-all                          : Runs all the test cases automatically.
  -test-backend                      : Runs backend tests.
  -test-typeChecking                 : Runs type checking tests.
  -test-frontend                     : Runs frontend tests.</pre>
  
  ```

## INFINITE LOOPERS