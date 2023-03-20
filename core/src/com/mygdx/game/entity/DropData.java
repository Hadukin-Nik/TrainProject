package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class DropData extends EntityData{
    private Sound dropSound;

    public DropData(double speed, double hp, String pathToImage, String pathToSound) {
        super(speed, hp, pathToImage);

        dropSound = Gdx.audio.newSound(Gdx.files.internal(pathToSound));
    }

}
