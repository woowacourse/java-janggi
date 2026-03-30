package view;

public class OutputView {

    public void askSangSetup(String sideName) {
        System.out.println(sideName + "나라의 상차림을 선택해주세요.");
        System.out.println(SangSetupType.convertDisplayFormat());
    }

    public void printBoard(String board) {
        System.out.println(board);
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }
}
