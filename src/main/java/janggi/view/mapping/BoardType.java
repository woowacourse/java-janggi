package janggi.view.mapping;

import janggi.model.board.Board;
import janggi.model.initializer.InsideTableSetting;
import janggi.model.initializer.LeftSidedTableSetting;
import janggi.model.initializer.OutsideTableSetting;
import janggi.model.initializer.RightSidedTableSetting;
import janggi.model.palace.Palaces;
import java.util.Arrays;
import java.util.function.Function;

public enum BoardType {
    FIRST(1, palaces -> new LeftSidedTableSetting(palaces).init()),
    SECOND(2, palaces -> new RightSidedTableSetting(palaces).init()),
    THIRD(3, palaces -> new InsideTableSetting(palaces).init()),
    FOURTH(4, palaces -> new OutsideTableSetting(palaces).init());

    private final int value;
    private final Function<Palaces, Board> mapper;

    BoardType(int value, Function<Palaces, Board> mapper) {
        if (value < 1 || value > 4) {
            throw new IllegalArgumentException("1에서 4 사이의 자연수가 아닙니다.");
        }

        this.value = value;
        this.mapper = mapper;
    }

    public static BoardType of(int value) {
        return Arrays.stream(values())
                .filter(boarType -> boarType.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 값입니다."));
    }

    public Board getBoard(Palaces palaces) {
        return mapper.apply(palaces);
    }
}
