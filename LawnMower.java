public class LawnMower {
    private int[] myPosition;

    LawnMower(){
        myPosition = new int[] {0,0};
    }

    public int[] getMyPosition() {
        return myPosition;
    }

    public void setMyPosition(int[] myPosition) {
        this.myPosition = myPosition;
    }
}
