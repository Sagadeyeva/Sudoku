package sveta.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class Grid {
    private final int grid[][];

    public Grid() {
        grid = new int[9][9];
    }

    public Grid(int[][] grid) {
        this.grid = grid;
    }

    public Grid(Grid other) {
        grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = other.grid[i][j];
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                str.append(grid[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    public void setElement(int line, int column, int value) {
        grid[line][column] = value;
    }

    public int getElmenet(int line, int column) {
        return grid[line][column];
    }

    public boolean checkLine(int line, int value) {
        for (int i = 0; i < 9; i++) {
            if (grid[line][i] == value) {
                return false;
            }
        }
        return true;
    }

    public boolean checkColumn(int column, int value) {
        for (int i = 0; i < 9; i++) {
            if (grid[i][column] == value) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSquare(int line, int column, int value) {
        int x = line / 3;
        int y = column / 3;
        x *= 3;
        y *= 3;
        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }

        }
        return true;
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> cellVariants(int line, int column) {
        if (grid[line][column] != 0) {
            return null;
        }
        ArrayList<Integer> setVariants = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            //кладем в i-ю ячейку число i
            setVariants.add(i, i);
        }
        //свободные цифры в линии
        for (int i = 0; i < 9; i++) {
            setVariants.set(grid[line][i], 0);
        }
        //
        for (int i = 1; i < 10; i++) {
            if (setVariants.get(i) != 0) {
                if (!checkSquare(line, column, setVariants.get(i))) {
                    setVariants.set(i, 0);

                } else if (!checkColumn(column, setVariants.get(i))) {
                    setVariants.set(i, 0);
                }
            }
        }

        ArrayList<Integer> possibleVariants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (setVariants.get(i) != 0) {
                possibleVariants.add(setVariants.get(i));
            }
        }
        return possibleVariants;
    }
}
