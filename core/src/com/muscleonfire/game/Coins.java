package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Coins{
    private int coin;
    private FileHandle coinFile;

    // setter and getter
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

    // openCoinFile method
    public void openCoinFile(){
        // open coin.txt file and save coin to the file
        // if no file exists, make one and set value to zero
        coinFile = Gdx.files.external("coin.txt");
        if(coinFile.exists()){
            coin = Integer.parseInt(coinFile.readString());
        } else {
            coin = 0;
            coinFile.writeString(Integer.toString(coin), false);
        }
    }

    // addCoin method
    public void addCoin(Score score){ // pass score
        // add score/100 into coin
        coin += (int)(score.getScore()/100);
    }

    // spendCoin method
    public void spendCoin(int payment){ // pass payment
        // minus payment from coin
        coin -= payment;
        // save coin
        saveCoin();
    }

    // saveCoin method
    public void saveCoin(){
        // save the coin into file
        coinFile.writeString(Integer.toString(coin), false);
    }

    // displayCoin method (return String value)
    public String displayCoin(){
        return String.valueOf(coin);
    }
}
