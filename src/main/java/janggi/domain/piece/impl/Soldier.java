package janggi.domain.piece.impl;

import janggi.domain.piece.CommonValidator;
import janggi.domain.piece.Team;
import janggi.domain.piece.PalacePiece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.function.Consumer;

public class Soldier extends PalacePiece {
    public static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(7, 1),
            new Position(7, 3),
            new Position(7, 5),
            new Position(7, 7),
            new Position(7, 9)
    );
    public static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(4, 5),
            new Position(4, 7),
            new Position(4, 9)
    );

    public Soldier(final Team team) {
        super("졸", team);
    }

    @Override
    public Consumer<Pieces> getMovableValidator(
            final Position beforePosition,
            final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateSingleStepMovement(beforePosition, afterPosition);
            CommonValidator.validateStraightMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateNotMovingTowardsOwnSide(beforePosition, afterPosition);
        };
    }

    @Override
    public Consumer<Pieces> getPalaceMovableValidator(final Position beforePosition, final Position afterPosition) {
        return pieces -> {
            CommonValidator.validateSingleStepMovement(beforePosition, afterPosition);
            validateNoSameTeamPieceAt(afterPosition, team, pieces);
            validateNotMovingTowardsOwnSide(beforePosition, afterPosition);
        };
    }

    public void validateNotMovingTowardsOwnSide(final Position beforePosition, final Position afterPosition) {
        if (team == Team.BLUE && afterPosition.x() - beforePosition.x() > 0) {
            throw new IllegalArgumentException("청졸은 아래 방향으로 이동할 수 없는 기물입니다.");
        }
        if (team == Team.RED && afterPosition.x() - beforePosition.x() < 0) {
            throw new IllegalArgumentException("홍졸은 윗 방향으로 이동할 수 없는 기물입니다.");
        }
    }

    @Override
    public int getScore() {
        return 2;
    }
}