package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Coins{
    int coin;
    FileHandle coinFile;

    void openCoinFile(){
        coinFile = Gdx.files.external("coin.txt");
        if(coinFile.exists()){
            coin = Integer.parseInt(coinFile.readString());
        } else {
            coin = 0;
            coinFile.writeString(Integer.toString(coin), false);
        }
    }
    void addCoin(Score score){
        coin += (int)(score.score/100);
    }
    void saveCoin(){
        coinFile.writeString(Integer.toString(coin), false);
    }
}
