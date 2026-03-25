package view;

public class OutputView {
    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }
}
