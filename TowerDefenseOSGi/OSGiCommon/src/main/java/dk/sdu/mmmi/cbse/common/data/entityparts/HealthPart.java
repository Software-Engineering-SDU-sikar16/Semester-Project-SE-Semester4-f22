package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Resources;

public class HealthPart implements EntityPart {
    public static Texture HeartTexture = Resources.LoadTexture("ui/hp.png");
    public static Texture HeartTextureNull = Resources.LoadTexture("ui/hp_null.png");
    int currentHealth;
    int startHealth;
    Sprite[] heartTextures;

    public HealthPart(int x, int y, int startHealth){
        this.startHealth = startHealth;
        currentHealth = startHealth;
        UpdateHeartTextureList();
    }
    public int GetHealth()
    {
        return currentHealth;
    }

    public void AddHealth(int amount){
        if (IsDead()){
            return;
        }
        if (currentHealth + amount > startHealth){
            return;
        }
        currentHealth += amount;
        UpdateHeartTextureList();
    }
    public void SubtractHealth(int amount){
        currentHealth -= amount;
        if (!IsDead()){
            UpdateHeartTextureList();
        }
    }

    public void UpdateHeartTextureList(){
        heartTextures = new Sprite[startHealth];
        for (int i = 0; i < startHealth; i++){
            if (i <= currentHealth) {
                heartTextures[i] = new Sprite(HeartTexture);

            } else {
                heartTextures[i] = new Sprite(HeartTextureNull);
            }
        }
    }

    public boolean IsDead()
    {
        return currentHealth <= 0;
    }

    public void SetSize(int width, int height){
        for (Sprite heart : heartTextures){
            heart.setSize(width, height);
        }
    }

    public void SetPosition(float width, float height){
        for (Sprite heart : heartTextures){
            heart.setPosition(width, height);
        }
    }
    @Override
    public void process(GameData gameData, Entity entity) {
        gameData.GlobalSpriteBatch.begin();
        if (entity.getTexture() != null){
            for (int i = 0; i < heartTextures.length; i++) {
                float heartX = entity.getX() + i*30;
                float heartY = entity.getY() - 15;
                heartTextures[i].setPosition(heartX, heartY);
                gameData.GlobalSpriteBatch.draw(heartTextures[i].getTexture(), heartTextures[i].getX(), heartTextures[i].getY(), heartTextures[i].getWidth(), heartTextures[i].getHeight());
            }

        }
        gameData.GlobalSpriteBatch.end();
    }

}
