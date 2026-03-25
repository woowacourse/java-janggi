package view;

public class OutputViewFormatter {
    public String formatInvalidInput(String message) {
        return String.format("[ERROR] 올바르지 않은 입력입니다. %s%n", message);
    }

    public String formatCountry(String country) {
        return String.format("%n---%n%n차례 : %s", country);
    }
}
