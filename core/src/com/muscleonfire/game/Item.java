package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Item extends GameObject { // can use it to spawn items

    private FileHandle itemFile;
    private int shield_amt;
    private int speed_amt;

    public Item() {
        itemFile = Gdx.files.external("item.txt");

        if (itemFile.exists()) {
            String[] arr = itemFile.readString().split("@");
            shield_amt = Integer.parseInt(arr[0]);
            speed_amt = Integer.parseInt(arr[1]);
        } else {
            shield_amt = 0;
            speed_amt = 0;
            itemFile.writeString(shield_amt + "@" + speed_amt, false);
        }
    }

    public void updateItems(){
        itemFile.writeString(shield_amt + "@" + speed_amt, false);
        System.out.println(shield_amt + "@" + speed_amt);
    }

    public int getSpeed_amt() {
        String[] arr = itemFile.readString().split("@");
        shield_amt = Integer.parseInt(arr[0]);
        return speed_amt;
    }

    public void setSpeed_amt(int speed_amt) {
        this.speed_amt = speed_amt;
        updateItems();
    }

    public int getShield_amt() {
        String[] arr = itemFile.readString().split("@");
        speed_amt = Integer.parseInt(arr[1]);
        return shield_amt;
    }

    public void setShield_amt(int shield_amt) {
        this.shield_amt = shield_amt;
        updateItems();
    }
}