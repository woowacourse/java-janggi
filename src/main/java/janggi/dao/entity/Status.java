package janggi.dao.entity;

import java.util.Arrays;

public enum Status {
    RUN(0), END(1);

    private final int symbol;

    Status(int symbol) {
        this.symbol = symbol;
    }

    public static Status from(int symbol) {
        return Arrays.stream(values())
                .filter(status -> status.symbol == symbol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(symbol + "에 해당하는 status가 없습니다."));
    }

    public int getSymbol() {
        return symbol;
    }
}
