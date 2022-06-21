package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;


public class HealthPart implements EntityPart {

    private static Texture HeartTexture;
    private static Texture HeartTextureNull;
    SpriteBatch batch;
    Array<Sprite> hearts = new Array<>();
    int healthCounter = 0;
    int maxHealth = 0;
    private Vector2 size = null;
    private int paddingX = 0;
    private int paddingY = 0;


    public HealthPart(Entity entity, int inputPaddingX, int inputPaddingY, int width, int height, int health) {
        LoadTex();
        batch = new SpriteBatch();
        size = new Vector2(width, height);
        LoadSprites(entity);


        paddingX = inputPaddingX;
        paddingY = inputPaddingY;
        healthCounter = health;
        maxHealth = health;
    }

    private void LoadTex() {
        HeartTexture = Resources.LoadTexture("../assets/ui/hp.png");
        HeartTextureNull = Resources.LoadTexture("../assets/ui/hp_null.png");

        for (int i = 0; i < healthCounter; i++) {
            hearts.add(new Sprite(HeartTexture));
        }
    }

    private void LoadSprites(Entity entity) {
        for (Sprite sprite : hearts) {
            sprite.setSize(size.x, size.y);
            sprite.setPosition(entity.getTransform().getX(), entity.getTransform().getY());
        }
    }

    public int GetHealth() {
        return MathUtils.clamp(healthCounter, 0, hearts.size - 1);
    }

    public boolean IsDead() {
        return healthCounter <= 0;
    }

    public void SubstractHealth() {
        healthCounter--;
        healthCounter = MathUtils.clamp(healthCounter, 0, hearts.size - 1);

        for (int i = healthCounter; i < hearts.size; i++) {
            hearts.get(i).setTexture(HeartTextureNull);
        }
    }

    public void AddHealth() {
        healthCounter = MathUtils.clamp(healthCounter, 0, hearts.size - 1);
        healthCounter++;

        for (int i = 0; i < healthCounter; i++) {
            hearts.get(i).setTexture(HeartTexture);
        }
    }


    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {
        if (entity == null) {
            return;
        }
        LoadTex();
        if (size == null || size == Vector2.Zero) {
            size = new Vector2(HeartTexture.getWidth() * 1.6f, HeartTexture.getHeight() * 1.6f);
        }
        LoadSprites(entity);

        for (int i = 0; i < hearts.size; i++) {
            hearts.get(i).setTexture(HeartTexture);
        }
    }

    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity) {
        if (entity == null) {
            return;
        }

        if (hearts.size < maxHealth) {
            LoadTex();
            LoadSprites(entity);
        }

        for (int i = 0; i < hearts.size; i++) {
            Sprite s = hearts.get(i);
            s.setPosition((paddingX * i) + (int) entity.getTransform().getX(), entity.getTransform().getY() + paddingY);
        }

    }

    @Override
    public void OnRender(GameData gameData, World world, Entity entity) {
        if (entity == null) {
            return;
        }
        gameData.GlobalSpriteBatch.begin();
        for (Sprite sprite : hearts) {
            sprite.draw(gameData.GlobalSpriteBatch);
        }
        gameData.GlobalSpriteBatch.end();
    }


}
