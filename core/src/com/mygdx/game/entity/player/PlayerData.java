package com.mygdx.game.entity.player;

import com.mygdx.game.entity.EntityData;

public class PlayerData extends EntityData {
    private double maxStamina;
    private double stamina;
    public PlayerData() {
        super();
    }

    public PlayerData(double speed, double hp, double maxStamina, double stamina) {
        super(speed, hp);
        this.maxStamina = maxStamina;
        this.stamina = stamina;
    }
    public double getStamina(){
        return stamina;
    }
    public void addStamina(double stamina){
        if (this.stamina != maxStamina){
            this.stamina = (this.stamina + stamina > maxStamina ? maxStamina : this.stamina + stamina );

        }
    }
}
