package janggi.model.initializer;

import janggi.model.board.Board;
import janggi.model.gimul.Piece;
import janggi.model.board.position.Position;
import java.util.HashMap;
import java.util.Map;

public abstract class BoardInitializer {

    public Board init() {
        Map<Position, Piece> board = new HashMap<>();

        board.putAll(initCha());
        board.putAll(initMa());
        board.putAll(initSang());
        board.putAll(initJang());
        board.putAll(initSa());
        board.putAll(initByeong());
        board.putAll(initPho());

        return new Board(board);
    }

    protected abstract Map<Position, Piece> initCha();

    protected abstract Map<Position, Piece> initMa();

    protected abstract Map<Position, Piece> initSang();

    protected abstract Map<Position, Piece> initJang();

    protected abstract Map<Position, Piece> initSa();

    protected abstract Map<Position, Piece> initByeong();

    protected abstract Map<Position, Piece> initPho();
}
