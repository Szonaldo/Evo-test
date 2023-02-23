public class Garden {
    private char[][] layout;

    Garden(){
        layout = new char[][]{
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g'}
        };
    }

    public void setLayout(char[][] layout) {
        this.layout = layout;
    }

    public char[][] getLayout() {
        return layout;
    }
}
