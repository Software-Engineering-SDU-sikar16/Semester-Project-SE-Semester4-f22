/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class LifePart implements EntityPart {
    private boolean dead = false;
    private int life;
    private boolean isHit = false;


    public LifePart(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
    
    public boolean isDead() {
        return dead;
    }

    
    
    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {
    
        
    }
    
    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity)
    {
        if (isHit) {
            life =- 1;
            isHit = false;
        }
        if (life <= 0) {
            dead = true;
        }
    }
    
    @Override
    public void OnRender(GameData gameData, World world, Entity entity)
    {
    
    }
}
