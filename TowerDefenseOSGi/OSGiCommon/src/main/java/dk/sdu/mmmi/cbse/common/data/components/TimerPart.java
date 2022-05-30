/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.components;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class TimerPart implements EntityPart {

    private float expiration;

    public TimerPart(float expiration) {
        this.expiration = expiration;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {
    
    }
    
    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity)
    {
        if (expiration > 0) {
            reduceExpiration(gameData.getDelta());
        }
    }
    
    @Override
    public void OnRender(GameData gameData, World world, Entity entity)
    {
    
    }
    
}
