package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ButtonPressed")
/**
 * Button pressed event
 */
public class ButtonPressed extends DataObject {
    public int getTimesPressed() {
        return timesPressed;
    }

    public void setTimesPressed(int timesPressed) {
        this.timesPressed = timesPressed;
    }

    int timesPressed;


    //Empty constructor for serialization
    public ButtonPressed() {
    }

    ButtonPressed(int timesPressed) {
        this.timesPressed = timesPressed;
    }
}
