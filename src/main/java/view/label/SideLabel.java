package view.label;

import domain.game.Side;
import java.util.Arrays;

public enum SideLabel {
    CHO(Side.CHO, "초"),
    HAN(Side.HAN, "한"),
    ;

    private final Side side;
    private final String label;

    SideLabel(Side side, String label) {
        this.side = side;
        this.label = label;
    }

    public static String getLabel(Side side) {
        return Arrays.stream(values())
                .filter(sideLabel -> sideLabel.hasSameSide(side))
                .map(sideLabel -> sideLabel.label)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(side + "의 라벨을 정의해야 합니다."));
    }

    private boolean hasSameSide(Side side) {
        return this.side == side;
    }
}
