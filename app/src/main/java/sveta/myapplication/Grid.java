package sveta.myapplication;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

final public class Grid {
    public static final int UNIT = 3;
    public static final int SIZE = UNIT * UNIT;
    //    public static Grid generation(int difficulty) {
//
//
//    }
//
    private static final int MAX_SHUFFLE = 20;
    private final int grid[][];

    public Grid() {
        grid = new int[SIZE][SIZE];
    }

    public Grid(int[][] grid) {
        this.grid = grid;
    }

    public Grid(Grid other) {
        grid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = other.grid[i][j];
            }
        }
    }

    public static Grid generate(int difficulty) {
        int[][] setka = generateSolved();
        if (difficulty == 0)
            return new Grid(setka);

        Random random = new Random();
        for (int i = 0; i < difficulty; i++) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            setka[x][y] = 0;
        }
        return new Grid(setka);
    }

    private static int[][] generateSolved() {
        int[][] array = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                array[i][j] = (i * UNIT + i / UNIT + j) % SIZE + 1;
            }
        }
        Random random = new Random();
        int limit = random.nextInt(MAX_SHUFFLE);
        for (int i = 0; i < limit; i++) {
            if (random.nextBoolean()) transpose(array);
            if (random.nextBoolean()) shuffleSquareRows(array);
            if (random.nextBoolean()) shuffleSingleRows(array);
        }
        return array;
    }

    private static void transpose(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = array[i][j];
                array[i][j] = array[j][i];
                array[j][i] = temp;
            }
        }
    }

    private static void shuffleSquareRows(int[][] array) {
        Random random = new Random();
        for (int i = 0; i < UNIT - 1; i++) {
            int j = 1 + i + random.nextInt(UNIT - 1 - i);
            swapSquare(array, i, j);
        }
    }

    private static void swapSingle(int[][] array, int i, int j) {
        int[] temp = new int[SIZE];
        System.arraycopy(array[i], 0, temp, 0, SIZE);
        System.arraycopy(array[j], 0, array[i], 0, SIZE);
        System.arraycopy(temp, 0, array[j], 0, SIZE);

    }

    private static void swapSquare(int[][] array, int i, int j) {
        int[][] temp = new int[UNIT][SIZE];
        int iStart = i * UNIT;
        int jStart = j * UNIT;
        int iLimit = iStart + UNIT;
        int jLimit = jStart + UNIT;

        for (int k = iStart, l = 0; k < iLimit; k++, l++) {
            System.arraycopy(array[k], 0, temp[l], 0, SIZE);
        }

        for (int k = iStart, l = jStart; k < iLimit; k++, l++) {
            System.arraycopy(array[l], 0, array[k], 0, SIZE);
        }

        for (int k = jStart, l = 0; k < jLimit; k++, l++) {
            System.arraycopy(temp[l], 0, array[k], 0, SIZE);
        }

    }

    private static void shuffleSingleRows(int[][] array) {
        Random random = new Random();
        for (int i = 0; i < UNIT; i++) {
            int start = i * UNIT;
            int limit = start + UNIT - 1;
            for (int j = start; j < limit; j++) {
                int k = start + 1 + random.nextInt(limit - j);
                swapSingle(array, j, k);

            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid1 = (Grid) o;

        return Arrays.deepEquals(grid, grid1.grid);

    }

    @Override
    public int hashCode() {
        return grid != null ? Arrays.deepHashCode(grid) : 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                str.append(grid[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    public void setElement(int line, int column, int value) {
        grid[line][column] = value;
    }

    public int getElement(int line, int column) {
        return grid[line][column];
    }

    //O(N)
    public boolean checkLine(int line, int value) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[line][i] == value) {
                return false;
            }
        }
        return true;
    }

    //O(N)
    public boolean checkColumn(int column, int value) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][column] == value) {
                return false;
            }
        }
        return true;
    }

    //O((N/UNIT)^2)

    public boolean checkSquare(int line, int column, int value) {
        int x = line / UNIT;
        int y = column / UNIT;
        x *= UNIT;
        y *= UNIT;
        for (int i = x; i < x + UNIT; i++) {
            for (int j = y; j < y + UNIT; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }

        }
        return true;
    }

    public boolean isSolved() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    //O(UNITN)+O(N*(((N/UNIT)^2)+O(N))
    public ArrayList<Integer> cellVariants(int line, int column) {
        if (grid[line][column] != 0) {
            //throw new AssertionError("Grid element " + line + ":" + column + " should be empty");
            return null;
        }
        ArrayList<Integer> setVariants = new ArrayList<>(SIZE + 1);
        for (int i = 0; i < SIZE + 1; i++) {
            //кладем в i-ю ячейку число i
            setVariants.add(i, i);
        }
        //свободные цифры в линии
        for (int i = 0; i < SIZE; i++) {
            setVariants.set(grid[line][i], 0);
        }
        //
        for (int i = 1; i < SIZE + 1; i++) {
            if (setVariants.get(i) != 0) {
                if (!checkSquare(line, column, setVariants.get(i))) {
                    setVariants.set(i, 0);

                } else if (!checkColumn(column, setVariants.get(i))) {
                    setVariants.set(i, 0);
                }
            }
        }

        ArrayList<Integer> possibleVariants = new ArrayList<>();
        for (int i = 0; i < SIZE + 1; i++) {
            if (setVariants.get(i) != 0) {
                possibleVariants.add(setVariants.get(i));
            }
        }
        return possibleVariants;
    }
}
