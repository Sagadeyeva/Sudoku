package sveta.myapplication;

import java.util.ArrayList;
import java.util.Stack;

public final class Algorithm {
    private Grid solvingGrid;
    private Stack<Grid> gridChanges = new Stack<>();

    //O(N^2 * O(cellVariants))
    private int oneVariant() {
        ArrayList<Integer> possible;

        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (solvingGrid.getElement(i, j) == 0) {
                    possible = solvingGrid.cellVariants(i, j);
                    assert (possible != null);
                    if (possible.isEmpty()) {
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

    //O(N^2 * O(oneVariant))
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

    //O(N^2 * O(cellVariants))
    private Cell currentCell() {
        ArrayList<Integer> minPossibleVariants = new ArrayList<>();
        int minCellLine = 0;
        int minCellColumn = 0;
        int minAmountOfVariants = Grid.SIZE+1;
        ArrayList<Integer> currentPossibleVariants;
        for (int i = 0; i < Grid.SIZE && minAmountOfVariants > 2; i++) {
            for (int j = 0; j < Grid.SIZE && minAmountOfVariants > 2; j++) {
                if (solvingGrid.getElement(i, j) == 0) {
                    currentPossibleVariants = solvingGrid.cellVariants(i, j);
                    assert currentPossibleVariants != null;
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
       //O( (O(obvious)+O(currentCell))*N)
    private boolean mainSolver() {
        if (solvingGrid.isSolved())
            return true;
        if (!obvious()) {
             return false;
        }
        Cell nextCell = currentCell();
        if (nextCell.possibleVariants.size() == 0) {
            if (solvingGrid.isSolved())
                return true;
            else
                return false;
        }
        for (int variant : nextCell.possibleVariants) {
            solvingGrid.setElement(nextCell.line, nextCell.column, variant);
            gridChanges.push(new Grid(solvingGrid));
            if (mainSolver()) {
                return true;
            } else {
               undoChanges();
            }
        }
        return false;
    }

    private static final class Cell {
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