package janggi.domain.piece.impl;

import janggi.domain.piece.CommonValidator;
import janggi.domain.piece.Team;
import janggi.domain.piece.PalacePiece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.function.Consumer;

public class Guard extends PalacePiece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(new Position(10, 4), new Position(10, 6));
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(new Position(1, 4), new Position(1, 6));

    public Guard(final Team team) {
        super("사", team);
    }

    @Override
    public Consumer<Pieces> getMovableValidator(final Position beforePosition, final Position afterPosition) {
        throw new IllegalArgumentException("사는 궁성 내에서만 이동할 수 있습니다.");
    }

    @Override
    public Consumer<Pieces> getPalaceMovableValidator(final Position beforePosition, final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateSingleStepMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
        };
    }

    @Override
    public int getScore() {
        return 3;
    }
}