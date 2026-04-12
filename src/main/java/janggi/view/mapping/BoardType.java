package janggi.view.mapping;

import janggi.model.board.Board;
import janggi.model.initializer.InsideTableSetting;
import janggi.model.initializer.LeftSidedTableSetting;
import janggi.model.initializer.OutsideTableSetting;
import janggi.model.initializer.RightSidedTableSetting;
import java.util.Arrays;
import java.util.function.Supplier;

public enum BoardType {
    FIRST(1, () -> new LeftSidedTableSetting().init()),
    SECOND(2, () -> new RightSidedTableSetting().init()),
    THIRD(3, () -> new InsideTableSetting().init()),
    FOURTH(4, () -> new OutsideTableSetting().init());

    private final int value;
    private final Supplier<Board> mapper;

    BoardType(int value, Supplier<Board> supplier) {
        if (value < 1 || value > 4) {
            throw new IllegalArgumentException("1에서 4 사이의 자연수가 아닙니다.");
        }

        this.value = value;
        this.mapper = supplier;
    }

    public static BoardType of(int value) {
        return Arrays.stream(values())
                .filter(boarType -> boarType.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 값입니다."));
    }

    public Board getBoard() {
        return mapper.get();
    }
}
