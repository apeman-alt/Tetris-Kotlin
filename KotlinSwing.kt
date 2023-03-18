import javax.swing.*;
import java.awt.*;

fun main() {
    //set up JFrame
    var window = JFrame("My Kotlin Window");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //set up JPanel
    var pan = JPanel();
    pan.setPreferredSize(Dimension(800,600));
    pan.setBackground(Color.decode("#cc6f68"));

    //add panel to window
    window.add(pan);

    //make window visible
    window.pack();
    window.setVisible(true);
}