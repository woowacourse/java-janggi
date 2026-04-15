package domain.board;

import domain.place.moveStrategy.CannonMoveStrategy;
import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.ChoSoldierMoveStrategy;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.GuardMoveStrategy;
import domain.place.moveStrategy.HanSoldierMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.piece.Cannon;
import domain.place.piece.Chariot;
import domain.place.piece.General;
import domain.place.piece.Guard;
import domain.place.piece.Piece;
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
        Map<Position, Piece> board = new HashMap<>();
        Palace palace = Palace.getInstance();

        setUpFormationHorseElephant(board, Side.CHO, cho);
        setUpFormationHorseElephant(board, Side.HAN, han);

        setUpFormation(board, Side.CHO);
        setUpFormation(board, Side.HAN);

        return new Board(board, palace);
    }

    private static void setUpFormationHorseElephant(Map<Position, Piece> board, Side side,
                                                    HorseElephantFormation horseElephantFormation) {
        int startLine = side.getStartLine();
        List<Function<Side, Piece>> formationMethod = horseElephantFormation.getFormationMethod();

        for (int i = 0; i < HORSE_ELEPHANT_COLS.size(); i++) {
            Position position = new Position(startLine, HORSE_ELEPHANT_COLS.get(i));
            Piece piece = formationMethod.get(i).apply(side);
            board.put(position, piece);
        }
    }

    private static void setUpFormation(Map<Position, Piece> board, Side side) {
        int startLine = side.getStartLine();
        int direction = side.getDirection();

        firstSetUpFormation(board, side, startLine);

        startLine += direction;
        board.put(new Position(startLine, GENERAL_COLS), new General(side, new GeneralMoveStrategy()));

        startLine += direction;
        cannonSetUpFormation(board, side, startLine);

        startLine += direction;
        soldierSetUpFormation(board, side, startLine);
    }

    private static void firstSetUpFormation(Map<Position, Piece> board, Side side, int startLine) {
        CHARIOT_COLS.forEach(c -> board.put(new Position(startLine, c), new Chariot(side, new ChariotMoveStrategy())));
        GUARD_COLS.forEach(c -> board.put(new Position(startLine, c), new Guard(side, new GuardMoveStrategy())));
    }

    private static void cannonSetUpFormation(Map<Position, Piece> board, Side side, int startLine) {
        CANNON_COLS.forEach(c -> board.put(new Position(startLine, c), new Cannon(side, new CannonMoveStrategy())));
    }

    private static void soldierSetUpFormation(Map<Position, Piece> board, Side side, int startLine) {
        MoveStrategy soldierMoveStrategy = createSoldierMoveStrategy(side);

        SOLDIER_COLS.forEach(c -> board.put(new Position(startLine, c), new Soldier(side, soldierMoveStrategy)));
    }

    private static MoveStrategy createSoldierMoveStrategy(Side side) {
        if (side == Side.CHO) {
            return new ChoSoldierMoveStrategy();
        }
        return new HanSoldierMoveStrategy();
    }
}
