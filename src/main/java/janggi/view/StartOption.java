package janggi.view;

public enum StartOption {

    NEW("1"),
    LOAD("2"),
    QUIT("Q");

    private final String select;

    StartOption(final String select) {
        this.select = select;
    }

    public static StartOption from(final String select) {
        for (final StartOption option : StartOption.values()) {
            if (option.select.equalsIgnoreCase(select)) {
                return option;
            }
        }

        throw new IllegalArgumentException("올바른 번호를 선택하세요");
    }

    public boolean isNew() {
        return this == NEW;
    }

    public boolean isLoad() {
        return this == LOAD;
    }

    public boolean isQuit() {
        return this == QUIT;
    }
}
