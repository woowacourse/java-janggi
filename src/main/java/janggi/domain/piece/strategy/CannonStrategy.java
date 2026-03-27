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
            for (int row = current.row() + 1; row <= i; row++) {
                route.add(Position.of(row, currentCol));
            }
            Position destination = route.removeLast();
            paths.add(new Path(List.copyOf(route), destination));
        }
        for (int i = current.row() - 2; i >= 0; i--) {
            List<Position> route = new ArrayList<>();
            for (int row = current.row() - 1; row >= i; row--) {
                route.add(Position.of(row, currentCol));
            }
            Position destination = route.removeLast();
            paths.add(new Path(List.copyOf(route), destination));
        }

        //column
        for (int i = current.column() + 2; i < 9; i++) {
            List<Position> route = new ArrayList<>();
            for (int col = current.column() + 1; col <= i; col++) {
                route.add(Position.of(currentRow, col));
            }
            Position destination = route.removeLast();
            paths.add(new Path(List.copyOf(route), destination));
        }
        for (int i = current.column() - 2; i >= 0; i--) {
            List<Position> route = new ArrayList<>();
            for (int col = current.column() - 1; col >= i; col--) {
                route.add(Position.of(currentRow, col));
            }
            Position destination = route.removeLast();
            paths.add(new Path(List.copyOf(route), destination));
        }
        return Collections.unmodifiableList(paths);
    }
}