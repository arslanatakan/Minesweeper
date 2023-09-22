import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    static int rowNumber;
    static int columnNumber;
    static HashSet<String> uncoveredMineCoordinates = new HashSet<>();

    public Minesweeper() {

    }

    public Minesweeper(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public static void run(){
        System.out.println("-------------------------------\nWelcome to the Minesweeper game!");
        Scanner input = new Scanner(System.in);
        if (rowNumber == 0 && columnNumber == 0){
            System.out.println("Set limits for the number of rows and columns!");
            System.out.print("Row =>> ");
            rowNumber = input.nextInt();
            System.out.print("Column =>> ");
            columnNumber = input.nextInt();
        }

        String matrixOfUser[][] = createStandardMatrix(rowNumber,columnNumber);
        String standardMatrix[][] = createStandardMatrix(rowNumber,columnNumber);
        int minedMatrix[][] = createMine(rowNumber,columnNumber);
        standardMatrix = updateStandardMatrixWithMines(standardMatrix,minedMatrix);
        //printMatrix(standardMatrix);   //-> It shows the mine positions.
        System.out.println("///////////////////////////////////////");
        printMatrix(matrixOfUser);

        System.out.println("Please enter a coordinate to check!");
        boolean isGameOver = false;
        boolean isWon = false;

        do{
            isGameOver = checkMine(standardMatrix,matrixOfUser);
            if(uncoveredMineCoordinates.size()==(rowNumber*columnNumber)-((rowNumber*columnNumber)/4)){
                isWon = true;
            }
        }while(!isGameOver && !isWon);

        if(isGameOver){
            System.out.println("Game Over!");
        }
        if(isWon){
            System.out.println("Congratulations, You Won!");
        }

    }

    public static boolean checkMine(String standardMatrix[][],String matrixOfUser[][]){
        Scanner input = new Scanner(System.in);
        int guessedX;
        int guessedY;
        int lowerBoundX;
        int lowerBoundY;
        int mineCounter = 0;
        boolean validInput = false;
        boolean isGameOver = false;
        boolean isMineCoordinateUnique = false;
        String mineCoordinate;
        do{
            System.out.print("X=> ");
            guessedX = input.nextInt();

            System.out.print("Y=> ");
            guessedY = input.nextInt();
            mineCoordinate = guessedX + "," + guessedY;


            if(guessedX < 0 || guessedX >= rowNumber || guessedY < 0 || guessedY >= columnNumber){
                System.out.println("Invalid coordinates. Please enter again!");
            }
            else{
                validInput = true;
            }
            if(validInput && !uncoveredMineCoordinates.contains(mineCoordinate)){
                uncoveredMineCoordinates.add(mineCoordinate);
                isMineCoordinateUnique = true;
            }
        }while(!validInput || !isMineCoordinateUnique);

        int upperBoundX = guessedX + 1;
        int upperBoundY = guessedY + 1;

        if(upperBoundX == rowNumber){
            upperBoundX = rowNumber - 1;
        }
        if(upperBoundY == columnNumber){
            upperBoundY = columnNumber - 1;
        }

        for(lowerBoundX = guessedX - 1; lowerBoundX <= upperBoundX; lowerBoundX++){
            if(lowerBoundX < 0){
                continue;
            }
            for(lowerBoundY = guessedY - 1; lowerBoundY <= upperBoundY; lowerBoundY++){
                if(lowerBoundY < 0){
                    continue;
                }
                if(standardMatrix[guessedX][guessedY].equals("*")){
                    isGameOver = true;
                }
                if(standardMatrix[lowerBoundX][lowerBoundY].equals("*")){
                    mineCounter++;
                }
            }
        }

        if(isGameOver){
            matrixOfUser[guessedX][guessedY] = "*";
        }
        else{
            matrixOfUser[guessedX][guessedY]= String.valueOf(mineCounter);
        }
        printMatrix(matrixOfUser);
        return isGameOver;
        //printMatrix(standardMatrix); //-> It prints the matrix with mine locations!
    }

    public static void printMatrix(String standardMatrix[][]){
        for(int i = 0; i < rowNumber; i++){
            for(int j = 0; j < columnNumber; j++){
                System.out.print(standardMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String[][] updateStandardMatrixWithMines(String standardMatrix[][], int minedMatrix[][]){
        for(int i = 0; i < rowNumber; i++){
            for(int j = 0; j < columnNumber; j++){
                if(minedMatrix[i][j] == 9){
                    standardMatrix[i][j] = "*";
                }
            }
        }
        return standardMatrix;
    }

    public static String[][] createStandardMatrix(int rowNumber, int columnNumber){
        String[][] matrix = new String[rowNumber][columnNumber];
        for(int i = 0; i < rowNumber; i++){
            for(int j = 0; j < columnNumber; j++){
                matrix[i][j] = "-";
            }
        }
        return matrix;
    }

    public static int[][] createMine(int rowNumber, int columnNumber){
        int numberOfMines = (rowNumber * columnNumber) / 4;
        int minedMatrix[][] = new int[rowNumber][columnNumber];

        Random rand = new Random();
        for(int i = 0; i < numberOfMines; i++){
            int randomX = rand.nextInt(rowNumber);
            int randomY = rand.nextInt(columnNumber);
            if(minedMatrix[randomX][randomY]==9){
                i--;
                continue;
            }
            else{
                minedMatrix[randomX][randomY] = 9; //number 9 represents mine.
            }
        }
        return minedMatrix;
    }
}

