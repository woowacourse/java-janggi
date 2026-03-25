package janggi.view;

public class OutputView {

    public void printLine(String message) {
        System.out.println(message);
    }

    private void printNewLine() {
        System.out.println();
    }

    public void printChoPlayerNameNotice() {
        printLine(Message.CHO_PLAYER_NAME_NOTICE);
    }

    public void printHanPlayerNameNotice() {
        printLine(Message.HAN_PLAYER_NAME_NOTICE);
    }
}
