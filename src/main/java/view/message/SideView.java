package view.message;

import domain.state.Side;

public enum SideView {

    HAN(Side.HAN, "한나라", "\u001B[31m"),
    CHU(Side.CHU, "초나라", "\u001B[34m");

    private static final String RESET = "\u001B[0m";

    private final Side side;
    private final String name;
    private final String color;

    SideView(Side side, String name, String color) {
        this.side = side;
        this.name = name;
        this.color = color;
    }

    public static String from(Side side) {
        for (SideView sideMessage : SideView.values()) {
            if (side.equals(sideMessage.side)) {
                return sideMessage.color + sideMessage.name + RESET;
            }
        }

        throw new IllegalArgumentException("존재하지 않는 진영입니다.");
    }

    public static String getSideColor(Side side) {
        for (SideView sideMessage : SideView.values()) {
            if (side.equals(sideMessage.side)) {
                return sideMessage.color;
            }
        }

        throw new IllegalArgumentException("존재하지 않는 진영입니다.");
    }

    public static String getResetColor() {
        return RESET;
    }
}
