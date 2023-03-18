import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;

fun main() {
    var game = Game();
    game.setup();
    
}

class Game () {

    val SIZE = 40;
    val GRID_W = 10;
    val GRID_H = 20;

    fun setup() {
        var window = Window("Tetris");
        var panel = GamePanel(GRID_W*SIZE,GRID_H*SIZE);

        window.add(panel);
        window.setVisible();
    }

}

class Window (title: String): JFrame() {

    //constructor
    init {
        super.setTitle(title);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //change the window title
    override fun setTitle(newTitle: String) {
        super.setTitle(newTitle);
    }

    //return type String
    override fun getTitle(): String {
        return super.getTitle();
    }

    //add Panels
    fun add(panel: GamePanel) {
        super.add(panel);
    }

    //make window visible
    fun setVisible() {
        super.pack();
        super.setVisible(true);
    }

}

class GamePanel (width: Int, height: Int): JPanel() {
    var w: Int;
    var h: Int;
    
    init {
        w = width;
        h = height;
        super.setPreferredSize(Dimension(w, h));
    }

    override fun getWidth(): Int {
        return w;
    }

    override fun getHeight(): Int {
        return h;
    }

    fun setWidth(newW: Int) {
        w = newW;
    }

    fun setHeight(newH: Int) {
        h = newH;
    }
}