package model;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Point> path;

    public Path() {
        this.path = new ArrayList<>();
    }

    public void addPoint(Point p) {
        this.path.add(p);
    }

    public boolean contains(Point p) {
        return path.contains(p);
    }

    public List<Point> getPath() {
        return path;
    }
}
