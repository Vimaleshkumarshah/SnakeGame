import javax.swing.*;

public class frame extends JFrame {

    frame(){
//        adding the panel
        this.add(new panel());
//        title of window
        this.setTitle("SnackGame");
//        for uniform experience for different size window
        this.setResizable(false);
//         layout manager will size window according to the display
        this.pack();

        this.setVisible(true);
    }
}
