package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("EnemyKilled")
/**
 * Enemy Killed event
 */
public class EnemyKilled extends DataObject {
    public EnemyKilled() {}
    String enemyType;

    public String getEnemyType(){
        return this.enemyType;
    }
    public void setEnemyType(String type){
        this.enemyType = type;
    }
}
