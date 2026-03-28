package factory;

import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import domain.Piece;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardFactory {

    private final UpTeamPiecePositionFactory upTeamPiecePositionFactory = new UpTeamPiecePositionFactory();
    private final DownTeamPositionFactory downTeamPositionFactory = new DownTeamPositionFactory();

    private final Map<Position, Piece> board = new HashMap<>();

    public Map<Position, Piece> initialBoard() {
        setupUpTeamPieces();
        setupDownTeamPieces();
        setupEmptySpaces();

        return new HashMap<>(board);
    }

    private void setupEmptySpaces() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                board.putIfAbsent(position,
                        new Piece(PieceProperty.none(), position));
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
        upTeamPiecePositionFactory.upTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.RED_SOLDIER, Team.RED), position)));
    }

    private void setupDownTeamSoldier() {
        downTeamPositionFactory.downTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GREEN_SOLDIER, Team.GREEN), position)));
    }

    private void setupUpTeamGuards() {
        upTeamPiecePositionFactory.upTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GUARD, Team.RED), position)));
    }

    private void setupDownTeamGuards() {
        downTeamPositionFactory.downTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GUARD, Team.GREEN), position)));
    }

    private void setupUpTeamChariots() {
        upTeamPiecePositionFactory.upTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CHARIOT, Team.RED), position)));
    }

    private void setupDownTeamChariots() {
        downTeamPositionFactory.downTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CHARIOT, Team.GREEN), position)));
    }

    private void setupUpTeamCannons() {
        upTeamPiecePositionFactory.upTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CANNON, Team.RED), position)));
    }

    private void setupDownTeamCannons() {
        downTeamPositionFactory.downTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN), position)));
    }

    private void setupUpTeamHorses() {
        upTeamPiecePositionFactory.upTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.HORSE, Team.RED), position)));
    }

    private void setupDownTeamHorses() {
        downTeamPositionFactory.downTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), position)));
    }

    private void setupUpTeamElephants() {
        upTeamPiecePositionFactory.upTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.ELEPHANT, Team.RED), position)));
    }

    private void setupDownTeamElephants() {
        downTeamPositionFactory.downTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.ELEPHANT, Team.GREEN), position)));
    }

    private void setupUpTeamGeneral() {
        Position position = upTeamPiecePositionFactory.upTeamGeneralPosition();
        board.putIfAbsent(position,
                new Piece(PieceProperty.of(PieceType.GENERAL, Team.RED), position));
    }

    private void setupDownTeamGeneral() {
        Position position = downTeamPositionFactory.downTeamGeneralPosition();
        board.putIfAbsent(position,
                new Piece(PieceProperty.of(PieceType.GENERAL, Team.GREEN), position));
    }
}
