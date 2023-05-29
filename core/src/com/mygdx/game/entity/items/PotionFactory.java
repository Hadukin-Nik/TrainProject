package com.mygdx.game.entity.items;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.effects.HealingEffect;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

public class PotionFactory {
    private PlayScreen playScreen;
    private PlayerData playerData;

    public PotionFactory(PlayScreen playScreen, PlayerData playerData) {
        this.playerData = playerData;
        this.playScreen = playScreen;
    }

    public void createHealPotion(Vector2 position) {
        Potion potion = new Potion(new HealingEffect(2,0.1,playerData), playScreen.getWorld(), position);
        playScreen.addToUpdate(potion);
    }
}
