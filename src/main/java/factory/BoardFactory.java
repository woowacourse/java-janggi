package factory;

import static domain.board.BoardPolicy.MAX_COLUMN;
import static domain.board.BoardPolicy.MAX_ROW;
import static domain.board.BoardPolicy.MIN_COLUMN;
import static domain.board.BoardPolicy.MIN_ROW;

import domain.board.Board;
import domain.board.HorseElephantFormation;
import domain.place.Empty;
import domain.place.Place;
import domain.place.moveStrategy.JumpMoveStrategy;
import domain.place.moveStrategy.OneStepMoveStrategy;
import domain.place.moveStrategy.StraightMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceJumpMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceOneStepMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceStraightMoveStrategy;
import domain.place.piece.Cannon;
import domain.place.piece.Chariot;
import domain.place.piece.General;
import domain.place.piece.Guard;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BoardFactory {

    private static final List<Integer> HORSE_ELEPHANT_COLS = List.of(2, 3, 7, 8);
    private static final List<Integer> CHARIOT_COLS = List.of(1, 9);
    private static final List<Integer> GUARD_COLS = List.of(4, 6);
    private static final int GENERAL_COLS = 5;
    private static final List<Integer> CANNON_COLS = List.of(2, 8);
    private static final List<Integer> SOLDIER_COLS = List.of(1, 3, 5, 7, 9);

    public static Board create(HorseElephantFormation cho, HorseElephantFormation han) {
        Map<Position, Place> board = setUpEmpty();

        setUpFormationHorseElephant(board, Side.CHO, cho);
        setUpFormationHorseElephant(board, Side.HAN, han);

        setUpFormation(board, Side.CHO);
        setUpFormation(board, Side.HAN);

        return new Board(board);
    }

    public static Map<Position, Place> setUpEmpty() {
        Map<Position, Place> board = new HashMap<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), new Empty());
            }
        }
        return board;
    }

    private static void setUpFormationHorseElephant(Map<Position, Place> board, Side side,
                                                    HorseElephantFormation horseElephantFormation) {
        int startLine = side.getStartLine();
        List<Function<Side, Place>> formationMethod = horseElephantFormation.getFormationMethod();

        for (int i = 0; i < HORSE_ELEPHANT_COLS.size(); i++) {
            Position position = new Position(startLine, HORSE_ELEPHANT_COLS.get(i));
            Place place = formationMethod.get(i).apply(side);
            board.put(position, place);
        }
    }

    private static void setUpFormation(Map<Position, Place> board, Side side) {
        int startLine = side.getStartLine();
        int setupDirection = side.getSetupDirection();

        firstSetUpFormation(board, side, startLine);

        startLine += setupDirection;
        board.put(new Position(startLine, GENERAL_COLS),
                new General(side, new OneStepMoveStrategy(), new PalaceOneStepMoveStrategy()));

        startLine += setupDirection;
        cannonSetUpFormation(board, side, startLine);

        startLine += setupDirection;
        soldierSetUpFormation(board, side, startLine);
    }

    private static void firstSetUpFormation(Map<Position, Place> board, Side side, int startLine) {
        CHARIOT_COLS.forEach(c -> board.put(new Position(startLine, c),
                new Chariot(side, new StraightMoveStrategy(), new PalaceStraightMoveStrategy())));
        GUARD_COLS.forEach(c -> board.put(new Position(startLine, c),
                new Guard(side, new OneStepMoveStrategy(), new PalaceOneStepMoveStrategy())));
    }

    private static void cannonSetUpFormation(Map<Position, Place> board, Side side, int startLine) {
        CANNON_COLS.forEach(c -> board.put(new Position(startLine, c),
                new Cannon(side, new JumpMoveStrategy(), new PalaceJumpMoveStrategy())));
    }

    private static void soldierSetUpFormation(Map<Position, Place> board, Side side, int startLine) {
        MoveStrategy soldierMoveStrategy = new SoldierMoveStrategy(side);

        SOLDIER_COLS.forEach(c -> board.put(new Position(startLine, c),
                new Soldier(side, soldierMoveStrategy, new PalaceSoldierMoveStrategy(side))));
    }

}
