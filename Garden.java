import java.awt.*;
import java.io.Console;

public class Garden {
    private char[][] layout;

    public Garden(){
        layout = new char[][]{
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
                {'g', 'g', 'g', 'g', 'g','g', 'g', 'g', 'g', 'g'},
        };
    }
    //
    public void setLayout(char[][] layout) {
        this.layout = layout;
    }

    public char[][] getLayout() {
        return layout;
    }

    public void printLayout(){

        /* //TODO: fancy borders and visualisation
        for (int i = 0; i < layout.length+2; i++) {
            if (i == 0 || i == layout.length+1){
                System.out.printf("+");
            } else {
                System.out.printf("-");
            }
        }
        */

        for (int i = 0; i < layout.length; i++){
            for (int j = 0; j < layout[0].length; j++) {

                System.out.print(Main.ANSI_GREEN + layout[i][j]+" " + Main.ANSI_RESET);
            }
            System.out.println();
        }
    }
}
