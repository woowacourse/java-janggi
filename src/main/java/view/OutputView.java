package view;

public class OutputView {
    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printEndMessage() {
        System.out.println("게임을 종료합니다.");
    }
    public void printErrorMessage(String message){
        System.out.println(message);
    }
}
