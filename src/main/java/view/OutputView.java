package view;


public class OutputView {

    public void printJanggiStart() {
        System.out.println("장기 시작");
    }

    public void printCurrentPosition(String currentPosition) {
        System.out.println("  １２３４５６７８９");
        System.out.println(currentPosition);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
