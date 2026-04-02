package factory;

import domain.Board;
import domain.Piece;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardFactory {

    private final UpTeamPiecePositionFactory upTeamPiecePositionFactory = new UpTeamPiecePositionFactory();
    private final DownTeamPositionFactory downTeamPositionFactory = new DownTeamPositionFactory();
    private final MoveStrategyFactory moveStrategyFactory = new MoveStrategyFactory();

    private final Map<Position, Piece> board = new HashMap<>();

    public Board initialBoard() {
        setupUpTeamPieces();
        setupDownTeamPieces();
        setupEmptySpaces();

        return Board.of(Map.copyOf(board));
    }

    private void setupEmptySpaces() {
        PieceProperty nonPiece = new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                board.putIfAbsent(position,
                        Piece.of(nonPiece, moveStrategyFactory.createMoveStrategy(nonPiece)));
            }
        }
    }

    private void setupUpTeamPieces() {
        setupUpTeamSoldier();
        setupUpTeamChariots();
        setupUpTeamCannons();
        setupUpTeamHorses();
        setupUpTeamGuards();
        setupUpTeamElephants();
        setupUpTeamGeneral();
    }

    private void setupDownTeamPieces() {
        setupDownTeamSoldier();
        setupDownTeamChariots();
        setupDownTeamCannons();
        setupDownTeamHorses();
        setupDownTeamGuards();
        setupDownTeamElephants();
        setupDownTeamGeneral();
    }

    private void setupUpTeamSoldier() {
        PieceProperty redSoldier = new PieceProperty(PieceType.SOLDIER, Team.RED);
        upTeamPiecePositionFactory.upTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(redSoldier, moveStrategyFactory.createMoveStrategy(redSoldier))));
    }

    private void setupDownTeamSoldier() {
        PieceProperty greenSoldier = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
        downTeamPositionFactory.downTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(greenSoldier, moveStrategyFactory.createMoveStrategy(greenSoldier))));
    }

    private void setupUpTeamGuards() {
        PieceProperty redGuard = new PieceProperty(PieceType.GUARD, Team.RED);
        upTeamPiecePositionFactory.upTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(redGuard, moveStrategyFactory.createMoveStrategy(redGuard))));
    }

    private void setupDownTeamGuards() {
        PieceProperty greenGuard = new PieceProperty(PieceType.GUARD, Team.GREEN);
        downTeamPositionFactory.downTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(greenGuard, moveStrategyFactory.createMoveStrategy(greenGuard))));
    }

    private void setupUpTeamChariots() {
        PieceProperty redChariot = new PieceProperty(PieceType.CHARIOT, Team.RED);
        upTeamPiecePositionFactory.upTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(redChariot, moveStrategyFactory.createMoveStrategy(redChariot))));
    }

    private void setupDownTeamChariots() {
        PieceProperty greenChariot = new PieceProperty(PieceType.CHARIOT, Team.GREEN);
        downTeamPositionFactory.downTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(greenChariot, moveStrategyFactory.createMoveStrategy(greenChariot))));
    }

    private void setupUpTeamCannons() {
        PieceProperty redCannon = new PieceProperty(PieceType.CANNON, Team.RED);
        upTeamPiecePositionFactory.upTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(redCannon, moveStrategyFactory.createMoveStrategy(redCannon))));
    }

    private void setupDownTeamCannons() {
        PieceProperty greenCannon = new PieceProperty(PieceType.CANNON, Team.GREEN);
        downTeamPositionFactory.downTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(greenCannon, moveStrategyFactory.createMoveStrategy(greenCannon))));
    }

    private void setupUpTeamHorses() {
        PieceProperty redHorse = new PieceProperty(PieceType.HORSE, Team.RED);
        upTeamPiecePositionFactory.upTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(redHorse, moveStrategyFactory.createMoveStrategy(redHorse))));
    }

    private void setupDownTeamHorses() {
        PieceProperty greenHorse = new PieceProperty(PieceType.HORSE, Team.GREEN);
        downTeamPositionFactory.downTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(greenHorse, moveStrategyFactory.createMoveStrategy(greenHorse))));
    }

    private void setupUpTeamElephants() {
        PieceProperty redElephant = new PieceProperty(PieceType.ELEPHANT, Team.RED);
        upTeamPiecePositionFactory.upTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(redElephant, moveStrategyFactory.createMoveStrategy(redElephant))));
    }

    private void setupDownTeamElephants() {
        PieceProperty greenElephant = new PieceProperty(PieceType.ELEPHANT, Team.GREEN);
        downTeamPositionFactory.downTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(greenElephant, moveStrategyFactory.createMoveStrategy(greenElephant))));
    }

    private void setupUpTeamGeneral() {
        PieceProperty redGeneral = new PieceProperty(PieceType.GENERAL, Team.RED);
        Position position = upTeamPiecePositionFactory.upTeamGeneralPosition();
        board.putIfAbsent(position,
                Piece.of(redGeneral, moveStrategyFactory.createMoveStrategy(redGeneral)));
    }

    private void setupDownTeamGeneral() {
        PieceProperty greenGeneral = new PieceProperty(PieceType.GENERAL, Team.GREEN);
        Position position = downTeamPositionFactory.downTeamGeneralPosition();
        board.putIfAbsent(position,
                Piece.of(greenGeneral, moveStrategyFactory.createMoveStrategy(greenGeneral)));
    }
}
