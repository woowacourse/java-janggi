package janggi.model.initializer;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.byeong.Byeong;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.linearMove.Pho;
import janggi.model.gimul.palace.Jang;
import janggi.model.gimul.palace.Sa;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BoardInitializerWithConst extends BoardInitializer {

    private static final List<Position> CHA_CHO = List.of(
            new Position(Row.HAN_BACK, Column.ONE),
            new Position(Row.HAN_BACK, Column.NINE)
    );

    private static final List<Position> CHA_HAN = List.of(
            new Position(Row.ONE, Column.ONE),
            new Position(Row.ONE, Column.NINE)
    );

    private static final Position JANG_CHO = new Position(Row.NINE, Column.FIVE);
    private static final Position JANG_HAN = new Position(Row.TWO, Column.FIVE);

    private static final List<Position> SA_CHO = List.of(
            new Position(Row.HAN_BACK, Column.FOUR),
            new Position(Row.HAN_BACK, Column.SIX)
    );

    private static final List<Position> SA_HAN = List.of(
            new Position(Row.ONE, Column.FOUR),
            new Position(Row.ONE, Column.SIX)
    );

    private static final List<Position> BYEONG_CHO = List.of(
            new Position(Row.SEVEN, Column.ONE),
            new Position(Row.SEVEN, Column.THREE),
            new Position(Row.SEVEN, Column.FIVE),
            new Position(Row.SEVEN, Column.SEVEN),
            new Position(Row.SEVEN, Column.NINE)
    );

    private static final List<Position> BYEONG_HAN = List.of(
            new Position(Row.FOUR, Column.ONE),
            new Position(Row.FOUR, Column.THREE),
            new Position(Row.FOUR, Column.FIVE),
            new Position(Row.FOUR, Column.SEVEN),
            new Position(Row.FOUR, Column.NINE)
    );

    private static final List<Position> PHO_CHO = List.of(
            new Position(Row.EIGHT, Column.TWO),
            new Position(Row.EIGHT, Column.EIGHT)
    );

    private static final List<Position> PHO_HAN = List.of(
            new Position(Row.THREE, Column.TWO),
            new Position(Row.THREE, Column.EIGHT)
    );

    @Override
    protected Map<Position, AbstractGimul> initCha() {
        return initGimul(CHA_CHO, new Cha(Team.CHO), CHA_HAN, new Cha(Team.HAN));
    }

    @Override
    protected Map<Position, AbstractGimul> initSa() {
        return initGimul(SA_CHO, new Sa(Team.CHO), SA_HAN, new Sa(Team.HAN));
    }

    @Override
    protected Map<Position, AbstractGimul> initJang() {
        Map<Position, AbstractGimul> board = new HashMap<>();
        board.put(JANG_CHO, new Jang(Team.CHO));
        board.put(JANG_HAN, new Jang(Team.HAN));
        return board;
    }

    @Override
    protected Map<Position, AbstractGimul> initByeong() {
        return initGimul(BYEONG_CHO, new Byeong(Team.CHO), BYEONG_HAN, new Byeong(Team.HAN));
    }

    @Override
    protected Map<Position, AbstractGimul> initPho() {
        return initGimul(PHO_CHO, new Pho(Team.CHO), PHO_HAN, new Pho(Team.HAN));
    }

    protected Map<Position, AbstractGimul> initGimul(
            List<Position> choPositions, AbstractGimul cho,
            List<Position> hanPositions, AbstractGimul han) {
        Map<Position, AbstractGimul> board = new HashMap<>();
        choPositions.forEach(position -> board.put(position, cho));
        hanPositions.forEach(position -> board.put(position, han));
        return board;
    }
}
