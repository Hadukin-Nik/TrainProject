package com.mygdx.game.entity.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.EntityData;

public class BulletData extends EntityData {
    private double damage;
    private double speed;
    public static final short BULLET_BIT = 128;
    public static final short PLAYER_BIT = 2;
    private Texture texture;
    public BulletData(double speed, double hp, String pathFromAssetsToImage, double damage){
        super(speed, hp, pathFromAssetsToImage);
        this.damage = damage;
    }

    public BulletData(){
        damage = 1;
        speed = 1000;
        texture = new Texture(Gdx.files.internal(Constants.PATH_TO_STANDART_IMAGE));//пример

    }
    public double damage(){
        return damage;
    }
}
