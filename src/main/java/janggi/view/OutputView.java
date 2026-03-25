package janggi.view;

public class OutputView {

    public void printLine(String message) {
        System.out.println(message);
    }

    private void printNewLine() {
        System.out.println();
    }

    public void printPlayerNameNotice(String sideName) {
        printLine(String.format(Message.PLAYER_NAME_NOTICE, sideName));
    }
}
