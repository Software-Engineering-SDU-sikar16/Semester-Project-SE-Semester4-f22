package dk.sdu.mmmi.cbse.common.data.helpers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontResource {
    int Size;
    BitmapFont Font;

    public FontResource(BitmapFont font, int size) {
        Font = font;
        Size = size;
    }

    public static String GetCharacterRangeFromTo(int from, int to) {
        StringBuilder builder = new StringBuilder();


        for (int i = from; i < to; i++) {
            String s = String.valueOf((char) i);
            builder.append(s);
        }

        return builder.toString();
    }

    public void Dispose() {
        Font.dispose();
    }

    int GetSize() {
        return Size;
    }

    public BitmapFont GetFont() {
        return Font;
    }
}
