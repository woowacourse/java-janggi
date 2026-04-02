package view;

public class ErrorOutputView {

    private static final String ERROR_PREFIX = "[ERROR]";

    public void printErrorMessage(Exception exception) {
        System.out.println(ERROR_PREFIX + exception.getMessage());
        System.out.println();
    }
}
