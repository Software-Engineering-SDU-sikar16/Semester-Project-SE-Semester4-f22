package dk.sdu.mmmi.cbse.common.data.Algorithms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.GameData;

// inspired from this video https://www.youtube.com/watch?v=OJxEcs0w_kE
// the only difference is that it's created from 0,0 instead of from the center as in the video.
// we need this for bullets hitting an enemy. I didn't want to use box2d because it doesn't work very well when you set the position manually. each frame. and this is faster to implement than learning box2d library and spending time making it work.
public class QuadTree<TValue> {
    public Array<QuadTreeNode> points;
    /*
     *  ---------------
     * |       |       |
     * |  NW   |  NE   |
     * |       |       |
     * |---------------|
     * |       |       |
     * |  SW   |   SE  |
     * |       |       |
     *  ---------------
     */
    QuadTree NW;
    QuadTree NE;
    QuadTree SE;
    QuadTree SW;
    private int capacity;
    private Rectangle rectangle;
    private boolean divided;
    public QuadTree(Rectangle rectangle, int capacity) {
        this.capacity = capacity;
        this.points = new Array<QuadTreeNode>();
        this.rectangle = rectangle;
        this.divided = false;
    }

    public boolean Insert(Vector2 point, TValue value) {
        if (!this.rectangle.contains(point)) {
            return false;
        }

        if (this.points.size < this.capacity) {

            this.points.add(new QuadTreeNode(point, value));
            return true;
        } else {
            if (!divided) {
                subdivide();
            }
            if (this.NW.Insert(point, value)) {
                return true;
            } else if (this.NE.Insert(point, value)) {
                return true;
            } else if (this.SW.Insert(point, value)) {
                return true;
            } else if (this.SE.Insert(point, value)) {
                return true;
            }

        }


        return false;
    }



    private void subdivide() {
        float halfWidth = rectangle.width / 2;
        float halfHeight = rectangle.height / 2;


        Vector2 rectangleCenter = new Vector2(0, 0);
        rectangle.getCenter(rectangleCenter);

        Rectangle nwRect = new Rectangle(rectangleCenter.x - halfWidth, rectangleCenter.y, halfWidth, halfHeight);
        Rectangle neRect = new Rectangle(rectangleCenter.x + halfWidth, rectangleCenter.y, halfWidth, halfHeight);

        Rectangle swRect = new Rectangle(rectangleCenter.x, rectangleCenter.y - halfHeight, halfWidth, halfHeight);
        Rectangle seRect = new Rectangle(rectangleCenter.x, rectangle.y - halfHeight, halfWidth, halfHeight);


        NW = new QuadTree(nwRect, this.capacity);
        NE = new QuadTree(neRect, this.capacity);

        SW = new QuadTree(swRect, this.capacity);
        SE = new QuadTree(seRect, this.capacity);

        divided = true;
    }

    public Array<TValue> Query(Rectangle rect) {
        Array<TValue> values = new Array<>();
        Query(rect, values);
        return values;
    }

    public void Query(Rectangle rect, Array<TValue> values) {
        if (!this.rectangle.overlaps(rect)) {
            return;
        } else {
            for (QuadTreeNode node : points) {
                if (rect.contains(node.position)) {
                    values.add(node.value);
                }
            }
            if (this.divided) {
                this.NW.Query(rect, values);
                this.NE.Query(rect, values);
                this.SW.Query(rect, values);
                this.SE.Query(rect, values);
            }

        }
    }

    public void Clear() {
        this.points.clear();
        if (this.divided) {
            this.NW.Clear();
            this.NE.Clear();
            this.SW.Clear();
            this.SE.Clear();
        }
    }

    public void Render(GameData gameData) {
        gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        gameData.GlobalShapeRenderer.setColor(Color.WHITE);
        gameData.GlobalShapeRenderer.rect(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height);
        gameData.GlobalShapeRenderer.end();

        gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        gameData.GlobalShapeRenderer.setColor(Color.WHITE);
        for (QuadTreeNode node : this.points) {
            gameData.GlobalShapeRenderer.circle(node.position.x, node.position.y, 5);
        }
        gameData.GlobalShapeRenderer.end();

        if (this.divided) {
            this.NW.Render(gameData);
            this.NE.Render(gameData);
            this.SW.Render(gameData);
            this.SE.Render(gameData);
        }


    }

    public class QuadTreeNode {
        public TValue value;
        public Vector2 position;

        public QuadTreeNode(Vector2 position, TValue value) {
            this.position = position;
            this.value = value;
        }
    }


}