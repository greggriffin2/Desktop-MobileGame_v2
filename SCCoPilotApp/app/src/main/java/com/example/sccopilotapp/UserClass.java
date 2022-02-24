package com.example.sccopilotapp;
import java.util.ArrayList;

public class UserClass {
    private int level;
    private ArrayList<Integer> perks;
    private boolean shipColor;

    UserClass(int level, ArrayList<Integer> perks, boolean shipColor) {
        this.level = level;
        this.perks = new ArrayList<>(6);
        this.shipColor = true;
    }

    public boolean getShipColor(){
        return this.shipColor;
    }

    /**
     * Need to add functionality to actually change the color of the ship Asset
     *
     * @param color
     */
    public void setShipColor(boolean color){
        this.shipColor = color;
    }

    public void setPerks(boolean color){
        this.shipColor = color;
    }

    public ArrayList<Integer> getPerks(){
        return this.perks;
    }

    /**
     * Need to add functionality to prevent perk overflow in limited capacity ArrayList
     *
     * @param newPerk
     * @return
     */
    public void setPerks(int newPerk){
        this.perks.remove(this.perks.size() - 1);
        this.perks.add(newPerk);
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return this.level;
    }
}
