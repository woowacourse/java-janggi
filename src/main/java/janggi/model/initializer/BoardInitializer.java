package janggi.model.initializer;

import janggi.model.board.Board;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class BoardInitializer {

    public Board init() {
        Map<Position, AbstractGimul> board = new HashMap<>();
        initAllGimuls(board);
        return new Board(board);
    }

    private void initAllGimuls(Map<Position, AbstractGimul> board) {
        board.putAll(initCha());
        board.putAll(initMa());
        board.putAll(initSang());
        board.putAll(initJang());
        board.putAll(initSa());
        board.putAll(initByeong());
        board.putAll(initPho());
    }

    protected abstract Map<Position, AbstractGimul> initCha();

    protected abstract Map<Position, AbstractGimul> initMa();

    protected abstract Map<Position, AbstractGimul> initSang();

    protected abstract Map<Position, AbstractGimul> initJang();

    protected abstract Map<Position, AbstractGimul> initSa();

    protected abstract Map<Position, AbstractGimul> initByeong();

    protected abstract Map<Position, AbstractGimul> initPho();

    public enum BoardType {
        LEFT(1) {
            public Board init() {
                return new LeftSidedTableSetting().init();
            }
        },
        RIGHT(2) {
            public Board init() {
                return new RightSidedTableSetting().init();
            }
        },
        INSIDE(3) {
            public Board init() {
                return new InsideTableSetting().init();
            }
        },
        OUTSIDE(4) {
            public Board init() {
                return new OutsideTableSetting().init();
            }
        };

        private final int number;

        BoardType(int number) {
            this.number = number;
        }

        public abstract Board init();

        public static BoardType of(int number) {
            return Arrays.stream(values())
                    .filter(type -> type.number == number)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("유효한 유형 번호를 입력하세요."));
        }
    }
}
