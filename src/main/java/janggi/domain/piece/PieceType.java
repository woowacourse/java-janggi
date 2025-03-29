package janggi.domain.piece;

import janggi.domain.gung.Gung;
import janggi.domain.path.path_filter.*;
import janggi.domain.path.path_provider.CrossPathProvider;
import janggi.domain.path.path_provider.GungOneStepPathProvider;
import janggi.domain.path.path_provider.GungPathProvider;
import janggi.domain.path.path_provider.PathProvider;
import janggi.domain.path.path_provider.movement_path_provider.*;

import java.util.List;

public enum PieceType {
    CHA(
            13,
            List.of(new CrossPathProvider(), new GungPathProvider(new Gung())),
            List.of(new BlockPathFilter(), new LastPositionAllyPathFilter())
    ),
    PO(
            7,
            List.of(new CrossPathProvider(), new GungPathProvider(new Gung())),
            List.of(new BlockSameTypePathFilter(), new JumpPathFilter(1), new LastPositionAllyPathFilter(), new LastPositionSameTypePathFilter())
    ),
    MA(
            5,
            List.of(new StraightDiagonalPathProvider()),
            List.of(new BlockPathFilter(), new LastPositionAllyPathFilter())
    ),
    SANG(
            3,
            List.of(new StraightDiagonalDiagonalPathProvider()),
            List.of(new BlockPathFilter(), new LastPositionAllyPathFilter())
    ),
    SA(
            3,
            List.of(new CrossOneStepPathProvider(), new GungOneStepPathProvider(new Gung())),
            List.of(new InGungPathFilter(new Gung()), new BlockPathFilter(), new LastPositionAllyPathFilter())
    ),
    JOL(
            2,
            List.of(new UpLeftRightPathProvider(), new GungOneStepPathProvider(new Gung())),
            List.of(new LastPositionAllyPathFilter())
    ),
    BYEONG(
            2,
            List.of(new DownLeftRightPathProvider(), new GungOneStepPathProvider(new Gung())),
            List.of(new LastPositionAllyPathFilter())
    ),
    GUNG(
            0,
            List.of(new CrossOneStepPathProvider(), new GungOneStepPathProvider(new Gung())),
            List.of(new InGungPathFilter(new Gung()), new BlockPathFilter(), new LastPositionAllyPathFilter())
    ),
    ;

    private final int score;
    private final List<PathProvider> pathProviders;
    private final List<PathFilter> pathFilters;

    PieceType(final int score, final List<PathProvider> pathProviders, final List<PathFilter> pathFilters) {
        this.score = score;
        this.pathProviders = pathProviders;
        this.pathFilters = pathFilters;
    }

    public int getScore() {
        return score;
    }

    public List<PathProvider> getPathProviders() {
        return pathProviders;
    }

    public List<PathFilter> getPathFilters() {
        return pathFilters;
    }
}