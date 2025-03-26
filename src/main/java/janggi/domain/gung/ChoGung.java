package janggi.domain.gung;

import janggi.domain.path.Path;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;

public class ChoGung extends Gung {

    private static final Set<Path> PATHS = initializePaths();
    private static final PositionFile MIN_FILE = FILE_4;
    private static final PositionFile MAX_FILE = FILE_6;
    private static final PositionRank MIN_RANK = RANK_1;
    private static final PositionRank MAX_RANK = RANK_3;

    @Override
    protected PositionFile getMinFile() {
        return MIN_FILE;
    }

    @Override
    protected PositionFile getMaxFile() {
        return MAX_FILE;
    }

    @Override
    protected PositionRank getMinRank() {
        return MIN_RANK;
    }

    @Override
    protected PositionRank getMaxRank() {
        return MAX_RANK;
    }

    @Override
    protected Set<Path> getPaths() {
        return PATHS;
    }

    @SuppressWarnings("DuplicatedCode")
    private static Set<Path> initializePaths() {
        final Set<Path> paths = new HashSet<>();
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
}
