package factory;

import domain.strategy.NoneMoveableStrategy;
import domain.Position;
import domain.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.Soldier;
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

        return Map.copyOf(board);
    }

    private void setupEmptySpaces() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                board.putIfAbsent(position ,
                        new None(Team.NONE, new NoneMoveableStrategy(position)));
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
                        new Soldier(Team.RED, new NoneMoveableStrategy(position))));
    }

    private void setupDownTeamSoldier() {
        downTeamPositionFactory.downTeamSoldierPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Soldier(Team.GREEN, new NoneMoveableStrategy(position))));
    }

    private void setupUpTeamGuards() {
        upTeamPiecePositionFactory.upTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Guard(Team.RED, new NoneMoveableStrategy(position))));
    }

    private void setupDownTeamGuards() {
        downTeamPositionFactory.downTeamGuardPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Guard(Team.GREEN, new NoneMoveableStrategy(position))));
    }

    private void setupUpTeamChariots() {
        upTeamPiecePositionFactory.upTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Chariot(Team.RED, new NoneMoveableStrategy(position))));
    }

    private void setupDownTeamChariots() {
        downTeamPositionFactory.downTeamChariotPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Chariot(Team.GREEN, new NoneMoveableStrategy(position))));
    }

    private void setupUpTeamCannons() {
        upTeamPiecePositionFactory.upTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Cannon(Team.RED, new NoneMoveableStrategy(position))));
    }

    private void setupDownTeamCannons() {
        downTeamPositionFactory.downTeamCannonPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Cannon(Team.GREEN, new NoneMoveableStrategy(position))));
    }

    private void setupUpTeamHorses() {
        upTeamPiecePositionFactory.upTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Horse(Team.RED, new NoneMoveableStrategy(position))));
    }

    private void setupDownTeamHorses() {
        downTeamPositionFactory.downTeamHorsePositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Horse(Team.GREEN, new NoneMoveableStrategy(position))));
    }

    private void setupUpTeamElephants() {
        upTeamPiecePositionFactory.upTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Elephant(Team.RED, new NoneMoveableStrategy(position))));
    }

    private void setupDownTeamElephants() {
        downTeamPositionFactory.downTeamElephantPositions()
                .forEach(position -> board.putIfAbsent(position,
                        new Elephant(Team.GREEN, new NoneMoveableStrategy(position))));
    }

    private void setupUpTeamGeneral() {
        Position position = upTeamPiecePositionFactory.upTeamGeneralPosition();
        board.putIfAbsent(position, new General(Team.RED, new NoneMoveableStrategy(position)));
    }

    private void setupDownTeamGeneral() {
        Position position = downTeamPositionFactory.downTeamGeneralPosition();
        board.putIfAbsent(position, new General(Team.GREEN, new NoneMoveableStrategy(position)));
    }
}
