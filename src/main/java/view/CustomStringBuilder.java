package view;

public class CustomStringBuilder {
    private final StringBuilder stringBuilder = new StringBuilder();

    public void appendHeader(char content) {
        stringBuilder.append(content).append("  ");
    }

    public void append(String content) {
        stringBuilder.append(content).append("  ");
    }


    public void appendBlankCell() {
        stringBuilder.append("   ");
    }

    public void appendLine(String content) {
        stringBuilder.append(content).append(System.lineSeparator());
    }

    public void lineSplit() {
        stringBuilder.append(System.lineSeparator());
    }

    public void print() {
        System.out.println(stringBuilder);
    }
}
