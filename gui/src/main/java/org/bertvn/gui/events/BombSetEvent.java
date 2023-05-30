package org.bertvn.gui.events;

public class BombSetEvent implements IGameEvent {

    private final int bombCount;

    public BombSetEvent(int bombCount){
        this.bombCount = bombCount;
    }

    public int getBombCount() {
        return bombCount;
    }
}
