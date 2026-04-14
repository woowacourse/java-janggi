package factory;

import domain.Board;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import domain.Piece;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardFactory {

    private static final UpTeamPiecePositionFactory upTeamPiecePositionFactory = new UpTeamPiecePositionFactory();
    private static final DownTeamPositionFactory downTeamPositionFactory = new DownTeamPositionFactory();

    private static final Map<Position, Piece> board = new HashMap<>();

    public static Board initialBoard() {
        return Board.of(initialPieces());
    }

    private static Map<Position, Piece> initialPieces() {
        setupUpTeamPieces();
        setupDownTeamPieces();
        setupEmptySpaces();

        return new HashMap<>(board);
    }

    private static void setupEmptySpaces() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                board.putIfAbsent(position,
                        new Piece(PieceProperty.none(), position));
            }
        }
    }

    private static void setupUpTeamPieces() {
        setupUpTeamSoldier();
        setupUpTeamChariots();
        setupUpTeamCannons();
        setupUpTeamHorses();
        setupUpTeamGuards();
        setupUpTeamElephants();
        setupUpTeamGeneral();
    }

    private static void setupDownTeamPieces() {
        setupDownTeamSoldier();
        setupDownTeamChariots();
        setupDownTeamCannons();
        setupDownTeamHorses();
        setupDownTeamGuards();
        setupDownTeamElephants();
        setupDownTeamGeneral();
    }

    private static void setupUpTeamSoldier() {
        upTeamPiecePositionFactory.upTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.RED_SOLDIER, Team.RED), position)));
    }

    private static void setupDownTeamSoldier() {
        downTeamPositionFactory.downTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GREEN_SOLDIER, Team.GREEN), position)));
    }

    private static void setupUpTeamGuards() {
        upTeamPiecePositionFactory.upTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GUARD, Team.RED), position)));
    }

    private static void setupDownTeamGuards() {
        downTeamPositionFactory.downTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GUARD, Team.GREEN), position)));
    }

    private static void setupUpTeamChariots() {
        upTeamPiecePositionFactory.upTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CHARIOT, Team.RED), position)));
    }

    private static void setupDownTeamChariots() {
        downTeamPositionFactory.downTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CHARIOT, Team.GREEN), position)));
    }

    private static void setupUpTeamCannons() {
        upTeamPiecePositionFactory.upTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CANNON, Team.RED), position)));
    }

    private static void setupDownTeamCannons() {
        downTeamPositionFactory.downTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN), position)));
    }

    private static void setupUpTeamHorses() {
        upTeamPiecePositionFactory.upTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.HORSE, Team.RED), position)));
    }

    private static void setupDownTeamHorses() {
        downTeamPositionFactory.downTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), position)));
    }

    private static void setupUpTeamElephants() {
        upTeamPiecePositionFactory.upTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.ELEPHANT, Team.RED), position)));
    }

    private static void setupDownTeamElephants() {
        downTeamPositionFactory.downTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.ELEPHANT, Team.GREEN), position)));
    }

    private static void setupUpTeamGeneral() {
        Position position = upTeamPiecePositionFactory.upTeamGeneralPosition();
        board.putIfAbsent(position,
                new Piece(PieceProperty.of(PieceType.GENERAL, Team.RED), position));
    }

    private static void setupDownTeamGeneral() {
        Position position = downTeamPositionFactory.downTeamGeneralPosition();
        board.putIfAbsent(position,
                new Piece(PieceProperty.of(PieceType.GENERAL, Team.GREEN), position));
    }
}
