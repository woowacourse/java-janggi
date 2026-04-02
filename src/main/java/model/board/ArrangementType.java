package model.board;

import model.board.strategy.InnerElephant;
import model.board.strategy.LeftElephant;
import model.board.strategy.OuterElephant;
import model.board.strategy.RightElephant;

public enum ArrangementType {
    INNER("1", new InnerElephant()),
    OUTER("2", new OuterElephant()),
    RIGHT("3", new RightElephant()),
    LEFT("4", new LeftElephant());

    private final String number;
    private final HorseElephantStrategy strategy;


    ArrangementType(String number, HorseElephantStrategy strategy) {
        this.number = number;
        this.strategy = strategy;
    }

    public static ArrangementType from(String number) {
        for (ArrangementType type : values()) {
            if (type.number.equals(number)) {
                return type;
            }
        }
        throw new IllegalArgumentException("[ERROR] 진영 선택은 1~4만 가능합니다.");
    }

    public HorseElephantStrategy strategy() {
        return strategy;
    }
}
