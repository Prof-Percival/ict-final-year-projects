package matrixmanipulation;

import java.util.Scanner;

public class MatrixManipulation {

    private static int[][] matrixA;
    private static int[][] matrixB;
    private static int[][] matrixC;
    static Scanner input = new Scanner(System.in);
    private static int size;
    
    //A:12.0, 2.0
    //  9.0, 6.0
    //B:7.0, 8.0
    //  4.0, 16.0
    
    public static void main(String[] args)
    {
        System.out.print("Input Size of Matrix (i.e 4 -> 4x4) >> ");
        size = input.nextInt();
        
        System.out.println();
        
        for (int p = 0; p < 2; p++)
        {
            addContents(p);
            System.out.println();
        }
        
        //Find Matrix Product
        matrixProduct();
        
        System.out.println();
        
        //Display Matrix Contents
        //Matrix A
        displayMatrixContents(0, matrixA);
        System.out.println();
        
        //Matrix B
        displayMatrixContents(1, matrixB);
        System.out.println();
        
        //Matrix C
        displayMatrixContents(2, matrixC);
        System.out.println();
        
    }
    
    private static void displayMatrixContents(int num, int[][] matrix)
    {
        String type = "";
        if (num == 0)
            type = "Matrix A";
        else
            if (num == 1)
                type = "Matrix B";
            else
                if (num == 2)
                    type = "Matrix C";
        
        System.out.printf("Contents of %s: %n", type);
        for (int p = 0; p < matrix.length; p++)
        {
            for (int c = 0; c < matrix[0].length; c++)
            {
                System.out.print(matrix[p][c] + " ");
            }
            
            System.out.println();
        }
    }
    
    private static void matrixProduct()
    {
        matrixC = new int[matrixA.length][matrixA[0].length];
        
        for (int p = 0; p < matrixA.length; p++)
        {
            for (int c = 0; c < matrixA[0].length; c++)
            {
                int sum = 0;
                
                for (int t = 0; t < matrixA.length; t++)
                {
                    sum += matrixA[p][t] * matrixB[t][c];
                }
                
                matrixC[p][c] = sum;
            }
        }
    }
    
    private static void addContents(int num)
    {
        String type = "";
        if (num == 0)
            type = "Matrix A";
        
        if (num == 1)
            type = "Matrix B";
        
        int[][] contents = new int[size][size];
        
        for (int p = 0; p < size; p++)
        {
            for (int c = 0; c < size; c++)
            {
                System.out.printf("Input Contents of %s Row: %d Column: %d >> ", type, p + 1, c + 1);
                contents[p][c] = input.nextInt();
            }
        }
        
        if (type.equals("Matrix A"))
        {
            matrixA = contents;
        }
        
        if (type.equals("Matrix B"))
        {
            matrixB = contents;
        }
        
    }
}