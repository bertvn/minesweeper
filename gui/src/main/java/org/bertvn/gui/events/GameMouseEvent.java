package org.bertvn.gui.events;

public class GameMouseEvent implements IGameEvent {

    public enum Type{
        HOLD,
        RELEASE;
    }

    private final Type type;

    public GameMouseEvent(Type type){
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
