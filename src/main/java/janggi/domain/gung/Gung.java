package janggi.domain.gung;

import janggi.domain.path.Path;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;

public class Gung {

    private static final Set<Path> PATHS = initializePaths();
    private static final PositionFile MIN_FILE = FILE_4;
    private static final PositionFile MAX_FILE = FILE_6;
    private static final PositionRank MIN_CHO_RANK = RANK_1;
    private static final PositionRank MAX_CHO_RANK = RANK_3;
    private static final PositionRank MIN_HAN_RANK = RANK_8;
    private static final PositionRank MAX_HAN_RANK = RANK_10;

    public boolean isInGung(final Path path) {
        return path.pathPositions().stream()
                .allMatch(this::isInGungPosition);
    }

    private boolean isInGungPosition(final Position position) {
        return position.file().isBetween(MIN_FILE, MAX_FILE)
               && (position.rank().isBetween(MIN_CHO_RANK, MAX_CHO_RANK)
                   || position.rank().isBetween(MIN_HAN_RANK, MAX_HAN_RANK));
    }

    public boolean isAvailablePathInGung(final Path path) {
        return PATHS.stream()
                .anyMatch(availablePath -> availablePath.isSuperPathOf(path));
    }

    public Set<Path> getAllPathsFrom(final Position position) {
        if (!isInGungPosition(position)) {
            return Set.of();
        }

        return PATHS.stream()
                .filter(path -> path.isStartWith(position))
                .collect(Collectors.toSet());
    }

    private static Set<Path> initializePaths() {
        Set<Path> paths = new HashSet<>();

        List<PositionRank[]> rankRanges = List.of(
                new PositionRank[]{RANK_1, RANK_2, RANK_3},
                new PositionRank[]{RANK_8, RANK_9, RANK_10}
        );
        PositionFile[] files = {FILE_4, FILE_5, FILE_6};

        for (PositionRank[] ranks : rankRanges) {
            for (PositionRank rank : ranks) {
                paths.addAll(new Path(List.of(
                        new Position(FILE_4, rank),
                        new Position(FILE_5, rank),
                        new Position(FILE_6, rank)
                )).subPathAndReverse());
            }

            for (PositionFile file : files) {
                paths.addAll(new Path(List.of(
                        new Position(file, ranks[0]),
                        new Position(file, ranks[1]),
                        new Position(file, ranks[2])
                )).subPathAndReverse());
            }

            paths.addAll(new Path(List.of(
                    new Position(FILE_4, ranks[0]),
                    new Position(FILE_5, ranks[1]),
                    new Position(FILE_6, ranks[2])
            )).subPathAndReverse());
            paths.addAll(new Path(List.of(
                    new Position(FILE_6, ranks[0]),
                    new Position(FILE_5, ranks[1]),
                    new Position(FILE_4, ranks[2])
            )).subPathAndReverse());
        }

        return paths;
    }
}
