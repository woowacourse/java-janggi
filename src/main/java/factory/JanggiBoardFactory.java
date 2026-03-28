package factory;

import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.DownToUpSoldierMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.GuardMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.NoneMoveableStrategy;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardFactory {

    private final UpTeamPiecePositionFactory upTeamPiecePositionFactory = new UpTeamPiecePositionFactory();
    private final DownTeamPositionFactory downTeamPositionFactory = new DownTeamPositionFactory();

    private Map<Position, Piece> board = new HashMap<>();

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
                        new Piece(PieceProperty.of(PieceType.EMPTY_VALUE, Team.NONE),
                                NoneMoveableStrategy.of(position)));
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
                        new Piece(PieceProperty.of(PieceType.SOLDIER, Team.RED),
                                DownToUpSoldierMoveStrategy.of(position))));
    }

    private void setupDownTeamSoldier() {
        downTeamPositionFactory.downTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                                DownToUpSoldierMoveStrategy.of(position))));
    }

    private void setupUpTeamGuards() {
        upTeamPiecePositionFactory.upTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GUARD, Team.RED), GuardMoveStrategy.of(position))));
    }

    private void setupDownTeamGuards() {
        downTeamPositionFactory.downTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.GUARD, Team.GREEN), GeneralMoveStrategy.of(position))));
    }

    private void setupUpTeamChariots() {
        upTeamPiecePositionFactory.upTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CHARIOT, Team.RED), ChariotMoveStrategy.of(position))));
    }

    private void setupDownTeamChariots() {
        downTeamPositionFactory.downTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CHARIOT, Team.GREEN), ChariotMoveStrategy.of(position))));
    }

    private void setupUpTeamCannons() {
        upTeamPiecePositionFactory.upTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CANNON, Team.RED), CannonMoveStrategy.of(position))));
    }

    private void setupDownTeamCannons() {
        downTeamPositionFactory.downTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.CANNON, Team.GREEN), CannonMoveStrategy.of(position))));
    }

    private void setupUpTeamHorses() {
        upTeamPiecePositionFactory.upTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.HORSE, Team.RED), HorseMoveStrategy.of(position))));
    }

    private void setupDownTeamHorses() {
        downTeamPositionFactory.downTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN), HorseMoveStrategy.of(position))));
    }

    private void setupUpTeamElephants() {
        upTeamPiecePositionFactory.upTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.ELEPHANT, Team.RED), ElephantMoveStrategy.of(position))));
    }

    private void setupDownTeamElephants() {
        downTeamPositionFactory.downTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Piece(PieceProperty.of(PieceType.ELEPHANT, Team.GREEN),
                                ElephantMoveStrategy.of(position))));
    }

    private void setupUpTeamGeneral() {
        Position position = upTeamPiecePositionFactory.upTeamGeneralPosition();
        board.putIfAbsent(position,
                new Piece(PieceProperty.of(PieceType.GENERAL, Team.RED), GeneralMoveStrategy.of(position)));
    }

    private void setupDownTeamGeneral() {
        Position position = downTeamPositionFactory.downTeamGeneralPosition();
        board.putIfAbsent(position,
                new Piece(PieceProperty.of(PieceType.GENERAL, Team.GREEN), GeneralMoveStrategy.of(position)));
    }
}
