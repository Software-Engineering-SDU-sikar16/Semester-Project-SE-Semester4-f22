package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class CustomCursorDrawer
{

    public static Pixmap CursorOpen;
    public static Pixmap CursorGrabbed;
    public static Pixmap CursorPointing;

    public CustomCursorDrawer()
    {
        CursorOpen = new Pixmap(Gdx.files.internal("../assets/ui/cursor_open.png"));
        CursorPointing = new Pixmap(Gdx.files.internal("../assets/ui/cursor_point.png"));
        CursorGrabbed = new Pixmap(Gdx.files.internal("../assets/ui/cursor_grab.png"));
        //SetOpenCursor();

    }

    public void render()
    {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            SetPointingCursor();
        } else {
            SetOpenCursor();
        }
        //Gdx.graphics.setCursor(cursor);
    }


    public static void SetCursor(Pixmap pixmap)
    {
        int xHotspot = pixmap.getWidth() / 2;
        int yHotspot = pixmap.getHeight() / 2;
        Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        Gdx.graphics.setCursor(cursor);
    }

    public static void SetOpenCursor()
    {
        SetCursor(CursorOpen);
    }


    public static void SetGrabCursor()
    {
        SetCursor(CursorGrabbed);
    }

    public static void SetPointingCursor()
    {
        SetCursor(CursorPointing);
    }

    public void Dispose()
    {
        CursorOpen.dispose();
        CursorGrabbed.dispose();
        CursorPointing.dispose();
    }


}