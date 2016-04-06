package sveta.myapplication;

import org.junit.Test;

/**
 * Created by Светлана on 23.03.2016.
 */
public class FirstTest {


    @Test
    public void test1() {
        System.out.println("First");
        int userGrid[][] = new int[][]
                        {{4, 0, 3, 0, 0, 2, 0, 0, 0},
                        {5, 0, 0, 0, 6, 0, 1, 2, 0},
                        {9, 0, 0, 0, 0, 0, 0, 0, 4},
                        {0, 0, 8, 0, 7, 0, 0, 0, 0},
                        {0, 0, 0, 2, 0, 3, 0, 0, 8},
                        {0, 3, 6, 0, 0, 0, 7, 0, 0},
                        {0, 7, 0, 9, 2, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 5, 0, 9, 6},
                        {0, 0, 0, 8, 0, 4, 5, 0, 0}};
        Grid inputGrid = new Grid(userGrid);
        Algorithm solver = new Algorithm();
        Grid solved = solver.solve(inputGrid);

        System.out.println(inputGrid);
        System.out.println();
        System.out.println(solved);
    }

    @Test
    public void test2() {
        System.out.println("Second");
        int userGrid[][] = new int[][]
                {       {8, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 6, 0, 0, 0, 0, 0},
                        {0, 7, 0, 0, 9, 0, 2, 0, 0},
                        {0, 5, 0, 0, 0, 7, 0, 0, 0},
                        {0, 0, 0, 0, 4, 5, 7, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 3, 0},
                        {0, 0, 1, 0, 0, 0, 0, 6, 8},
                        {0, 0, 8, 5, 0, 0, 0, 1, 0},
                        {0, 9, 0, 0, 0, 0, 4, 0, 0}};
        Grid inputGrid = new Grid(userGrid);
        Algorithm solver = new Algorithm();
        Grid solved = solver.solve(inputGrid);

        System.out.println(inputGrid);
        System.out.println();
        System.out.println(solved);
    }


    @Test
    public void test3() {
        System.out.println("third");
        int userGrid[][] = new int[][]
                {       {4, 0, 0, 2, 0, 0, 5, 0, 7},
                        {0, 0, 2, 0, 0, 7, 0, 0, 0},
                        {0, 5, 0, 0, 8, 0, 0, 0, 3},
                        {6, 0, 0, 8, 0, 0, 0, 9, 0},
                        {0, 0, 9, 0, 0, 0, 8, 0, 0},
                        {0, 2, 0, 0, 0, 6, 0, 0, 1},
                        {9, 0, 0, 0, 6, 0, 0, 1, 0},
                        {0, 0, 0, 4, 0, 0, 3, 0, 0},
                        {1, 0, 3, 0, 0, 2, 0, 0, 9}};
        Grid inputGrid = new Grid(userGrid);
        Algorithm solver = new Algorithm();
        Grid solved = solver.solve(inputGrid);

        System.out.println(inputGrid);
        System.out.println();
        System.out.println(solved);
    }
}
