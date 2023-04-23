package com.mygdx.game.constants;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final float EPS = 0.0001F;

    public static final float SPEED_STANDART = 1.0F;
    public static final float HP_STANDART = 10;
    public static final float PPM = 100;

    public static final float dampingPerSecond = 0.5f;

    public static final int HEIGHT_SCREEN_STANDART = 208;
    public static final int WIDTH_SCREEN_STANDART = 400;

    public static final Vector2 PLACE_OF_SPAWN_STANDART = new Vector2(32 / PPM, 32 / PPM);

    public static final Vector2 JUMP_DIRECTION_STANDART = new Vector2(2, 3);


    public static final String PATH_TO_STANDART_IMAGE = "StandartCircle.png";
    public static final String PATH_TO_STANDART_SOUNDSAMPLE = "drop.wav";
}
