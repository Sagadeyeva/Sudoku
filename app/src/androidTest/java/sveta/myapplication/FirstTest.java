package sveta.myapplication;

import org.junit.Test;
import org.junit.Assert;

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
        int solvedArray[][] = new int[][]
                {{4, 6, 3, 1, 8, 2, 9, 7, 5,},
                        {5, 8, 7, 4, 6, 9, 1, 2, 3,},
                        {9, 2, 1, 3, 5, 7, 8, 6, 4,},
                        {2, 4, 8, 6, 7, 1, 3, 5, 9,},
                        {7, 5, 9, 2, 4, 3, 6, 1, 8,},
                        {1, 3, 6, 5, 9, 8, 7, 4, 2,},
                        {3, 7, 5, 9, 2, 6, 4, 8, 1,},
                        {8, 1, 4, 7, 3, 5, 2, 9, 6,},
                        {6, 9, 2, 8, 1, 4, 5, 3, 7,}};
        Grid inputGrid = new Grid(userGrid);
        Grid solvedGrid = new Grid(solvedArray);
        Algorithm solver = new Algorithm();
        Grid solved = solver.solve(inputGrid);
        Assert.assertEquals(solvedGrid, solved);
    }

    @Test
    public void test2() {
        System.out.println("Second");
        int userGrid[][] = new int[][]
                {{8, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 3, 6, 0, 0, 0, 0, 0},
                        {0, 7, 0, 0, 9, 0, 2, 0, 0},
                        {0, 5, 0, 0, 0, 7, 0, 0, 0},
                        {0, 0, 0, 0, 4, 5, 7, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 3, 0},
                        {0, 0, 1, 0, 0, 0, 0, 6, 8},
                        {0, 0, 8, 5, 0, 0, 0, 1, 0},
                        {0, 9, 0, 0, 0, 0, 4, 0, 0}};
        int solvedArray[][] = new int[][]
        {{8, 1, 2, 7, 5, 3, 6, 4, 9, },
            {9, 4, 3, 6, 8, 2, 1, 7, 5, },
            {6, 7, 5, 4, 9, 1, 2, 8, 3, },
            {1, 5, 4, 2, 3, 7, 8, 9, 6, },
            {3, 6, 9, 8, 4, 5, 7, 2, 1, },
            {2, 8, 7, 1, 6, 9, 5, 3, 4, },
            {5, 2, 1, 9, 7, 4, 3, 6, 8, },
            {4, 3, 8, 5, 2, 6, 9, 1, 7, },
            {7, 9, 6, 3, 1, 8, 4, 5, 2, },
        };
        Grid inputGrid = new Grid(userGrid);
        Grid solvedGrid = new Grid(solvedArray);
        Algorithm solver = new Algorithm();
        Grid solved = solver.solve(inputGrid);
        Assert.assertEquals(solvedGrid, solved);
    }


    @Test
    public void test3() {
        System.out.println("third");
        int userGrid[][] = new int[][]
                {{4, 0, 0, 2, 0, 0, 5, 0, 7},
                        {0, 0, 2, 0, 0, 7, 0, 0, 0},
                        {0, 5, 0, 0, 8, 0, 0, 0, 3},
                        {6, 0, 0, 8, 0, 0, 0, 9, 0},
                        {0, 0, 9, 0, 0, 0, 8, 0, 0},
                        {0, 2, 0, 0, 0, 6, 0, 0, 1},
                        {9, 0, 0, 0, 6, 0, 0, 1, 0},
                        {0, 0, 0, 4, 0, 0, 3, 0, 0},
                        {1, 0, 3, 0, 0, 2, 0, 0, 9}};
        int solvedArray[][] = new int[][]
        {{4, 1, 8, 2, 9, 3, 5, 6, 7, },
            {3, 9, 2, 6, 5, 7, 1, 8, 4},
            {7, 5, 6, 1, 8, 4, 9, 2, 3 },
            {6, 3, 1, 8, 4, 5, 7, 9, 2 },
            {5, 4, 9, 7, 2, 1, 8, 3, 6 },
            {8, 2, 7, 9, 3, 6, 4, 5, 1 },
            {9, 7, 4, 3, 6, 8, 2, 1, 5 },
            {2, 6, 5, 4, 1, 9, 3, 7, 8 },
            {1, 8, 3, 5, 7, 2, 6, 4, 9 },
        };
        Grid inputGrid = new Grid(userGrid);
        Grid solvedGrid = new Grid(solvedArray);
        Algorithm solver = new Algorithm();
        Grid solved = solver.solve(inputGrid);
        Assert.assertEquals(solvedGrid, solved);
    }
}
