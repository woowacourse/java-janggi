import dao.JanggiDatabaseConnector;

public class Application {
    public static void main(String[] args) {
        JanggiBoard board = new JanggiBoard(new JanggiDatabaseConnector());
        board.play();
    }
}
