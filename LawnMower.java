import java.util.*;

public class LawnMower {
    private int[] myPosition;
    private String[][] myGarden;
    private GardenTileGraphNode[][] nodes;
    private LinkedList<int[]> moveOptions;
    private Set<String> pastMoves;

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
            Thread.sleep(200);
            System.out.println(count + " position: " + myPosition[0] + "," + myPosition[1]);
        }


    }

    private void neighbourRoutingUpdate(int x, int y,GardenTileGraphNode innerTemp,GardenTileGraphNode goal, LinkedList<GardenTileGraphNode> queue,Set<GardenTileGraphNode> explored,GardenTileGraphNode end){
        //queue.add(new GardenTileGraphNode(innerTemp.getX(), innerTemp.getY() - 1, true, innerTemp, myGarden ));
        nodes[x][y].setParent(innerTemp);
        if (explored.contains(innerTemp) || queue.contains(innerTemp)){
            if (goal.equalString(nodes[x][y].toString())){
                end = nodes[x][y];
                return;
            }
            queue.add(nodes[x][y]);
        }

    }

    private LinkedList<int[]> getPath(GardenTileGraphNode goal){
        /*
        start myPositon;
        taget: goal;
        neighbours; : complicated
        frontier: queue;
        explored;*/
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
            GardenTileGraphNode innerTemp = queue.poll();
            explored.add(innerTemp);
            innerTemp.setVisited(true);

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
            }
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

/*
    private LinkedList<int[]> createRoute(int[] goal){ //todo


        LinkedList<GardenTileGraphNode> queue = new LinkedList<>();// format "x,y,distance
        Map<GardenTileGraphNode,GardenTileGraphNode> parentNodes;
        LinkedList<int[]> result = new LinkedList<>();
        GardenTileGraphNode current = new GardenTileGraphNode(myPosition[0],myPosition[1],0,true );

        queue.add(current);
        //visited.add(myPosition[0]+","+myPosition[1]);


        while (queue.size() != 0){

            current = queue.poll();
            result.add(current.getArray());
            if (current.getArray()[0] == goal[0] && current.getArray()[1] == goal[1]){
                return result;
            }

            if (inBoundsLeft(current.getArray())
                    && !nodes[current.getX()][current.getY() - 1].isVisited()
                    && nodes[current.getX()][current.getY() - 1].isPassable()){
                queue.add(new GardenTileGraphNode(current.getX(), current.getY() - 1, current.getDistance() + 1, true ));
                nodes[current.getX()][current.getY() - 1].setVisited(true);
            }
            if (inBoundsUp(current.getArray())
                    && !nodes[current.getX() - 1][current.getY()].isVisited()
                    && nodes[current.getX() - 1][current.getY()].isPassable()){
                queue.add(new GardenTileGraphNode(current.getX() - 1, current.getY(), current.getDistance() + 1, true));
                nodes[current.getX() - 1][current.getY()].setVisited(true);
            }
            if (inBoundsRight(current.getArray())
                    && !nodes[current.getX()][current.getY() + 1].isVisited()
                    && nodes[current.getX()][current.getY() + 1].isPassable()) {
                queue.add(new GardenTileGraphNode(current.getX(), current.getY() + 1, current.getDistance() + 1, true));
                nodes[current.getX()][current.getY() + 1].setVisited(true);
            }
            if (inBoundsDown(current.getArray())
                    && !nodes[current.getX() + 1][current.getY()].isVisited()
                    && nodes[current.getX() + 1][current.getY()].isPassable()) {
                queue.add(new GardenTileGraphNode(current.getX() + 1, current.getY(), current.getDistance() + 1, true));
                nodes[current.getX() + 1][current.getY()].setVisited(true);
            }

        }


        return null;
    }
*/

    private void goToNextValidState(int[] goal){
        System.out.println("Entered goToNextValidState()");
        LinkedList<int[]> route = getPath(nodes[goal[0]][goal[1]]);
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                nodes[i][j].setVisited(false);
                nodes[i][j].setParent(null);
            }
        }
        for (int[] next : route) {
            myPosition = next;
            System.out.println(next.toString());
            for (int i = 0; i < myGarden.length; i++) {
                for (int j = 0; j < myGarden[i].length; j++) {
                    if (myPosition[0] == i && myPosition[1] == j){
                        System.out.printf("@");
                    } else if(pastMoves.contains(vectorToString(new int[]{i,j}))){
                        System.out.printf(ANSI.RED+myGarden[i][j]+ANSI.RESET);
                    } else {
                        System.out.printf(ANSI.GREEN+myGarden[i][j]+ANSI.RESET);
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
                    System.out.printf("@");
                } else if(pastMoves.contains(vectorToString(new int[]{i,j}))){
                    System.out.printf(ANSI.RED+myGarden[i][j]+ANSI.RESET);
                } else {
                    System.out.printf(ANSI.GREEN+myGarden[i][j]+ANSI.RESET);
                }

            }
            System.out.println();
        }
    }

    private void move() {
        /*
        myPosition = moveOptions.getLast();
        moveOptions.removeLast();
        pastMoves.add(vectorToString(myPosition));*/

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

    private boolean inBoundsUp(){return  myPosition[0] - 1 >= 0;}
    private boolean inBoundsUp(int[] pos){return  pos[0] - 1 >= 0;}

    private boolean inBoundsRight(){return myPosition[1] + 1 < myGarden[myPosition[0]].length;}
    private boolean inBoundsRight(int[] pos){return pos[1] + 1 < myGarden[pos[0]].length;}

    private boolean inBoundsDown(){return myPosition[0] + 1 < myGarden.length;}
    private boolean inBoundsDown(int[] pos){return pos[0] + 1 < myGarden.length;}

    private boolean inBoundsLeft(){ return myPosition[1] - 1 >= 0;}
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
                nodes[i][j] = new GardenTileGraphNode(i,j,0,myGarden[i][j]);
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
