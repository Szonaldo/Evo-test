

public class Main {

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
    }
}
