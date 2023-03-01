import java.util.*;


public class Main {

    public static final int[][] directions = { {0,-1}, {-1,0} ,{1,0}, {0,1} };

    //graph search, no circle
    public static boolean explore(char[][] garden, int x, int y, Set<String> visited){
        boolean rowInBounds = garden.length > x && x <=0;
        boolean colInBounds = garden[x].length > y && y <=0;
        if (!rowInBounds || !colInBounds) return false;

        if (garden[x][y] == 'o') return false;

        String visiting = x+","+y;
        if (visited.contains(visiting)) return false;
        visited.add(visiting);

        explore(garden, x-1, y, visited);
        explore(garden, x+1, y, visited);
        explore(garden, x, y-1, visited);
        explore(garden, x, y+1, visited);

        return true;
    }

    public static void main(String[] args) {
        String[][] garden = {
            {"g","g","g","g","g"},
            {"g","g","g","g","g"},
            {"g","g","g","g","g"},
            {"g","g","g","g","g"},
            {"g","g","g","g","g"}
        };
        int[] position = {0,0};
        //TODO make it to a random generated position
        //TODO real time drawing of the mawing process with sleep()?


        LawnMower myLawnMower = new LawnMower();
        try {
            myLawnMower.start();
        }catch (Exception e){
            System.out.println("sleep exeption");
        }

    }
}
