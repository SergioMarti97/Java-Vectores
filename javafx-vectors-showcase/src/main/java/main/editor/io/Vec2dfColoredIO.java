package main.editor.io;

import main.editor.vec.Vec2dfColored;
import org.simple.db.io.PlainTextIO;

public class Vec2dfColoredIO extends PlainTextIO<Vec2dfColored> {

    public Vec2dfColoredIO(String fileName) {
        super(fileName);
    }

    @Override
    public boolean isMalformed(String line) {
        return false;
    }

    @Override
    public Vec2dfColored build(String line) {
        String[] split = line.split(" ");
        try {
            String name = split[0];
            float x = Float.parseFloat(split[1].replace("x", ""));
            float y = Float.parseFloat(split[2].replace("y", ""));
            float offX = Float.parseFloat(split[3].replace("x", ""));
            float offY = Float.parseFloat(split[4].replace("y", ""));
            float r = Float.parseFloat(split[5].replace("r", ""));
            float g = Float.parseFloat(split[6].replace("g", ""));
            float b = Float.parseFloat(split[7].replace("b", ""));
            return new Vec2dfColored(name, x, y, r, g, b).setOffset(offX, offY);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String write(Vec2dfColored v) {
        return v.toString();
    }

}
