package janggi.model.initializer;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.diagonalMove.Sang;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.List;
import java.util.Map;

public class OutsideTableSetting extends BoardInitializerWithConst {

    private static final List<Position> MA_CHO = List.of(
            new Position(Row.HAN_BACK, Column.THREE),
            new Position(Row.HAN_BACK, Column.SEVEN)
    );

    private static final List<Position> MA_HAN = List.of(
            new Position(Row.ONE, Column.THREE),
            new Position(Row.ONE, Column.SEVEN)
    );

    private static final List<Position> SANG_CHO = List.of(
            new Position(Row.HAN_BACK, Column.TWO),
            new Position(Row.HAN_BACK, Column.EIGHT)
    );

    private static final List<Position> SANG_HAN = List.of(
            new Position(Row.ONE, Column.TWO),
            new Position(Row.ONE, Column.EIGHT)
    );

    @Override
    protected Map<Position, AbstractGimul> initMa() {
        return initGimul(MA_CHO, new Ma(Team.CHO), MA_HAN, new Ma(Team.HAN));
    }

    @Override
    protected Map<Position, AbstractGimul> initSang() {
        return initGimul(SANG_CHO, new Sang(Team.CHO), SANG_HAN, new Sang(Team.HAN));
    }
}
