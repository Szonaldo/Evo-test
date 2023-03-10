import java.util.*;

public class LawnMower {
    private int[] myPosition;
    private final String[][] myGarden;
    private GardenTileGraphNode[][] nodes;
    private final LinkedList<int[]> moveOptions;
    private final Set<String> pastMoves;

    public void start() throws InterruptedException {
        pastMoves.add(vectorToString(myPosition));
        int count = 1;

        updateVisit();
        System.out.println(count + " position: " + myPosition[0] + "," + myPosition[1]);

        //steps
        while (!moveOptions.isEmpty()){
            move();
            updateVisit();
            count++;
            //Thread.sleep(1000);
            System.out.println(count + " position: " + myPosition[0] + "," + myPosition[1]);
        }


    }

    private void neighbourRoutingUpdate(int x,
                                        int y,
                                        GardenTileGraphNode innerTemp,
                                        GardenTileGraphNode goal,
                                        LinkedList<GardenTileGraphNode> queue,
                                        Set<GardenTileGraphNode> explored,
                                        GardenTileGraphNode end){

        nodes[x][y].setParent(innerTemp);
        if (explored.contains(innerTemp) || queue.contains(innerTemp)){
            if (goal.equalString(nodes[x][y].toString())){
                end = nodes[x][y];
                return;
            }
            queue.add(nodes[x][y]);
        }

    }

    private boolean isGoalReached(GardenTileGraphNode goal, GardenTileGraphNode current){
        return goal.equalString(current.toString());
    }

    private void resetParentAndVisited(){
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                nodes[i][j].setVisited(false);
                nodes[i][j].setParent(null);
            }
        }
    }

    private LinkedList<int[]> getPath(GardenTileGraphNode goal){

        resetParentAndVisited();

        GardenTileGraphNode currentPosition = nodes[myPosition[0]][myPosition[1]];
        LinkedList<int[]> result = new LinkedList<>();
        if (goal.equalString(currentPosition.toString())){
            result.add(currentPosition.getArray());
            return result;
        }
        LinkedList<GardenTileGraphNode> queue = new LinkedList<>();
        Set<GardenTileGraphNode> explored = new HashSet<>();
        GardenTileGraphNode end = null;
        queue.add(currentPosition);
        currentPosition.setParent(null);

        while (queue.size() != 0){
            GardenTileGraphNode innerTemp = queue.poll(); // u
            explored.add(innerTemp);
            innerTemp.setVisited(true);

            for (GardenTileGraphNode neighbour : innerTemp.getNeighbours()) {
                neighbour.setParent(innerTemp);
                boolean exp = explored.contains(innerTemp);
                boolean que = queue.contains(innerTemp); //probl??m??s queue
                boolean vis = innerTemp.isVisited();
                boolean tmp = neighbour.getParent() == null;
                if (!vis || !que || !tmp ) {
                    if (isGoalReached(goal,neighbour)){
                        end = neighbour;
                        break;
                    }
                    if (neighbour.isPassable()) {
                        queue.add(neighbour);
                    }
                }
            }
            if (!queue.isEmpty() && isGoalReached(goal,queue.peekLast())){
                break;
            }
            /*

            if (inBoundsLeft(innerTemp.getArray())
                    && !nodes[innerTemp.getX()][innerTemp.getY() - 1].isVisited()
                    && nodes[innerTemp.getX()][innerTemp.getY() - 1].isPassable()){

                neighbourRoutingUpdate(innerTemp.getX(),innerTemp.getY() - 1,innerTemp,goal,queue,explored, end);
                if (goal.equalString(nodes[innerTemp.getX()][innerTemp.getY() - 1].toString())){
                    end = nodes[innerTemp.getX()][innerTemp.getY() - 1];
                    break;
                }

            }
            if (inBoundsUp(innerTemp.getArray())
                    && !nodes[innerTemp.getX() - 1][innerTemp.getY()].isVisited()
                    && nodes[innerTemp.getX() - 1][innerTemp.getY()].isPassable()){

                neighbourRoutingUpdate(innerTemp.getX() - 1,innerTemp.getY(),innerTemp,goal,queue,explored, end);
                if (goal.equalString(nodes[innerTemp.getX() - 1][innerTemp.getY()].toString())){
                    end = nodes[innerTemp.getX() - 1][innerTemp.getY()];
                    break;
                }
            }
            if (inBoundsRight(innerTemp.getArray())
                    && !nodes[innerTemp.getX()][innerTemp.getY() + 1].isVisited()
                    && nodes[innerTemp.getX()][innerTemp.getY() + 1].isPassable()) {

                neighbourRoutingUpdate(innerTemp.getX(),innerTemp.getY() + 1,innerTemp,goal,queue,explored, end);
                if (goal.equalString(nodes[innerTemp.getX()][innerTemp.getY() + 1].toString())){
                    end = nodes[innerTemp.getX()][innerTemp.getY() + 1];
                    break;
                }
            }
            if (inBoundsDown(innerTemp.getArray())
                    && !nodes[innerTemp.getX() + 1][innerTemp.getY()].isVisited()
                    && nodes[innerTemp.getX() + 1][innerTemp.getY()].isPassable()) {

                neighbourRoutingUpdate(innerTemp.getX()+1,innerTemp.getY(),innerTemp,goal,queue,explored, end);
                if (goal.equalString(nodes[innerTemp.getX()][innerTemp.getY()].toString())){
                    end = nodes[innerTemp.getX() + 1][innerTemp.getY()];
                    break;
                }
            }*/
        }

        if (end == null){
            return null;
        }
        else{
            currentPosition = end;
            while (currentPosition.getParent() != null){
                result.add(currentPosition.getArray());
                currentPosition = currentPosition.getParent();
            }
            return result;
        }
    }

    private void goToNextValidState(int[] goal){
        System.out.println("Entered goToNextValidState()");
        LinkedList<int[]> route = getPath(nodes[goal[0]][goal[1]]);

        assert route != null;
        for (int[] next : route) {
            myPosition = next;
            System.out.println(next.toString());
            for (int i = 0; i < myGarden.length; i++) {
                for (int j = 0; j < myGarden[i].length; j++) {
                    if (myPosition[0] == i && myPosition[1] == j){
                        System.out.print("@");
                    } else if(pastMoves.contains(vectorToString(new int[]{i,j}))){
                        System.out.print(ANSI.RED+myGarden[i][j]+ANSI.RESET);
                    } else {
                        System.out.print(ANSI.GREEN+myGarden[i][j]+ANSI.RESET);
                    }
                }
                System.out.println();
            }
        }
    }

    private void printCurrentState(){
        System.out.println("Entered printCurrentState()");
        for (int i = 0; i <myGarden.length; i++) {

            for (int j = 0; j < myGarden[i].length; j++) {

                if (myPosition[0] == i && myPosition[1] == j){
                    System.out.print("@");
                } else if(pastMoves.contains(vectorToString(new int[]{i,j}))){
                    System.out.print(ANSI.RED+myGarden[i][j]+ANSI.RESET);
                } else {
                    System.out.print(ANSI.GREEN+myGarden[i][j]+ANSI.RESET);
                }

            }
            System.out.println();
        }
    }

    private void move() {

        if (isNeighbour(moveOptions.getLast())) {
            myPosition = moveOptions.getLast();
            moveOptions.removeLast();
            pastMoves.add(vectorToString(myPosition));
            printCurrentState();
        } else {
            goToNextValidState(moveOptions.getLast());
        }
    }
    private int[] deltaPosition(int[] currentPos, int[] goalPos){
        return new int[] {currentPos[0] - goalPos[0], currentPos[1] - goalPos[1]};
        //return new int[] {myPosition[0] - myOptions.getLast()[0], myPosition[1] - myOptions.getLast()[0]};
    }

    private boolean isNeighbour(int[] goalPos){
        int[] temp = deltaPosition(myPosition,goalPos);
        //int[] temp = deltaPosition(myPosition,myOptions.getLast());
        boolean absX = Math.abs(temp[0]) <= 1;
        boolean absY = Math.abs(temp[1]) <= 1;
        return absX && absY ;
    }

    private String vectorToString(int[] vector){
        return vector[0] + "," + vector[1];
    }


    private boolean inBoundsUp(int[] pos){return  pos[0] - 1 >= 0;}

    private boolean inBoundsRight(int[] pos){return pos[1] + 1 < myGarden[pos[0]].length;}

    private boolean inBoundsDown(int[] pos){return pos[0] + 1 < myGarden.length;}

    private boolean inBoundsLeft(int[] pos){ return pos[1] - 1 >= 0;}

    private boolean linkedListStringContains(LinkedList<int[]> list, int[] position){
        for (int[] arr : list){
            if (arr[0] == position[0] && arr[1] == position[1]) return false;
        }
        return true;
    }

    //check 4 direction for possible tiles to step on, also add them to relevant stack
    private void updateVisit(){

        int[] temp = (myPosition[1] - 1 >= 0) ? new int[]{ myPosition[0],myPosition[1] - 1} : new int[]{-1,-1};
        boolean leftIsValid = myPosition[1] - 1 >= 0
                && myGarden[temp[0]][temp[1]].equals("g")
                && !pastMoves.contains(vectorToString(temp))
                && linkedListStringContains(moveOptions,temp);
        if (leftIsValid) moveOptions.add(temp);

        temp = (myPosition[0] + 1 < myGarden.length) ? new int[] {myPosition[0] + 1,myPosition[1]} : new int[]{-1,-1};
        boolean downIsValid = myPosition[0] + 1 < myGarden.length
                && myGarden[temp[0]][temp[1]].equals("g")
                && !pastMoves.contains(vectorToString(temp))
                && linkedListStringContains(moveOptions, temp);
        if (downIsValid) moveOptions.add(temp);

        temp = (myPosition[1] + 1 < myGarden[myPosition[0]].length) ? new int[] {myPosition[0],myPosition[1] + 1} : new int[]{-1,-1};
        boolean rightIsValid = myPosition[1] + 1 < myGarden[myPosition[0]].length
                && myGarden[temp[0]][temp[1]].equals("g")
                && !pastMoves.contains(vectorToString(temp))
                && linkedListStringContains(moveOptions, temp);
        if (rightIsValid) moveOptions.add(temp);


        temp = (myPosition[0] - 1 >= 0) ? new int[] {myPosition[0] - 1,myPosition[1]} : new int[]{-1,-1};
        boolean upIsValid = myPosition[0] - 1 >= 0
                && myGarden[temp[0]][temp[1]].equals("g")
                && !pastMoves.contains(vectorToString(temp))
                && linkedListStringContains(moveOptions, temp);
        if (upIsValid) moveOptions.add(temp);

    }


    //constructors
    public LawnMower(){
        myPosition = new int[] {0,0};
        myGarden = new String[][] {
                {"g","g","g","o","g"},
                {"g","g","g","o","g"},
                {"g","g","g","g","g"},
                {"g","g","g","g","g"},
                {"g","g","g","g","g"}
        };
        nodes = new GardenTileGraphNode[myGarden.length][myGarden[0].length];
        for (int i = 0; i < myGarden.length; i++) {
            for (int j = 0; j < myGarden[i].length; j++) {
                nodes[i][j] = new GardenTileGraphNode(i,j,myGarden[i][j]);
            }
        }
        for (int i = 0; i < myGarden.length; i++) {
            for (int j = 0; j < myGarden[i].length; j++) {
                if (i > 0) nodes[i-1][j].getNeighbours().add(nodes[i][j]);
                if (j > 0) nodes[i][j-1].getNeighbours().add(nodes[i][j]);
                if (j < myGarden[i].length - 2) nodes[i][j+1].getNeighbours().add(nodes[i][j]);
                if (i < myGarden.length - 2) nodes[i+1][j].getNeighbours().add(nodes[i][j]);
            }
        }
        moveOptions = new LinkedList<>();
        pastMoves = new HashSet<>();
    }

    public  LawnMower(int[] myPosition, String[][] gardenLayout){
        this.myPosition = myPosition;
        this.myGarden = gardenLayout;
        moveOptions = new LinkedList<>();
        pastMoves = new HashSet<>();
    }

    public LawnMower(int[] myPosition){
        this.myPosition = myPosition;
        myGarden= new String[][]{
                {"g","g","g","o","g"},
                {"g","g","g","o","g"},
                {"g","g","g","g","g"},
                {"g","g","g","g","g"},
                {"g","g","g","g","g"}
        };
        moveOptions = new LinkedList<>();
        pastMoves = new HashSet<>();
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
