package com.mygdx.game.entity.effects;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;

import java.util.ArrayList;

public class EffectManager {
    ArrayList<Effect> effects;
    PlayerProvider player;

    public EffectManager(PlayerProvider player)
    {
        this.player = player;
        effects = new ArrayList<>();
    }
    public void AddEffect(Effect effect)
    {
        effects.add(effect);
        effect.ApplayEffect(player);
    }

    public void update(double time)
    {
        ArrayList<Effect> toRemove = new ArrayList<>();
        for (Effect effect: effects)
        {
            effect.update(time);
            if(!effect.IsActive()) toRemove.add(effect);
        }
        for (Effect effect: toRemove) {
            effects.remove(effect);
        }
    }

    public ArrayList<Effect> GetEffects()
    {
        return effects;
    }

}
