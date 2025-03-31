import controller.JanggiController;

public class Application {

    private static final JanggiController controller = new JanggiController();

    public static void main(String[] args) {
        int gameId = controller.startGame();
        while (controller.isPlaying(gameId)) {
            controller.playTurn(gameId);
            controller.nextTurn(gameId);
        }
        controller.endGame(gameId);
    }
}
