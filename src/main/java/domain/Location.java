package domain;

import java.util.Objects;

public class Location {

    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location location)) {
            return false;
        }
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean isUnder(Location location) {
        return this.x < location.x || this.y < location.y;
    }

    public boolean isOver(Location location) {
        return this.x > location.x || this.y > location.y;
    }
}
