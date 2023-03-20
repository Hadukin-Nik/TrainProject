package com.mygdx.game.constants;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final double EPS = 0.0001;

    public static final double SPEED_STANDART = 1.0;
    public static final double HP_STANDART = 10;

    public static final float dampingPerSecond = 0.5f;

    public static final int HEIGHT_SCREEN_STANDART = 400;
    public static final int WIDTH_SCREEN_STANDART = 800;

    public static final Vector2 PLACE_OF_SPAWN_STANDART = new Vector2(WIDTH_SCREEN_STANDART / 2, HEIGHT_SCREEN_STANDART / 2);

    public static final Vector2 JUMP_DIRECTION_STANDART = new Vector2(2, 3);


    public static final String PATH_TO_STANDART_IMAGE = "badlogic.jpg";
    public static final String PATH_TO_STANDART_SOUNDSAMPLE = "drop.wav";
}
