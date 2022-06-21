package dk.sdu.mmmi.cbse.osgioverlay;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class OverlayUtils {
    public static GlyphLayout GlyphLayouter = null;


    private Vector2 GetTextCenterPosition(BitmapFont font, String text, float x, float y) {
        if (GlyphLayouter == null) {
            GlyphLayouter = new GlyphLayout();
        }
        GlyphLayouter.setText(font, text);
        return new Vector2(x - GlyphLayouter.width / 2, y);
    }


}
