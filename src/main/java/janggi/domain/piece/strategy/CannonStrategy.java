package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CannonStrategy implements MoveStrategy{
    @Override
    public List<Path> findMovablePaths(Position current) {
        int currentRow = current.row();
        int currentCol = current.column();
        List<Path> paths = new ArrayList<>();

        //row
        for (int i = current.row() + 2; i <= 9; i++) {
            List<Position> route = new ArrayList<>();
            for (int j = current.row() + 1; j <= i; j++) {
                route.add(Position.of(j, currentCol));
            }
            Position destination = route.removeLast();
            paths.add(new Path(route, destination));
        }
        for (int i = current.row() - 2; i >= 0; i--) {
            List<Position> route = new ArrayList<>();
            for (int j = current.row() - 1; j >= i; j--) {
                route.add(Position.of(j, currentCol));
            }
            Position destination = route.removeLast();
            paths.add(new Path(route, destination));
        }

        //column
        for (int i = current.column() + 2; i < 9; i++) {
            List<Position> route = new ArrayList<>();
            for (int j = current.column() + 1; j <= i; j++) {
                route.add(Position.of(currentRow, j));
            }
            Position destination = route.removeLast();
            paths.add(new Path(route, destination));
        }
        for (int i = current.column() - 2; i >= 0; i--) {
            List<Position> route = new ArrayList<>();
            for (int j = current.column() - 1; j >= i; j--) {
                route.add(Position.of(currentRow, j));
            }
            Position destination = route.removeLast();
            paths.add(new Path(route, destination));
        }
        return Collections.unmodifiableList(paths);
    }
}