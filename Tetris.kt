import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

fun main() {
    var game = Game();
    game.setup();
    game.run();
    
}

class Game () {

    

    companion object {
        val SIZE = 40;
        val GRID_W = 10;
        val GRID_H = 20;

        var s = Square();
        lateinit var panel: GamePanel;
    }

    fun setup() {
        var window = Window("Tetris");
        panel = GamePanel(GRID_W*SIZE,GRID_H*SIZE);

        window.add(panel);
        window.setVisible();
    }

    fun run() {
        while (true) {
            s.moveDown();
            Thread.sleep(500);
            panel.refresh();    
        }
    }

    fun getSize(): Int {
        return SIZE;
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

    override fun paintComponent(g: Graphics) {

        //clear background
        clear(g);

        //draw squares
        Game.s.draw(g);

    }

    fun clear(g: Graphics) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,w,h);
    }

    fun refresh() {
        super.repaint();
    }

}

class Square {
    var x: Int;
    var y: Int;
    val color: Color;
    val SIZE = Game.SIZE;

    init {
        x = SIZE*Game.GRID_W/2 - SIZE;
        y = 0;
        color = generateColor();
    }

    fun generateColor(): Color {
        val colors = arrayOf(Color.RED, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN, Color.YELLOW);
        var last = colors.size -1;
        return colors[(0..last).random()]
    }

    fun draw (g: Graphics) {
        g.setColor(color);
        g.fillRect(x,y,SIZE,SIZE);
    }

    fun moveDown() {
        y+=SIZE;
    }
}
