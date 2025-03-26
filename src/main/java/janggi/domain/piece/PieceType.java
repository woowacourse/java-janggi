package janggi.domain.piece;

import janggi.domain.path.path_filter.*;
import janggi.domain.path.path_provider.movement_path_provider.*;
import janggi.domain.path.path_provider.CrossLinePathProvider;
import janggi.domain.path.path_provider.GungOneStepPathProvider;
import janggi.domain.path.path_provider.GungPathProvider;
import janggi.domain.path.path_provider.PathProvider;

import java.util.List;

public enum PieceType {
    차(
            List.of(new CrossLinePathProvider(), new GungPathProvider(new Gung())),
            List.of(new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter())
    ),
    마(
            List.of(new StraightDiagonalPathProvider()),
            List.of(new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter())
    ),
    상(
            List.of(new StraightDiagonalDiagonalPathProvider()),
            List.of(new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter())
    ),
    사(
            List.of(new CrossPathProvider(), new GungOneStepPathProvider(new Gung())),
            List.of(new InGungPathFilter(new Gung()), new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter())
    ),
    장(
            List.of(new CrossPathProvider(), new GungOneStepPathProvider(new Gung())),
            List.of(new InGungPathFilter(new Gung()), new NonMiddleBlockPathFilter(), new NonStopAtAllyPathFilter())
    ),
    포(
            List.of(new CrossLinePathProvider(), new GungPathProvider(new Gung())),
            List.of(new BlockSameTypePathFilter(), new JumpPathFilter(1), new NonStopAtAllyPathFilter(), new NonStopAtSameTypePathFilter())
    ),
    졸(
            List.of(new UpLeftRightPathProvider()),
            List.of(new NonStopAtAllyPathFilter())
    ),
    병(
            List.of(new DownLeftRightPathProvider()),
            List.of(new NonStopAtAllyPathFilter())
    );

    public final List<PathProvider> pathProviders;
    public final List<PathFilter> pathFilters;

    PieceType(final List<PathProvider> pathProviders, final List<PathFilter> pathFilters) {
        this.pathProviders = pathProviders;
        this.pathFilters = pathFilters;
    }
}
