package com.mygdx.game.entity.items;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.effects.Effect;
import com.mygdx.game.effects.HealingEffect;
import com.mygdx.game.effects.StaminaRestoreEffect;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.Random;

public class PotionFactory {
    private PlayScreen playScreen;
    private PlayerData playerData;
    private Random rand;

    public PotionFactory(PlayScreen playScreen, PlayerData playerData) {
        this.playerData = playerData;
        this.playScreen = playScreen;

        rand = new Random();
    }

    public void createHealPotion(Vector2 position) {
        Potion potion = new Potion(new HealingEffect(2,0.1,playerData), playScreen.getWorld(), position);
        playScreen.addToUpdate(potion);
    }

    public void createSanityPotion(Vector2 position) {
        Potion potion = new Potion(new StaminaRestoreEffect(5,0.1,playerData), playScreen.getWorld(), position);
        playScreen.addToUpdate(potion);
    }

    public void createRandomPotion(Vector2 position) {
        if(rand.nextBoolean()) {
            createHealPotion(position);
        } else {
            createSanityPotion(position);
        }
    }
}
