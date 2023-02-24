import java.util.ArrayList;

public class LawnMower {
    private int[] myPosition;
    private char[][] gardenLayout;
    private ArrayList<int[]> moveOptions;

    //methods
    public void move(){
        int i = 0;
        for (int[] direction : Main.directions) {
            int x = direction[0]+myPosition[0];
            int y = direction[1]+myPosition[1];
            if (x < gardenLayout.length  &&  x > 0  &&  y < gardenLayout[0].length  &&  y >0
            ){
                moveOptions.add(new int[] {x,y});
            }
        }
    }

    //constructors
    public LawnMower(){
        myPosition = new int[] {0,0};
        gardenLayout = new char[][]{
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'}
        };
        moveOptions = null;
    }

    public LawnMower(int x, int y){
        myPosition = new int[] {x,y};
    }

    //getters
    public int[] getMyPosition() {
        return myPosition;
    }
    //setters
    public void setMyPosition(int[] myPosition) {
        this.myPosition = myPosition;
    }
}
