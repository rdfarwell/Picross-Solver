import java.util.Arrays;
import java.util.Scanner;

public class PicrossSolver {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        String[] inputData;
        int[][] rows = new int[0][0], columns = new int[0][0];
        String[][] solved;
        int width = 0, height = 0, highestColumn = 0, widestRow = 0, counter = 0;
        boolean error = true;

        while (error) {
            System.out.println("Please enter the width of the Picross puzzle:");
            try {
                width = Integer.parseInt(scr.nextLine().trim());
                error = false;
                columns = new int[width][];
            } catch (NumberFormatException notAnInt) {
                System.out.println("Error: Input not an integer");
                error = true;
            }
        }
        error = true;
        while (error) {
            System.out.println("Please enter the height of the Picross puzzle:");
            try {
                height = Integer.parseInt(scr.nextLine().trim());
                error = false;
                rows = new int[height][];
            } catch (NumberFormatException notAnInt) {
                System.out.println("Error: Input not an integer");
                error = true;
            }
        }

        System.out.println("Please enter the numbers of each column, starting on the left. (Ex: 4 6 3)");
        for (int x = 0; x < width; x++) {
            System.out.println("Please enter the values of column " + (x+1) + ":");
            inputData = scr.nextLine().trim().split(" ");

            try {
                int[] storeNum = new int[inputData.length];
                int sum = 0;
                columns[x] = new int[inputData.length];
                if (inputData.length > highestColumn) {
                    highestColumn = inputData.length;
                }

                for (int i = 0; i < inputData.length; i++) {
                    storeNum[i] = Integer.parseInt(inputData[i]);
                    sum += storeNum[i];
                    columns[x][i] = storeNum[i];
                }

                if (sum + storeNum.length - 1 > height) {
                    System.out.println("Error: Input array is not possible");
                    x--;
                }
            } catch (NumberFormatException notAnInt) {
                System.out.println("Error: Input not an integer");
                x--;
            }
        }

        System.out.println("Please enter the numbers of each row, starting at the top. (Ex: 4 6 3)");
        for (int y = 0; y < height; y++) {
            System.out.println("Please enter the values of row " + (y+1) + ":");
            inputData = scr.nextLine().trim().split(" ");

            try {
                int[] storeNum = new int[inputData.length];
                int sum = 0;
                rows[y] = new int[inputData.length];
                if (inputData.length > widestRow) {
                    widestRow = inputData.length;
                }

                for (int i = 0; i < inputData.length; i++) {
                    storeNum[i] = Integer.parseInt(inputData[i]);
                    sum += storeNum[i];
                    rows[y][i] = storeNum[i];
                }

                if (sum + storeNum.length - 1 > width) {
                    System.out.println("Error: Input array is not possible");
                    y--;
                }
            } catch (NumberFormatException notAnInt) {
                System.out.println("Error: Input not an integer");
                y--;
            }
        }

        Solver solvedGraph = new Solver(columns, rows, highestColumn, widestRow);
        //solved = Solver.Solve(columns, rows, highestColumn, widestRow);

        System.out.println("Does this graph look correct?");
        for (int t = 0; t < highestColumn * 2 - 1; t++) {
            if (t % 2 == 0) {
                StringBuilder col = new StringBuilder();
                col.append(" ".repeat(widestRow * 2));
                for (int z = 0; z < width; z++) {
                    try {
                        col.append(columns[z][/*highestColumn - 1 - */counter]).append(" ");
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException noColumnData) {
                        col.append("  ");
                    }
                }
                System.out.println(col.toString());
                counter++;
            }
//            else {
//                System.out.println();
//            }
        }
        for (int t = 0; t < height; t++) {
            StringBuilder row = new StringBuilder();
            counter = 0;
            for (int z = 0; z < widestRow; z++) {
                try {
                    row.append(rows[t][counter]).append(" ");
                } catch (NullPointerException | ArrayIndexOutOfBoundsException noRowData) {
                    row.append("  ");
                }
                counter++;
            }
            for (int v = 0; v < width; v++) {
                //row.append(solved[v][t]).append(" ");
                row.append(solvedGraph.getGraph()[v][t]).append(" ");
            }
            System.out.println(row.toString());
        }

        System.out.println("\n" + Arrays.deepToString(solvedGraph.getGraph()));
    }
}
