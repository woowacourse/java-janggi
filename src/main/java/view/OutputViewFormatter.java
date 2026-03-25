package view;

public class OutputViewFormatter {
    public String printInvalidInput(String message) {
        return String.format("[ERROR] 올바르지 않은 입력입니다. %s%n");
    }
}
