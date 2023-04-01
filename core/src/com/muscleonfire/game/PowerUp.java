package com.muscleonfire.game;

public class PowerUp {

    public enum Power{
        Shield,
        Speed
    }

    private Power power = null;
    private Player pat;
    private float speedUp = 0;
    private float speedTime = 0;

    public PowerUp(Player pat){
        this.pat = pat;
    }

    public void setShieldUp(){
        power = Power.Shield;
        pat.setPlayerAnimTextures(new AnimationLoader().loadAnimation("Textures/player/playerfront_shield.png", 2,1, 0.5f),
                new AnimationLoader().loadAnimation("Textures/player/playerleft_shield.png", 4,1, 0.2f),
                new AnimationLoader().loadAnimation("Textures/player/playerright_shield.png", 4,1, 0.2f));
    }

    public void setSpeedUp(){
        power = Power.Speed;
        speedTime = 0;
        speedUp = 100;
        pat.setPlayerAnimTextures(new AnimationLoader().loadAnimation("Textures/player/playerfront_speed.png", 2,1, 0.5f),
                new AnimationLoader().loadAnimation("Textures/player/playerleft_speed.png", 4,1, 0.2f),
                new AnimationLoader().loadAnimation("Textures/player/playerright_speed.png", 4,1, 0.2f));
    }

    public boolean checkShield(){
        if (power == Power.Shield){
            power = null;
            pat.setPlayerAnimTextures( new AnimationLoader().loadAnimation("Textures/player/player_front.png", 2,1, 0.5f),
                    new AnimationLoader().loadAnimation("Textures/player/player_left.png", 4,1, 0.2f),
                    new AnimationLoader().loadAnimation("Textures/player/player_right.png", 4,1, 0.2f));
            return true;
        }
        return false;
    }


    public void checkSpeed(float delta){
        if(power == Power.Speed){
            if (speedTime < 10) {
                speedTime += delta;
            } else {
                speedUp = 0;
                power = null;
                pat.setPlayerAnimTextures(new AnimationLoader().loadAnimation("Textures/player/player_front.png", 2,1, 0.5f),
                        new AnimationLoader().loadAnimation("Textures/player/player_left.png", 4,1, 0.2f),
                        new AnimationLoader().loadAnimation("Textures/player/player_right.png", 4,1, 0.2f));
            }
        }
    }

    public float getSpeedUp() {
        return speedUp;
    }
}
