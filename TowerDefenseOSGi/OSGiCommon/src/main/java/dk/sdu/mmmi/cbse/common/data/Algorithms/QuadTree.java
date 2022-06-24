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
    public Array<QuadTreeNode> points; // the points that are in the quadtree
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
    QuadTree NW; // north west quadtree
    QuadTree NE; // north east quadtree
    QuadTree SE; // south east quadtree
    QuadTree SW; // south west quadtree
    private int capacity; // the capacity of the quadtree
    private Rectangle rectangle; // the rectangle that the quadtree is in
    private boolean divided; // if the quadtree is divided or not
    public QuadTree(Rectangle rectangle, int capacity) { // constructor
        this.capacity = capacity; // set the capacity
        this.points = new Array<QuadTreeNode>(); // create the array of points
        this.rectangle = rectangle; // set the rectangle
        this.divided = false; // the quadtree is not divided
    }

    public boolean Insert(Vector2 point, TValue value) { // insert a point into the quadtree
        if (!this.rectangle.contains(point)) { // if the point is not in the rectangle
            return false; // return false
        }

        if (this.points.size < this.capacity) { // if the quadtree is not full
            this.points.add(new QuadTreeNode(point, value)); // add the point to the quadtree
            return true; // return true
        } else {
            if (!divided) { // if the quadtree is not divided
                subdivide(); // subdivide the quadtree
            }
            if (this.NW.Insert(point, value)) { // if the point is in the north west quadtree
                return true;
            } else if (this.NE.Insert(point, value)) {  // if the point is in the north east quadtree
                return true;
            } else if (this.SW.Insert(point, value)) { // if the point is in the south west quadtree
                return true;
            } else if (this.SE.Insert(point, value)) { // if the point is in the south east quadtree
                return true;
            }

        }


        return false; // if the point is not in the quadtree
    }



    private void subdivide() {
        float halfWidth = rectangle.width / 2; // get the half width of the rectangle
        float halfHeight = rectangle.height / 2; // get the half height of the rectangle


        Vector2 rectangleCenter = new Vector2(0, 0); // create a vector2 for the center of the rectangle
        rectangle.getCenter(rectangleCenter); // get the center of the rectangle

        Rectangle nwRect = new Rectangle(rectangleCenter.x - halfWidth, rectangleCenter.y, halfWidth, halfHeight); // create a rectangle for the north west quadtree
        Rectangle neRect = new Rectangle(rectangleCenter.x + halfWidth, rectangleCenter.y, halfWidth, halfHeight); // create a rectangle for the north east quadtree

        Rectangle swRect = new Rectangle(rectangleCenter.x, rectangleCenter.y - halfHeight, halfWidth, halfHeight); // create a rectangle for the south west quadtree
        Rectangle seRect = new Rectangle(rectangleCenter.x, rectangle.y - halfHeight, halfWidth, halfHeight); // create a rectangle for the south east quadtree


        NW = new QuadTree(nwRect, this.capacity); // create the north west quadtree
        NE = new QuadTree(neRect, this.capacity); // create the north east quadtree

        SW = new QuadTree(swRect, this.capacity); // create the south west quadtree
        SE = new QuadTree(seRect, this.capacity); // create the south east quadtree

        divided = true; // the quadtree is now divided
    }

    public Array<TValue> Query(Rectangle rect) { // query the quadtree for points that are in the rectangle
        Array<TValue> values = new Array<>(); // create a new array of values
        Query(rect, values); // query the quadtree for points that are in the rectangle
        return values; // return the array of values
    }

    public void Query(Rectangle rect, Array<TValue> values) { // query the quadtree for points that are in the rectangle
        if (!this.rectangle.overlaps(rect)) { // if the rectangle is not in the quadtree
            return;
        } else {
            for (QuadTreeNode node : points) { // for each node in the quadtree
                if (rect.contains(node.position)) { // if the rectangle contains the node
                    values.add(node.value); // add the node to the array of values
                }
            }
            if (this.divided) { // if the quadtree is divided
                this.NW.Query(rect, values); // query the north west quadtree
                this.NE.Query(rect, values); // query the north east quadtree
                this.SW.Query(rect, values); // query the south west quadtree
                this.SE.Query(rect, values); // query the south east quadtree
            }

        }
    }

    public void Clear() { // clear the quadtree
        this.points.clear(); // clear the points
        if (this.divided) { // if the quadtree is divided
            this.NW.Clear(); // clear the north west quadtree
            this.NE.Clear(); // clear the north east quadtree
            this.SW.Clear(); // clear the south west quadtree
            this.SE.Clear(); // clear the south east quadtree
        }
    }

    public void Render(GameData gameData) { // render the quadtree
        gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line); // begin the shape renderer
        gameData.GlobalShapeRenderer.setColor(Color.WHITE); // set the color to white
        gameData.GlobalShapeRenderer.rect(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height); // draw the rectangle of the quadtree as a rectangle with the width and height of the rectangle
        gameData.GlobalShapeRenderer.end(); // end the shape renderer

        gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // begin the shape renderer
        gameData.GlobalShapeRenderer.setColor(Color.WHITE); // set the color to white
        for (QuadTreeNode node : this.points) { // for each node in the quadtree
            gameData.GlobalShapeRenderer.circle(node.position.x, node.position.y, 5); // draw a circle at the position of the node
        }
        gameData.GlobalShapeRenderer.end(); // end the shape renderer

        if (this.divided) { // if the quadtree is divided
            this.NW.Render(gameData);  // render the north west quadtree
            this.NE.Render(gameData); // render the north east quadtree
            this.SW.Render(gameData); // render the south west quadtree
            this.SE.Render(gameData); // render the south east quadtree
        }


    }

    public class QuadTreeNode { // create a quadtree node
        public TValue value; // the value of the node
        public Vector2 position; // the position of the node

        public QuadTreeNode(Vector2 position, TValue value) { // create a quadtree node
            this.position = position; // set the position of the node
            this.value = value; // set the value of the node
        }
    }


}