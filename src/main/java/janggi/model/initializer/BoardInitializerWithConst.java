package janggi.model.initializer;

import janggi.model.Team;
import janggi.model.gimul.Byeong;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.palace.Jang;
import janggi.model.gimul.linearMove.Pho;
import janggi.model.gimul.palace.Sa;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BoardInitializerWithConst extends BoardInitializer {

    private static final List<Position> CHA_CHO = List.of(
            new Position(Row.ZERO, Column.ONE),
            new Position(Row.ZERO, Column.NINE)
    );

    private static final List<Position> CHA_HAN = List.of(
            new Position(Row.ONE, Column.ONE),
            new Position(Row.ONE, Column.NINE)
    );

    private static final Position JANG_CHO = new Position(Row.NINE, Column.FIVE);
    private static final Position JANG_HAN = new Position(Row.TWO, Column.FIVE);


    private static final List<Position> SA_CHO = List.of(
            new Position(Row.ZERO, Column.FOUR),
            new Position(Row.ZERO, Column.SIX)
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
        Map<Position, AbstractGimul> board = new HashMap<>();

        Cha cho = new Cha(Team.CHO);
        Cha han = new Cha(Team.HAN);

        for (Position position : CHA_CHO) {
            board.put(position, cho);
        }

        for (Position position : CHA_HAN) {
            board.put(position, han);
        }

        return board;
    }

    @Override
    protected Map<Position, AbstractGimul> initJang() {
        Map<Position, AbstractGimul> board = new HashMap<Position, AbstractGimul>();

        Jang cho = new Jang(Team.CHO);
        Jang han = new Jang(Team.HAN);

        board.put(JANG_CHO, cho);
        board.put(JANG_HAN, han);

        return board;
    }

    @Override
    protected Map<Position, AbstractGimul> initSa() {
        Map<Position, AbstractGimul> board = new HashMap<Position, AbstractGimul>();

        Sa cho = new Sa(Team.CHO);
        Sa han = new Sa(Team.HAN);

        for (Position position : SA_CHO) {
            board.put(position, cho);
        }

        for (Position position : SA_HAN) {
            board.put(position, han);
        }

        return board;
    }

    @Override
    protected Map<Position, AbstractGimul> initByeong() {
        Map<Position, AbstractGimul> board = new HashMap<Position, AbstractGimul>();

        Byeong cho = new Byeong(Team.CHO);
        Byeong han = new Byeong(Team.HAN);

        for (Position position : BYEONG_CHO) {
            board.put(position, cho);
        }

        for (Position position : BYEONG_HAN) {
            board.put(position, han);
        }

        return board;
    }


    @Override
    protected Map<Position, AbstractGimul> initPho() {
        Map<Position, AbstractGimul> board = new HashMap<Position, AbstractGimul>();

        Pho cho = new Pho(Team.CHO);
        Pho han = new Pho(Team.HAN);

        for (Position position : PHO_CHO) {
            board.put(position, cho);
        }

        for (Position position : PHO_HAN) {
            board.put(position, han);
        }

        return board;
    }


}
