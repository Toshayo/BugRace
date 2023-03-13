package net.toshayo.android.bugrace;

public interface IObservable {
    void attach(IObserver observer);
    void detach(IObserver observer);
    void notifyObservers();
}
