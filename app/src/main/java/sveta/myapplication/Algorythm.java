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
//int iteration=0;
    private boolean mainSolver() {

        //iteration++;

        if (solvingGrid.isSolved())
            return true;

//        System.out.println();
//        System.out.println("Start iteration "+iteration);
//        System.out.println(solvingGrid);

       // Grid tempState=solvingGrid;

        if (!obvious()) {
           // / solvingGrid = gridChanges.peek();

            //solvingGrid=tempState;

           // iteration--;
//            System.out.println("Obvious false. Back to "+iteration);
//            System.out.println(solvingGrid);
            return false;
        }
//
//        System.out.println("After obvious");
//        System.out.println(solvingGrid);

        Cell nextCell = currentCell();

//        System.out.println("Picked cell");
//        System.out.println("Line: "+nextCell.line+" Column: "+nextCell.column);
//        System.out.print("Variants: ");

//        for (Integer var :
//                nextCell.possibleVariants) {
//            System.out.print(" " + var);
//        }
//        System.out.println();

        if (nextCell.possibleVariants.size() == 0) {
            //undoChanges();

//            System.out.println("No variants for next cell");

            if (solvingGrid.isSolved())
                return true;
            else
                return false;
        }

        for (Integer variant : nextCell.possibleVariants) {

//            System.out.println("Iteration "+iteration);
//            System.out.println("Set elem");
//            System.out.println("Line: "+nextCell.line+" Column: "+nextCell.column);
//            System.out.println("to "+variant);

            solvingGrid.setElement(nextCell.line, nextCell.column, variant);

//            System.out.println(solvingGrid);

            gridChanges.push(new Grid(solvingGrid));

            if (mainSolver())
                return true;
            else
                undoChanges();

        }

//        iteration--;
//        System.out.println("Back to "+iteration);

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