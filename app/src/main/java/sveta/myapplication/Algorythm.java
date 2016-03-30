package sveta.myapplication;


import java.util.ArrayList;
import java.util.Stack;

public class Algorythm {
    private Grid solvingGrid;
    private Stack<Grid> gridChanges = new Stack<>();

    private int oneVariant() {
        ArrayList<Integer> possible;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (solvingGrid.getElmenet(i, j) == 0) {
                    possible = solvingGrid.cellVariants(i, j);
                    if (possible.size() == 0) {
                        return -1;
                    }
                    if (possible.size() == 1) {
                        solvingGrid.setElement(i, j, possible.get(0));
                        return 1;

                    }
                }
            }
        }
        return 0;
    }

    private boolean obvious() {
        int flag = 1;
        while (flag == 1) {
            flag = oneVariant();
        }
        if (flag == 0)
            return true;
        else
            return false;
    }

    public Grid solve(Grid grid) {
        solvingGrid = new Grid(grid);
        gridChanges.add(solvingGrid);
        if (mainSolver())
            return solvingGrid;
        else
            return grid;
    }

    private void undoChanges() {
        solvingGrid = gridChanges.pop();
    }

    private Cell currentCell() {
        ArrayList<Integer> minPossibleVariants = new ArrayList<>();
        int minCellLine = 0;
        int minCellColumn = 0;
        int minAmountOfVariants = 10;
        ArrayList<Integer> currentPossibleVariants;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (solvingGrid.getElmenet(i, j) == 0) {
                    currentPossibleVariants = solvingGrid.cellVariants(i, j);
                    if (currentPossibleVariants.size() < minAmountOfVariants) {
                        minAmountOfVariants = currentPossibleVariants.size();
                        minPossibleVariants = currentPossibleVariants;
                        minCellLine = i;
                        minCellColumn = j;
                    }
                }
            }
        }
        return new Cell(minPossibleVariants, minCellLine, minCellColumn);
    }

    private boolean mainSolver() {
        if (solvingGrid.isSolved())
            return true;

        if (!obvious()) {
            solvingGrid = gridChanges.peek();
            return false;
        }


        Cell nextCell = currentCell();
        if (nextCell.possibleVariants.size() == 0) {
            //undoChanges();
            if (solvingGrid.isSolved())
                return true;
            else
                return false;
        }

        for (Integer variant : nextCell.possibleVariants) {
            solvingGrid.setElement(nextCell.line, nextCell.column, variant);
            gridChanges.add(solvingGrid);
            if (mainSolver())
                return true;
            else
                undoChanges();
        }
        return false;
    }

    private final class Cell {
        public final ArrayList<Integer> possibleVariants;
        public final int line;
        public final int column;

        public Cell(ArrayList<Integer> possibleVariants, int line, int column) {
            this.column = column;
            this.line = line;
            this.possibleVariants = possibleVariants;
        }
    }
}