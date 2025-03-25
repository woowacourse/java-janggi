package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.ArrayList;
import java.util.List;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;

public class Gung {

    private static final PositionFile MIN_FILE = FILE_4;
    private static final PositionFile MAX_FILE = FILE_6;
    private static final PositionRank MIN_RANK = RANK_1;
    private static final PositionRank MAX_RANK = RANK_3;
    private static final List<Path> availablePaths = initializeAllAvailablePaths();

    private static List<Path> initializeAllAvailablePaths() {
        final List<Path> paths = new ArrayList<>();

        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_1), new Position(FILE_5, RANK_1), new Position(FILE_6, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_2), new Position(FILE_5, RANK_2), new Position(FILE_6, RANK_2))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_3), new Position(FILE_5, RANK_3), new Position(FILE_6, RANK_3))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_1), new Position(FILE_4, RANK_2), new Position(FILE_4, RANK_3))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_5, RANK_1), new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_3))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_1), new Position(FILE_6, RANK_2), new Position(FILE_6, RANK_3))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_1), new Position(FILE_5, RANK_2), new Position(FILE_6, RANK_3))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_3), new Position(FILE_5, RANK_2), new Position(FILE_6, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_1), new Position(FILE_5, RANK_1), new Position(FILE_4, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_2), new Position(FILE_5, RANK_2), new Position(FILE_4, RANK_2))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_3), new Position(FILE_5, RANK_3), new Position(FILE_4, RANK_3))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_4, RANK_3), new Position(FILE_4, RANK_2), new Position(FILE_4, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_5, RANK_3), new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_3), new Position(FILE_6, RANK_2), new Position(FILE_6, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_3), new Position(FILE_5, RANK_2), new Position(FILE_4, RANK_1))).subPaths());
        paths.addAll(new Path(List.of(new Position(FILE_6, RANK_1), new Position(FILE_5, RANK_2), new Position(FILE_4, RANK_3))).subPaths());

        return paths;
    }


    public boolean isInGung(final Path path) {
        return path.pathPositions().stream()
                .allMatch(this::isInGungPosition);
    }

    private boolean isInGungPosition(final Position position) {
        return position.file().isBetween(MIN_FILE, MAX_FILE)
                && position.rank().isBetween(MIN_RANK, MAX_RANK);
    }

    public boolean isAvailablePathInGung(final Path path) {
        return availablePaths.stream()
                .anyMatch(availablePath -> availablePath.isSuperPathOf(path));
    }

    public List<Path> getAllPathsFrom(final Position position) {
        if (!isInGungPosition(position)) {
            return List.of();
        }

        return availablePaths.stream()
                .filter(path -> path.isStartWith(position))
                .toList();
    }
}
