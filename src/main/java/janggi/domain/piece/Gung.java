package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.List;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;

public class Gung {

    private static final PositionFile MIN_FILE = FILE_4;
    private static final PositionFile MAX_FILE = FILE_6;
    private static final PositionRank MIN_RANK = RANK_1;
    private static final PositionRank MAX_RANK = RANK_3;

    private static final List<Path> availablePaths = List.of(
            new Path(List.of(new Position(FILE_4, RANK_1), new Position(FILE_5, RANK_1), new Position(FILE_6, RANK_1))),
            new Path(List.of(new Position(FILE_4, RANK_2), new Position(FILE_5, RANK_2), new Position(FILE_6, RANK_2))),
            new Path(List.of(new Position(FILE_4, RANK_3), new Position(FILE_5, RANK_3), new Position(FILE_6, RANK_3))),
            new Path(List.of(new Position(FILE_4, RANK_1), new Position(FILE_4, RANK_2), new Position(FILE_4, RANK_3))),
            new Path(List.of(new Position(FILE_5, RANK_1), new Position(FILE_5, RANK_2), new Position(FILE_5, RANK_3))),
            new Path(List.of(new Position(FILE_6, RANK_1), new Position(FILE_6, RANK_2), new Position(FILE_6, RANK_3))),
            new Path(List.of(new Position(FILE_4, RANK_1), new Position(FILE_5, RANK_2), new Position(FILE_6, RANK_3))),
            new Path(List.of(new Position(FILE_4, RANK_3), new Position(FILE_5, RANK_2), new Position(FILE_6, RANK_1)))
    );

    public boolean isInGung(final Path path) {
        return path.pathPositions().stream()
                .allMatch(this::isInGungPosition);
    }

    private boolean isInGungPosition(final Position position) {
        return position.file().isBetween(MIN_FILE, MAX_FILE)
                && position.rank().isBetween(MIN_RANK, MAX_RANK);
    }

    public boolean isAvailablePath(final Path path) {
        return availablePaths.stream()
                .anyMatch(availablePath -> availablePath.isSuperPathOf(path));
    }
}
