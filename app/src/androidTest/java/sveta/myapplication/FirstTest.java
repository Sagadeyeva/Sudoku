package sveta.myapplication;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by Светлана on 23.03.2016.
 */
public class FirstTest {
    

    @Test
    public void test1() {
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
        Algorythm solver = new Algorythm();
        Grid solved = solver.solve(inputGrid);

        System.out.println(inputGrid);
        System.out.println();
        System.out.println(solved);
    }
}
