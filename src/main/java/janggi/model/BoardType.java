package janggi.model;

import janggi.model.initializer.InsideTableSetting;
import janggi.model.initializer.LeftSidedTableSetting;
import janggi.model.initializer.OutsideTableSetting;
import janggi.model.initializer.RightSidedTableSetting;
import java.util.Arrays;

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
