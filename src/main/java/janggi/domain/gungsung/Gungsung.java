package janggi.domain.gungsung;

import janggi.domain.path.Path;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static janggi.domain.position.PositionFile.FILE_4;
import static janggi.domain.position.PositionFile.FILE_6;
import static janggi.domain.position.PositionRank.*;

public final class Gungsung {

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
        final Set<Path> paths = new HashSet<>();
        final Set<Path> defaultGungPaths = getDefaultGungPaths();

        defaultGungPaths.stream()
                .map(defaultPath -> defaultPath.parallelMove(3, 0))
                .forEach(paths::add);
        defaultGungPaths.stream()
                .map(defaultPath -> defaultPath.parallelMove(3, 7))
                .forEach(paths::add);

        return paths;
    }

    /**
     * 랭크 1~3, 파일 1~3 기준으로 궁의 모든 길을 만드는 메서드입니다.
     *
     * @return 기본 궁의 모든 길
     */
    private static Set<Path> getDefaultGungPaths() {
        final Set<Path> paths = new HashSet<>();

        final List<Position> diagonalPositions = new ArrayList<>();
        final List<Position> reverseDiagonalPositions = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            final List<Position> verticalPositions = new ArrayList<>();
            final List<Position> horizontalPositions = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                verticalPositions.add(new Position(PositionFile.findByAmount(i), PositionRank.findByAmount(j)));
                horizontalPositions.add(new Position(PositionFile.findByAmount(j), PositionRank.findByAmount(i)));
            }
            paths.addAll(new Path(verticalPositions).subPathAndReverse());
            paths.addAll(new Path(horizontalPositions).subPathAndReverse());

            diagonalPositions.add(new Position(PositionFile.findByAmount(i), PositionRank.findByAmount(i)));
            reverseDiagonalPositions.add(new Position(PositionFile.findByAmount(i), PositionRank.findByAmount(4 - i)));
        }
        paths.addAll(new Path(diagonalPositions).subPathAndReverse());
        paths.addAll(new Path(reverseDiagonalPositions).subPathAndReverse());

        return paths;
    }
}
