package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Coins{
    private int coin;
    private FileHandle coinFile;

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public FileHandle getCoinFile() {
        return coinFile;
    }

    public void setCoinFile(FileHandle coinFile) {
        this.coinFile = coinFile;
    }

    public void openCoinFile(){
        coinFile = Gdx.files.external("coin.txt");
        if(coinFile.exists()){
            coin = Integer.parseInt(coinFile.readString());
        } else {
            coin = 0;
            coinFile.writeString(Integer.toString(coin), false);
        }
    }
    public void addCoin(Score score){
        coin += (int)(score.getScore()/100);
    }
    public void spendCoin(int payment){
        coin -= payment;
        saveCoin();
    }
    public void saveCoin(){
        coinFile.writeString(Integer.toString(coin), false);
    }
    public String displayCoin(){
        return String.valueOf(coin);
    }
}
