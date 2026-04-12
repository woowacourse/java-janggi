package janggi.model.initializer;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Byeong;
import janggi.model.piece.Piece;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.palace.Sa;
import janggi.model.piece.straightMove.Cha;
import janggi.model.piece.straightMove.Pho;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BoardInitializerWithConst implements BoardInitializer {

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

    public Board init() {
        Map<Position, Piece> board = new HashMap<>();

        board.putAll(initCha());
        board.putAll(initMa());
        board.putAll(initSang());
        board.putAll(initJang());
        board.putAll(initSa());
        board.putAll(initByeong());
        board.putAll(initPho());

        return PlayingBoard.of(board);
    }

    protected abstract Map<Position, Piece> initMa();

    protected abstract Map<Position, Piece> initSang();


    protected Map<Position, Piece> initCha() {
        Map<Position, Piece> board = new HashMap<>();

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

    protected Map<Position, Piece> initJang() {
        Map<Position, Piece> board = new HashMap<>();

        Jang cho = new Jang(Team.CHO);
        Jang han = new Jang(Team.HAN);

        board.put(JANG_CHO, cho);
        board.put(JANG_HAN, han);

        return board;
    }

    protected Map<Position, Piece> initSa() {
        Map<Position, Piece> board = new HashMap<>();

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

    protected Map<Position, Piece> initByeong() {
        Map<Position, Piece> board = new HashMap<>();

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


    protected Map<Position, Piece> initPho() {
        Map<Position, Piece> board = new HashMap<>();

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
