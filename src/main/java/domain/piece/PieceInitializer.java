package domain.piece;

import domain.SetUp;
import domain.Team;
import domain.direction.PieceDirection;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Soldier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PieceInitializer {

    private static final Position HAN_GENERAL_POSITION = Position.of(5, 2);
    private static final Position HAN_CHARIOT_POSITION = Position.of(1, 1);
    private static final Position HAN_CANNON_POSITION = Position.of(2, 3);
    private static final Position HAN_GUARD_POSITION = Position.of(4, 1);
    private static final Position HAN_SOLDIER_POSITION = Position.of(1, 4);

    public static List<Piece> createTeamPieces(final Team team, final SetUp setUp) {
        if (team == Team.HAN) {
            return createHanPieces(setUp);
        }
        return createChoPieces(setUp);
    }

    private static List<Piece> createHanPieces(final SetUp setUp) {
        return createPieces(position -> position, setUp);
    }

    private static List<Piece> createChoPieces(final SetUp setUp) {
        return createPieces(Position::flipUpDown, setUp);
    }

    private static List<Piece> createPieces(final Function<Position, Position> teamSide, final SetUp setUp) {
        List<Piece> pieces = new ArrayList<>();
        addGeneral(teamSide, pieces);
        addChariots(teamSide, pieces);
        addCannons(teamSide, pieces);
        addGuards(teamSide, pieces);
        addSoldiers(teamSide, pieces);
        addSetUpPieces(teamSide, pieces, setUp);
        return pieces;
    }

    private static void addSetUpPieces(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                       SetUp setUp) {
        pieces.addAll(setUp.initializeHorseAndElephant(teamSide));
    }

    private static void addGeneral(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new General(teamSide.apply(HAN_GENERAL_POSITION), PieceDirection.GENERAL.get()));
    }

    private static void addChariots(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Chariot(teamSide.apply(HAN_CHARIOT_POSITION), PieceDirection.CHARIOT.get()));
        pieces.add(new Chariot(teamSide.apply(HAN_CHARIOT_POSITION.flipLeftRight()), PieceDirection.CHARIOT.get()));
    }

    private static void addCannons(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Cannon(teamSide.apply(HAN_CANNON_POSITION), PieceDirection.CANNON.get()));
        pieces.add(new Cannon(teamSide.apply(HAN_CANNON_POSITION.flipLeftRight()), PieceDirection.CANNON.get()));
    }

    private static void addGuards(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Guard(teamSide.apply(HAN_GUARD_POSITION), PieceDirection.GUARD.get()));
        pieces.add(new Guard(teamSide.apply(HAN_GUARD_POSITION.flipLeftRight()), PieceDirection.GUARD.get()));
    }

    private static void addSoldiers(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        for (int step = 0; step < 10; step += 2) {
            pieces.add(
                    new Soldier(teamSide.apply(HAN_SOLDIER_POSITION.moveRow(step)),
                            PieceDirection.HAN_SOLDIER.get()));
        }
    }
}
