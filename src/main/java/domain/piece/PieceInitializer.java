package domain.piece;

import domain.game.SetUp;
import domain.player.Team;
import domain.position.Position;
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
            return createHanPieces(setUp, team);
        }
        return createChoPieces(setUp, team);
    }

    private static List<Piece> createHanPieces(final SetUp setUp, final Team team) {
        return createPieces(position -> position, setUp, team);
    }

    private static List<Piece> createChoPieces(final SetUp setUp, final Team team) {
        return createPieces(Position::flipUpDown, setUp, team);
    }

    private static List<Piece> createPieces(final Function<Position, Position> teamSide, final SetUp setUp,
                                            final Team team) {
        List<Piece> pieces = new ArrayList<>();
        addGeneral(teamSide, pieces, team);
        addChariots(teamSide, pieces);
        addCannons(teamSide, pieces);
        addGuards(teamSide, pieces, team);
        addSoldiers(teamSide, pieces, team);
        addSetUpPieces(teamSide, pieces, setUp);
        return pieces;
    }

    private static void addSetUpPieces(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                       final SetUp setUp) {
        pieces.addAll(setUp.initializeHorseAndElephant(teamSide));
    }

    private static void addGeneral(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                   final Team team) {
        if (team == Team.HAN) {
            pieces.add(new Piece(teamSide.apply(HAN_GENERAL_POSITION), PieceType.GENERAL, MovementRule.HAN_GENERAL));
            return;
        }
        pieces.add(new Piece(teamSide.apply(HAN_GENERAL_POSITION), PieceType.GENERAL, MovementRule.CHO_GENERAL));
    }

    private static void addChariots(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Piece(
                teamSide.apply(HAN_CHARIOT_POSITION), PieceType.CHARIOT, MovementRule.CHARIOT));
        pieces.add(new Piece(
                teamSide.apply(HAN_CHARIOT_POSITION.flipLeftRight()), PieceType.CHARIOT, MovementRule.CHARIOT));
    }

    private static void addCannons(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Piece(
                teamSide.apply(HAN_CANNON_POSITION), PieceType.CANNON, MovementRule.CANNON));
        pieces.add(new Piece(
                teamSide.apply(HAN_CANNON_POSITION.flipLeftRight()), PieceType.CANNON, MovementRule.CANNON));
    }

    private static void addGuards(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                  final Team team) {
        if (team == Team.HAN) {
            pieces.add(new Piece(
                    teamSide.apply(HAN_GUARD_POSITION), PieceType.GUARD, MovementRule.HAN_GUARD));
            pieces.add(new Piece(
                    teamSide.apply(HAN_GUARD_POSITION.flipLeftRight()), PieceType.GUARD, MovementRule.HAN_GUARD));
            return;
        }
        pieces.add(new Piece(
                teamSide.apply(HAN_GUARD_POSITION), PieceType.GUARD, MovementRule.CHO_GUARD));
        pieces.add(new Piece(
                teamSide.apply(HAN_GUARD_POSITION.flipLeftRight()), PieceType.GUARD, MovementRule.CHO_GUARD));
    }

    private static void addSoldiers(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                    final Team team) {
        if (team == Team.HAN) {
            createSoldiers(teamSide, pieces, MovementRule.HAN_SOLDIER);
            return;
        }
        createSoldiers(teamSide, pieces, MovementRule.CHO_SOLDIER);
    }

    private static void createSoldiers(Function<Position, Position> teamSide, List<Piece> pieces,
                                       final MovementRule rule) {
        for (int step = 0; step < 10; step += 2) {
            pieces.add(new Piece(teamSide.apply(HAN_SOLDIER_POSITION.moveRow(step)), PieceType.SOLDIER, rule));
        }
    }
}
