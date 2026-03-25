package domain.vo;

public enum Row {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");


    private final String value;

    Row(String value) {
        this.value = value;
    }

    public Row reverse() {
        return Row.values()[9 - this.ordinal()];
    }

    public String getValue() {
        return value;
    }
}
