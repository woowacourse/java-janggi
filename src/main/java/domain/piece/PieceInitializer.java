package domain.piece;

import domain.direction.PieceDirections;
import domain.game.SetUp;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Soldier;
import domain.player.Team;
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
        addGeneral(teamSide, pieces);
        addChariots(teamSide, pieces);
        addCannons(teamSide, pieces);
        addGuards(teamSide, pieces);
        addSoldiers(teamSide, pieces, team);
        addSetUpPieces(teamSide, pieces, setUp);
        return pieces;
    }

    private static void addSetUpPieces(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                       SetUp setUp) {
        pieces.addAll(setUp.initializeHorseAndElephant(teamSide));
    }

    private static void addGeneral(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new General(teamSide.apply(HAN_GENERAL_POSITION), PieceDirections.GENERAL.get()));
    }

    private static void addChariots(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Chariot(teamSide.apply(HAN_CHARIOT_POSITION), PieceDirections.CHARIOT.get()));
        pieces.add(new Chariot(teamSide.apply(HAN_CHARIOT_POSITION.flipLeftRight()), PieceDirections.CHARIOT.get()));
    }

    private static void addCannons(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Cannon(teamSide.apply(HAN_CANNON_POSITION), PieceDirections.CANNON.get()));
        pieces.add(new Cannon(teamSide.apply(HAN_CANNON_POSITION.flipLeftRight()), PieceDirections.CANNON.get()));
    }

    private static void addGuards(final Function<Position, Position> teamSide, final List<Piece> pieces) {
        pieces.add(new Guard(teamSide.apply(HAN_GUARD_POSITION), PieceDirections.GUARD.get()));
        pieces.add(new Guard(teamSide.apply(HAN_GUARD_POSITION.flipLeftRight()), PieceDirections.GUARD.get()));
    }

    private static void addSoldiers(final Function<Position, Position> teamSide, final List<Piece> pieces,
                                    final Team team) {
        if (team == Team.HAN) {
            createSoldiers(teamSide, pieces, PieceDirections.HAN_SOLDIER);
            return;
        }
        createSoldiers(teamSide, pieces, PieceDirections.CHO_SOLDIER);
    }

    private static void createSoldiers(Function<Position, Position> teamSide, List<Piece> pieces,
                                       PieceDirections directions) {
        for (int step = 0; step < 10; step += 2) {
            pieces.add(
                    new Soldier(teamSide.apply(HAN_SOLDIER_POSITION.moveRow(step)),
                            directions.get()));
        }
    }
}
