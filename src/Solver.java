import java.util.Arrays;

public class Solver {
    private String[][] graph;
    private int[][] columns;
    private int[][] rows;
    private int columnLargest;
    private int rowLargest;

    public Solver(int[][] columns, int[][] rows, int columnLargest, int rowLargest) {
        this.columns = columns;
        this.rows = rows;
        this.columnLargest = columnLargest;
        this.rowLargest = rowLargest;
        graph = new String[columns.length][rows.length];

        Solve();
    }

    public String[][] getGraph() {
        return graph;
    }

    public int[][] getRows() {
        return rows;
    }

    public int[][] getColumns() {
        return columns;
    }

    private void Solve() {
        for (int x = 0; x < columns.length; x++) {
            for (int y = 0; y < rows.length; y++) {
                if (graph[x][y] == null) {
                    graph[x][y] = ".";
                }
            }
        }

        emptyFill();
        maxFill();
        weightedSections();

        int timeout = 0;

        while (timeout < 10 && !checkIfSolved()) {
            markEmpties();
            markFull();
            timeout++;
        }
    }

    private void maxFill() {
        for (int x = 0; x < columns.length; x++) {
            for (int y = 0; y < columnLargest; y++) {
                try {
                    if (columns[x][y] == columns.length) {
                        for (int i = 0; i < rows.length; i++) {
                            graph[x][i] = "■";
                        }
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException noColumnData) {
                    System.out.println("Error: Error with filling columns");
                }
            }
        }

        for (int x = 0; x < rows.length; x++) {
            for (int y = 0; y < rowLargest; y++) {
                try {
                    if (rows[x][y] == rows.length) {
                        for (int i = 0; i < columns.length; i++) {
                            graph[i][x] = "■";
                        }
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException noColumnData) {
                    System.out.println("Error: Error with filling rows");
                }
            }
        }
    }

    private void emptyFill() {
        for (int x = 0; x < columns.length; x++) {
            if (columns[x][0] == 0) {
                for (int i = 0; i < rows.length; i++) {
                    graph[x][i] = "x";
                }
            }
        }

        for (int y = 0; y < rows.length; y++) {
            if (rows[y][0] == 0) {
                for (int j = 0; j < columns.length; j++) {
                    graph[j][y] = "x";
                }
            }
        }
    }

    private void weightedSections() {
        for (int x = 0; x < columns.length; x++) {
            int sum = 0;
            for (int i : columns[x]) {
                sum += i;
            }
            if (columns[x][0] != columns.length && sum + columns[x].length - 1 == columns.length) {
                int[] iterateArray = Arrays.copyOf(getColumns()[x], getColumns()[x].length);
                for (int y = 0; y < rows.length; y++) {
                    if (iterateArray[0] > 0) {
                        graph[x][y] = "■";
                        iterateArray[0]--;
                    }
                    else if (iterateArray[0] == 0) {
                        graph[x][y] = "x";
                        iterateArray = Arrays.copyOfRange(iterateArray, 1, iterateArray.length);
                    }
                }
            }
        }

        for (int x = 0; x < rows.length; x++) {
            int sum = 0;
            for (int i : rows[x]) {
                sum += i;
            }
            if (rows[x][0] != rows.length && sum + rows[x].length - 1 == rows.length) {
                int[] iterateArray = Arrays.copyOf(getRows()[x], getRows()[x].length);
                for (int y = 0; y < columns.length; y++) {
                    if (iterateArray[0] > 0) {
                        graph[y][x] = "■";
                        iterateArray[0]--;
                    }
                    else if (iterateArray[0] == 0) {
                        graph[y][x] = "x";
                        iterateArray = Arrays.copyOfRange(iterateArray, 1, iterateArray.length);
                    }
                }
            }
        }
    }

    private void markEmpties() {
        for (int x = 0; x < graph.length; x++) {
            int blockCounter = 0, sum = 0;
            for (int y = 0; y < graph[x].length; y++) {
                if (graph[x][y] != null && graph[x][y].equals("■")) {
                    blockCounter++;
                }
            }

            for (int i = 0; i < columns[x].length; i++) {
                sum += columns[x][i];
            }

            if (blockCounter == sum) {
                for (int z = 0; z < graph[x].length; z++) {
                    if (!graph[x][z].equals("■")) {
                        graph[x][z] = "x";
                    }
                }
            }
        }

        for (int x = 0; x < graph[0].length; x++) {
            int blockCounter = 0, sum = 0;
            for (int y = 0; y < graph.length; y++) {
                if (graph[y][x] != null && graph[y][x].equals("■")) {
                    blockCounter++;
                }
            }

            for (int i = 0; i < rows[x].length; i++) {
                sum += rows[x][i];
            }

            if (blockCounter == sum) {
                for (int z = 0; z < graph.length; z++) {
                    if (!graph[z][x].equals("■")) {
                        graph[z][x] = "x";
                    }
                }
            }
        }
    }

    private void markFull() {
        for (int x = 0; x < graph.length; x++) {
            int openCounter = 0, sum = 0;
            for (int y = 0; y < graph[x].length; y++) {
                if (graph[x][y] != null && !graph[x][y].equals("x")) {
                    openCounter++;
                }
            }

            for (int i = 0; i < columns[x].length; i++) {
                sum += columns[x][i];
            }

            if (openCounter == sum) {
                for (int z = 0; z < graph[x].length; z++) {
                    if (!graph[x][z].equals("x")) {
                        graph[x][z] = "■";
                    }
                }
            }
        }

        for (int x = 0; x < graph[0].length; x++) {
            int openCounter = 0, sum = 0;
            for (int y = 0; y < graph.length; y++) {
                if (graph[y][x] != null && !graph[y][x].equals("x")) {
                    openCounter++;
                }
            }

            for (int i = 0; i < rows[x].length; i++) {
                sum += rows[x][i];
            }

            if (openCounter == sum) {
                for (int z = 0; z < graph.length; z++) {
                    if (!graph[z][x].equals("x")) {
                        graph[z][x] = "■";
                    }
                }
            }
        }
    }

    private boolean checkIfSolved() {
        for (int x = 0; x < graph.length; x++) {
            for (int y = 0; y < graph[0].length; y++) {
                if (graph[x][y].equals(".")) {
                    return false;
                }
            }
        }

        return true;
    }
}
