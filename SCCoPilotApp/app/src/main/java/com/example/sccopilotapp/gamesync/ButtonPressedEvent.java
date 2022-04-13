package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ButtonPressed")
public class ButtonPressedEvent extends DataObject {
    int timesPressed;

    //Empty constructor for serialization
    public ButtonPressedEvent() {
    }

    ButtonPressedEvent(int timesPressed) {
        this.timesPressed = timesPressed;
    }

    public int getTimesPressed() {
        return timesPressed;
    }

    public void setTimesPressed(int timesPressed) {
        this.timesPressed = timesPressed;
    }
}
