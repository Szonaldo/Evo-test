import java.io.*;
//TODO: enum for garden cases [clean,road,obstacle, mixed]
public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    /*
    *  0,1,2,
    *  3,x,4,
    *  5,6,7
    * */
    public static final int[][] directions = {
            {-1,-1}, {0,-1}, {1,-1},
            {-1,0} ,{1,0},
            {1,1}, {0,1}, {-1,1}
    };


    public static void main(String[] args) {
        Garden myGarden = new Garden();
        LawnMower LawnMower = new LawnMower();
        myGarden.printLayout();
        System.out.println("test");
    }
}
