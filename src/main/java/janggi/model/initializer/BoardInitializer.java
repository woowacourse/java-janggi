package janggi.model.initializer;

import janggi.model.board.Board;
import janggi.model.gimul.AbstractGimul;
import janggi.model.board.position.Position;
import java.util.HashMap;
import java.util.Map;

public abstract class BoardInitializer {

    public Board init() {
        Map<Position, AbstractGimul> board = new HashMap<>();

        board.putAll(initCha());
        board.putAll(initMa());
        board.putAll(initSang());
        board.putAll(initJang());
        board.putAll(initSa());
        board.putAll(initByeong());
        board.putAll(initPho());

        return new Board(board);
    }

    protected abstract Map<Position, AbstractGimul> initCha();

    protected abstract Map<Position, AbstractGimul> initMa();

    protected abstract Map<Position, AbstractGimul> initSang();

    protected abstract Map<Position, AbstractGimul> initJang();

    protected abstract Map<Position, AbstractGimul> initSa();

    protected abstract Map<Position, AbstractGimul> initByeong();

    protected abstract Map<Position, AbstractGimul> initPho();
}
