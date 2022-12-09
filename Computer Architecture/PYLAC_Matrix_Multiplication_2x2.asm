######################################################## 
#  REGISTERS RELATION 								   #
#  $t0 - MULTI-COUNTER/ COUNTER FOR LOOP K			   #
#  $t1 - ADDRESS OF DIM/ADDRESS OF MATBT			   #
#  $t2 - ADDRESS OF MATA 							   #
#  $t3 - SCANNED NUMBER/ADDRESS OF SPACE			   #
#  $t4 - DIMENSION 									   #
#  $t5 - A[i]/DOUBLED DIMENSION 					   #
#  $t6 - BT[i]/ADDRESS OF MESSAGE 2	 			       #
#  $t7 - MULT = A[i] * B[i]							   #
#  $s0 - ADDRESS OF MESG1/ADDRESS OF TWOPTS/COUNTER FOR#
#        LOOP J; COMPARISON PURPOSES				   #
#  $s1 - TEMPORAL COUNTER							   #
#  $s2 - COUNTER FOR LOOPI 							   #
#  $s3 - END OF LINE 								   #
#  $s4 - (C[i]) SUM = SUM + MULT					   #
########################################################

.data
prompt:  .asciiz "The Dimension of The Matrices Will Be: 2 x 2.\nNow, You Must Define The Numbers for The Matrix A (4 numbers), Then Immediately The Numbers for The Matrix B Will Be Asked: \n"
twopts: .asciiz ": "
newLine: .asciiz "\n"
space  : .asciiz "  "
matrixA: .asciiz "Contents of Matrix A"
matrixB: .asciiz "Contents of Matrix B"
matrixC: .asciiz "Contents of Matrix C"
		.align 4
matrixElements: .space 32

MATA :  .byte 00
		.byte 00
		.byte 00
		.byte 00

MATB:   .byte 00
		.byte 00
		.byte 00
		.byte 00

MATC:   .byte 00
		.byte 00
		.byte 00
		.byte 00

MATBT:  .byte 00
		.byte 00
		.byte 00
		.byte 00

DIM: 	.byte 4

.text
main:	
	xor $t0, $t0, $t0 # Counter Initialization
	la  $t1, DIM 	  # Load the address of DIM (Dimension) into $t1
	la  $t2, MATA     # Load the address of MATA (Matrix A) into $t2
	la  $s0, prompt   # Load the address of Prompt Message into $s0
	la  $s3, newLine  # Load the address of the End of Line into $s3
	lbu $t4, 0($t1)   # Load the value of DIM
	add $t5, $t4, $t4 # Temporal Variable for the condition in "ragain"
	addi $t9, $0, 0
	
	li  $v0, 4 		  # Print Prompt Message
	la  $a0, 0($s0)
	syscall

	la  $s0, twopts   # Load the address of 2Points into $s0

read:	
	li  $v0, 1 		  # Print the index of the matrix
	move $a0, $t0
	syscall

	li  $v0, 4 		  # Print 2Points
	la  $a0, 0($s0)
	syscall

	li  $v0, 5		  # Read the Integer
	syscall
	add $t3, $0, $v0  # Store the number given by the user into $t3
	
	sw $v0, matrixElements($t9)
	addi $t9, $t9, 4

	sb $t3, 0($t2)	  # Store the number in the Matrix 
	addi $t2, $t2, 1 	  # Increase by 1 the Index, the Counter and the Temporal Counter ($s1)
	addi $t0, $t0, 1 
	addi $s1, $s1, 1 	  
	bne $t4, $t0, read	# If the counter it is not equal to the dimension, keep reading

ragain: 
	li $v0, 4 		   # Call service in order to print an "End of Line"
	la $a0, 0($s3)
	syscall

	xor $t0, $t0, $t0  # Reinitialize Counter 
	bne $s1, $t5, read # Read Again; Now for Matrix B

intlz1: 
	la  $t2, MATA     # Reload the address of MATA (Matrix A) into $t2
	la  $t3, space 	  # Load the address of Spaces into $t3
	xor $s1, $s1, $s1 # Reinitialize Temporal Counter
	la  $t1, MATBT	  # Load the address of MATBT into $t1

trnpse: 
	lbu $t6, 4($t2)   # Load B[i] 
	sb  $t6, 0($t1)   # Store B[i] into it's transpose (BT[i])
	addi $t2, $t2, 2  # Offset for the Transpose
	addi $t0, $t0, 2  # Increase counter in 2
	addi $t1, $t1, 1 	  # Increase by 1 the index of BT[i]
	bne $t0, $t4, trnpse  # If the 2 numbers of a line are printed, go to the next one
	addi $s1, $s1, 1 	  # Next Line
	la  $t2, MATA     # Reload the address of MATA into $t2
	xor $t0, $t0, $t0 # Reinitialize Counter 
	add $t2, $t2, $s1 # Increase the offset for $t2
	bne $s1, 2, trnpse # If the 2 lines are not printed, repeat the process
		

			
#>>>>>>>>>>>>>>>>>>>>Printing Elements of Matrix A<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
printMatrixA:
	addi $t0, $zero, 0
	li $v0, 4 		  # Print Matrix A Message 
	la $a0,  matrixA
	syscall

matrixANewLine:
    li $v0, 4 		  # Call service in order to print an "End of Line"
    la $a0, 0($s3)
    syscall
    
    addi $t9, $zero, 0

	   #############################################################

printMatrixAElements: 

	beq $t0, 16, printMatrixB
	lw $t8, matrixElements($t0)
	
	addi $t0, $t0, 4
	
	li $v0, 1
	move $a0, $t8
	syscall
	
	li $v0, 4 		  # Print the spaces between numbers 
	la $a0, space
	syscall
	
	addi $t9, $t9, 1
	beq $t9, 2, matrixANewLine # If all the numbers of the Matrix A haven't been printed, do all the same process
	
	j printMatrixAElements
#>>>>>>>>>>>>>>>>>>>>Done Printing Elements of Matrix A<<<<<<<<<<<<<<<<<<<<<<<<<<<<


#>>>>>>>>>>>>>>>>>>>>Printing Elements of Matrix B<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
printMatrixB:
	li $v0, 4 		  # Call service in order to print an "End of Line"
    la $a0, 0($s3)
    syscall
    
	addi $t0, $zero, 16
	li $v0, 4 		  # Print Matrix B Message 
	la $a0,  matrixB
	syscall

matrixBNewLine: 
    li $v0, 4 		  # Call service in order to print an "End of Line"
    la $a0, 0($s3)
    syscall
    
    addi $t9, $zero, 0

	   #############################################################

printMatrixBElements: 

	beq $t0, 32, intlz2
	lw $t8, matrixElements($t0)
	
	addi $t0, $t0, 4
	
	li $v0, 1
	move $a0, $t8
	syscall
	
	li $v0, 4 		  # Print the spaces between numbers 
	la $a0, space
	syscall
	
	addi $t9, $t9, 1
	beq $t9, 2, matrixBNewLine # If all the numbers of the Matrix B haven't been printed, do all the same process
	
	j printMatrixBElements
#>>>>>>>>>>>>>>>>>>>>Done Printing Elements of Matrix B<<<<<<<<<<<<<<<<<<<<<<<<<<<<


intlz2:	
	li $v0, 4 		  # Call service in order to print an "End of Line" twice
    la $a0, 0($s3)
    syscall
    
	la  $t6, matrixC	  # Load the address of Message (Matrix C) into $t6
	li  $s2, -2		  # MATA Offset Counter; Counter for Loop i
	xor $s1, $s1, $s1 # Reinitialize Temporal Counter

	li $v0, 4 		  # Print Message 2 
	la $a0, 0($t6)
	syscall

loopi:  
	xor $s0, $s0, $s0 # Initialize counter for loop j
	la  $t1, MATBT    # Reload the address of MATBT into $t1
    addi $s2, $s2, 2 # Increase in 2 the counter for loop i

    li $v0, 4 		  # Call service in order to print an "End of Line"
    la $a0, 0($s3)
    syscall

	   #############################################################

loopj:  
	beq $s0, $t4, loopi # If $s0 it's equal to $t4, go to loop i
    la  $t2, MATA     # Reload the address of MATA into $t2
    add $t2, $t2, $s2 # Increase the index of A[i] depending the current state of the process
    xor $s4, $s4, $s4 # Reinitialize Sum for C[i] 
    xor $t0, $t0, $t0 # Reinitialize Counter for loop k

	   #############################################################

loopk: 
	lbu $t5, 0($t2)    # Load A[i]
	lbu $t6, 0($t1)    # Load BT[i]
	mul $t7, $t5, $t6  # $t7 = A[i] * B[i]
	add $s4, $s4, $t7  # sum = sum + $t7

	addi $t2, $t2, 1   # Increase by 1 the indices of A[i] & BT[i]; 
	addi $t1, $t1, 1		  
	addi $t0, $t0, 1   # Increase by 1 the counter for loop k & the counter for loop j
	addi $s0, $s0, 1
	bne $t0, 2, loopk  # If $t0 it's not equals to 2, go to loop k

	addi $s1, $s1, 1   # Increase by 1 the counter of the index of MATC
	la  $t2, MATA      # Reload the address of MATA into $t2
	add $t2, $t2, $s1  # Increase the index of C[i]
	sb $s4, 17($t2)	  # Store the value of $s4 into MATC

	li $v0, 1 		  # Print number of C[i]
	add $a0, $0, $s4
	syscall

	li $v0, 4 		  # Print the spaces between numbers 
	la $a0, 0($t3)
	syscall

    bne $s1, $t4, loopj # If all the numbers of the MATC haven't been printed, do all the same process

	   #############################################################
	li $v0, 4 		  # Call service in order to print an "End of Line"
	la $a0, 0($s3)
	syscall

exit:   
	li $v0, 10 		  # Exit Program
	syscall
