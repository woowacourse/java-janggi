package view;

public class ConfigurationOutputView {

    public void printLocalGame() {
        System.err.println("-----------------------------");
        System.err.println(" 데이터베이스가 연결되지 않았습니다.");
        System.err.println("게임을 불러오거나 저장하지 않습니다.");
        System.err.println("-----------------------------");
        System.err.println();
    }
}
