    .section .data
    .section .text
    .globl _start

_start:
    // Initialize variables
    @ Set up the stack frame
    PUSH {FP, LR}   @ Push the previous FP and LR onto the stack
    MOV FP, SP      @ Set FP to point to the current stack top (start of the stack frame)

    @ Allocate space for local variables
    SUB SP, SP, #8  @ Adjust SP to create space for a 4-byte integer

    @ Store a value in the local variable
    MOV R0, #10     @ Load the value 10 into R0
    STR R0, [FP, #-4]  @ Store R0 (10) at the location pointed to by FP-4 (local variable)

    @ Store a value in the local variable
    MOV R0, #20     @ Load the value 20 into R0
    STR R0, [FP, #-8]  @ Store R0 (20) at the location pointed to by FP-8 (local variable)

    // Call the add function
    BL add

    // Save the result in sum
    MOV R2, R0         // sum = result of add(x, y)

    // Call the _min_caml_print_int function
    BL _min_caml_print_int
    
    // Exit the program
    MOV R7, #1         // syscall: exit
    SWI 0              // make syscall

// Function to add two numbers (x and y)
add:
    LDR R1, [FP, #-4]
    LDR R2, [FP, #-8]
    ADD R0, R1, R2     // result = x + y
    BX LR              // return

