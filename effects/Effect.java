package com.mygdx.game.entity.effects;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

public abstract class Effect {
    double stateTimer;
    double duration;
    PlayerProvider playerProvider;
    float amount;
    public void Effect(float amount, double duration)
    {
        this.amount = amount;
        this.duration = duration;
    }
    public abstract void ApplayEffect(PlayerProvider playerProvider);
    public abstract void EndEffect();
    void update(double dt) {
     stateTimer+=dt;
     if(stateTimer>=duration) EndEffect();
    }
    double GetDuration()
    {
        return duration;
    }
    double GetRemainingDuration(){
        return duration - stateTimer;
    }
    boolean IsActive(){ return GetRemainingDuration()>0;}
}
