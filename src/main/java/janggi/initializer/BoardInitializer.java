package janggi.initializer;

import janggi.Board;
import janggi.gimul.Gimul;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public abstract class BoardInitializer {

    public Board init() {
        Map<Position, Gimul> board = new HashMap<Position, Gimul>();

        board.putAll(initCha());
        board.putAll(initMa());
        board.putAll(initSang());
        board.putAll(initJang());
        board.putAll(initSa());
        board.putAll(initByeong());
        board.putAll(initPho());

        return new Board(board);
    }

    protected abstract Map<Position, Gimul> initCha();

    protected abstract Map<Position, Gimul> initMa();

    protected abstract Map<Position, Gimul> initSang();

    protected abstract Map<Position, Gimul> initJang();

    protected abstract Map<Position, Gimul> initSa();

    protected abstract Map<Position, Gimul> initByeong();

    protected abstract Map<Position, Gimul> initPho();
}
