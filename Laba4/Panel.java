import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private Memory memory;

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void paint(Graphics g) {
        if (memory != null) {
            int sizeField = 8;
            int margin = 220;
            super.paint(g);
            int x = margin;
            int y = 0;

            for (int i = 0; i < memory.getAmountOfCells(); i++) {
                if (x + sizeField >= getWidth()) {
                    x = margin;
                    y += sizeField;
                }
                switch (memory.getCells()[i].getStatus()) {
                    //цвета не по заданию что бы не разъедали глаза...
                    case 0 -> g.setColor(Color.darkGray);
                    case 1 -> g.setColor(Color.cyan);
                    case 2 -> g.setColor(Color.magenta);
                }
                g.fillRect(x, y, sizeField, sizeField);
                g.setColor(Color.black);
                g.drawRect(x, y, sizeField, sizeField);
                x += sizeField;
            }
        }
    }
}
