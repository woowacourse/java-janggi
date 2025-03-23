import controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        JanggiController controller = new JanggiController();
        controller.startGame();
        while (controller.isPlaying()) {
            controller.playTurn();
            controller.nextTurn();
        }
        controller.endGame();
    }
}
