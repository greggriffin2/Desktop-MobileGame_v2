package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

@JsonTypeName("EnemyKilled")
/**
 * Enemy Killed event
 */
public class EnemyKilled extends DataObject {
    String enemyType;

    public EnemyKilled(String type) {
        this.enemyType = type;
    }

    public EnemyKilled() {
    }


    public String getEnemyType() {
        return this.enemyType;
    }

    public void setEnemyType(String type) {
        this.enemyType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnemyKilled that = (EnemyKilled) o;
        return enemyType.equals(that.enemyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enemyType);
    }

}
