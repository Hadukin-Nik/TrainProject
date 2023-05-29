package com.mygdx.game.effects;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;

import java.util.ArrayList;

public class EffectManager {
    private ArrayList<Effect> effects;
    private PlayerData player;


    public EffectManager(PlayerData player)
    {
        this.player = player;
        effects = new ArrayList<>();
    }
    public void AddEffect(Effect effect)
    {
        effects.add(effect);
        effect.ApplayEffect();
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
