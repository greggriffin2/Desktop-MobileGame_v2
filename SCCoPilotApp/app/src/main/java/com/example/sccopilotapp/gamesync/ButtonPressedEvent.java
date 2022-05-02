package com.example.sccopilotapp.gamesync;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

@JsonTypeName("ButtonPressed")
public class ButtonPressedEvent extends DataObject {
    int timesPressed;


    //Empty constructor for serialization
    public ButtonPressedEvent() {
    }

    public ButtonPressedEvent(int timesPressed) {
        this.timesPressed = timesPressed;
    }

    public int getTimesPressed() {
        return timesPressed;
    }

    public void setTimesPressed(int timesPressed) {
        this.timesPressed = timesPressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ButtonPressedEvent that = (ButtonPressedEvent) o;
        return timesPressed == that.timesPressed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timesPressed);
    }
}
