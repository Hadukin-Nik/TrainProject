package com.mygdx.game.entity.effects;

import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerProvider;
public class SpeedBoost extends  Effect{

    public SpeedBoost(float amount, double duration)
    {
        this.Effect(amount,duration);
    }
    @Override
    public void ApplayEffect(PlayerProvider playerProvider) {
        this.playerProvider = playerProvider;
        //this.playerProvider.playerData.speed+=amount;
    }

    @Override
    public void EndEffect() {
        //if(this.playerProvider.playerData.speed - this.amount > 0) playerProvider.playerData.speed-=this.amount;
    }
}
