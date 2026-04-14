package janggi.domain;

import java.util.List;

public class Path {
    private final List<JanggiPosition> route;
    private final JanggiPosition destination;

    public Path(List<JanggiPosition> route, JanggiPosition destination) {
        this.route = route;
        this.destination = destination;
    }

    public JanggiPosition destination() {
        return destination;
    }

    public boolean isDestinationInsidePalace() {
        return destination.isPalace();
    }

    public boolean hasDestination(JanggiPosition position) {
        return destination == position;
    }

    public boolean hasRoute(JanggiPosition currentPosition) {
        return route.stream()
                .anyMatch(position -> position == currentPosition);
    }
}
