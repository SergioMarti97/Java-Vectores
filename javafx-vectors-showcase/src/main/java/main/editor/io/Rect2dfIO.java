package main.editor.io;

import org.geom.shape.rectangle.Rect2df;
import org.simple.db.io.PlainTextIO;

public class Rect2dfIO extends PlainTextIO<Rect2df> {

    public Rect2dfIO(String fileName) {
        super(fileName);
    }

    @Override
    public boolean isMalformed(String line) {
        return line.split(" ").length != 4;
    }

    @Override
    public Rect2df build(String line) {
        String[] split = line.split(" ");
        try {
            float posX = Float.parseFloat(split[0].replace("x", ""));
            float posY = Float.parseFloat(split[1].replace("y", ""));
            float sizeX = Float.parseFloat(split[2].replace("x", ""));
            float sizeY = Float.parseFloat(split[3].replace("y", ""));
            return new Rect2df(posX, posY, sizeX, sizeY);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String write(Rect2df r) {
        return r.getPos().toString() + ' ' + r.getSize().toString();
    }

}
