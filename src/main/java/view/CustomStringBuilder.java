package view;

public class CustomStringBuilder {

    private final StringBuilder stringBuilder = new StringBuilder();

    public void appendHeader(final char content) {
        stringBuilder.append(content).append("  ");
    }

    public void append(final String content) {
        stringBuilder.append(content).append("  ");
    }

    public void appendBlankCell() {
        stringBuilder.append("   ");
    }

    public void lineSplit() {
        stringBuilder.append(System.lineSeparator());
    }

    public void print() {
        System.out.println(stringBuilder);
    }

}
