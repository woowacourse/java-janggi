package janggi.domain.state;

public abstract class Running extends Started {

    @Override
    public boolean isOngoing() {
        return true;
    }
}
