package org.bertvn.gui.events;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Notifier {

    private static Notifier notifier;

    private final List<WeakReference<IObserver>> observers = new ArrayList<>();

    private Notifier() {
        //do nothing
    }

    public static Notifier getInstance() {
        if(notifier == null) {
            notifier = new Notifier();
        }
        return notifier;
    }

    public void subscribe(IObserver observer) {
        observers.add(new WeakReference<>(observer));
    }

    public void unsubscribe(IObserver observer) {
        List<WeakReference<IObserver>> toDelete = observers.stream()
                .filter(x -> Objects.equals(x.get(), observer))
                .toList();
        observers.removeAll(toDelete);
    }

    public void notify(IGameEvent gameEvent) {
        for(WeakReference<IObserver> observer : observers) {
            IObserver actualObserver = observer.get();
            if(actualObserver != null) {
                actualObserver.notify(gameEvent);
            }
        }
    }
}
