package factory;

import domain.Piece;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.DownwardSoldierMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.GuardMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.NonMoveableStrategy;
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

        return Map.copyOf(board);
    }

    private void setupEmptySpaces() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE), new NonMoveableStrategy()));
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
                        Piece.of(new PieceProperty(PieceType.SOLDIER, Team.RED),
                                new DownwardSoldierMoveStrategy())));
    }

    private void setupDownTeamSoldier() {
        downTeamPositionFactory.downTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.SOLDIER, Team.GREEN),
                                new DownwardSoldierMoveStrategy())));
    }

    private void setupUpTeamGuards() {
        upTeamPiecePositionFactory.upTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.GUARD, Team.RED), new GuardMoveStrategy())));
    }

    private void setupDownTeamGuards() {
        downTeamPositionFactory.downTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.GUARD, Team.GREEN), new GeneralMoveStrategy())));
    }

    private void setupUpTeamChariots() {
        upTeamPiecePositionFactory.upTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.CHARIOT, Team.RED), new ChariotMoveStrategy())));
    }

    private void setupDownTeamChariots() {
        downTeamPositionFactory.downTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.CHARIOT, Team.GREEN), new ChariotMoveStrategy())));
    }

    private void setupUpTeamCannons() {
        upTeamPiecePositionFactory.upTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.CANNON, Team.RED), new CannonMoveStrategy())));
    }

    private void setupDownTeamCannons() {
        downTeamPositionFactory.downTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.CANNON, Team.GREEN), new CannonMoveStrategy())));
    }

    private void setupUpTeamHorses() {
        upTeamPiecePositionFactory.upTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.HORSE, Team.RED), new HorseMoveStrategy())));
    }

    private void setupDownTeamHorses() {
        downTeamPositionFactory.downTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.HORSE, Team.GREEN), new HorseMoveStrategy())));
    }

    private void setupUpTeamElephants() {
        upTeamPiecePositionFactory.upTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.ELEPHANT, Team.RED), new ElephantMoveStrategy())));
    }

    private void setupDownTeamElephants() {
        downTeamPositionFactory.downTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        Piece.of(new PieceProperty(PieceType.ELEPHANT, Team.GREEN), new ElephantMoveStrategy())));
    }

    private void setupUpTeamGeneral() {
        Position position = upTeamPiecePositionFactory.upTeamGeneralPosition();
        board.putIfAbsent(position,
                Piece.of(new PieceProperty(PieceType.GENERAL, Team.RED), new GeneralMoveStrategy()));
    }

    private void setupDownTeamGeneral() {
        Position position = downTeamPositionFactory.downTeamGeneralPosition();
        board.putIfAbsent(position,
                Piece.of(new PieceProperty(PieceType.GENERAL, Team.GREEN), new GeneralMoveStrategy()));
    }
}
