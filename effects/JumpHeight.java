package com.mygdx.game.entity.effects;

import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerProvider;

public class JumpHeight extends Effect{
    public JumpHeight(float amount, double duration)
    {
        this.Effect(amount,duration);
    }
    @Override
    public void ApplayEffect(PlayerProvider playerProvider) {
        this.playerProvider = playerProvider;
        this.playerProvider.playerData.jumpHeight+=amount/ Constants.PPM;
    }

    @Override
    public void EndEffect() {
        if(this.playerProvider.playerData.jumpHeight - this.amount/Constants.PPM > 0) playerProvider.playerData.jumpHeight-=this.amount/Constants.PPM;
    }
}
