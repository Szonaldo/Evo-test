import javax.swing.plaf.SplitPaneUI;

public class GardenTileGraphNode {
    private int x;
    private int y;
    private boolean isVisited;
    private boolean isPassable;
    private GardenTileGraphNode parent;

    public GardenTileGraphNode(){
        x = -1;
        y = -1;
        isVisited = false;
        isPassable = true;
        parent = null;
    }

    public GardenTileGraphNode(int x, int y, boolean visited, GardenTileGraphNode parent, String[][] layout){
        this.x = -1;
        this.y = -1;
        this.isVisited = false;
        this.isPassable = layout[x][y].compareTo("g") == 0;
        this.parent = null;
    }

    public GardenTileGraphNode(int x, int y, boolean visited){
        this.x = x;
        this.y = y;
        this.isVisited = false;
        this.parent = null;
    }

    public GardenTileGraphNode(int x, int y, boolean visited, boolean isPassable){
        this.x = x;
        this.y = y;
        this.isVisited = false;
        this.isPassable = isPassable;
        this.parent = null;
    }

    public GardenTileGraphNode(int x, int y, int distance, String isPassable){
        this.x = x;
        this.y = y;
        this.isVisited = false;
        if (isPassable.compareTo("g") == 0){
            this.isPassable = true;
        } else {
            this.isPassable = false;
        }
        this.parent = null;
    }

    public GardenTileGraphNode(int x, int y, int distance, boolean isVisited, String isPassable){
        this.x = x;
        this.y = y;
        this.isVisited = isVisited;
        if (isPassable.compareTo("g") == 0){
            this.isPassable = true;
        } else {
            this.isPassable = false;
        }
        this.parent = null;
    }

    public boolean equalString(String string){
        return string.compareTo(this.toString()) == 0;
    }

    public String toString(){
        return x + "," + y;
    }

    public int[] getArray(){
        return new int[]{this.x,this.y};
    }

    public boolean exist(){
        return this != null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public GardenTileGraphNode getParent() {
        return parent;
    }

    public void setParent(GardenTileGraphNode parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        this.isVisited = visited;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }
}
