/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class MovingPart implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up;

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float dt = gameData.getDelta();


    }

    @Override
    public void OnRender(GameData gameData, World world, Entity entity) {

    }

}
