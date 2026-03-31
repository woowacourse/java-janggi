package model.formation;

import java.util.stream.Stream;

public enum JanggiFormation {
    SANG_MA_SANG_MA(1, "상마상마"),
    MA_SANG_MA_SANG(2, "마상마상"),
    MA_SANG_SANG_MA(3, "마상상마"),
    SANG_MA_MA_SANG(4, "상마마상");

    private final int order;
    private final String formation;

    JanggiFormation(int order, String formation) {
        this.order = order;
        this.formation = formation;
    }

    public static JanggiFormation from(int order) {
        return Stream.of(values())
                .filter(formation -> formation.order == order)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상차림 번호입니다."));
    }

    public int getOrder() {
        return order;
    }

    public String getFormation() {
        return formation;
    }
}
