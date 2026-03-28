import board.InnerSangSetup;
import board.LeftSangSetup;
import core.JanggiGame;
import position.Position;

public class Application {
    public static void main(String[] args) {
        // TODO : view 에서 상차림 선택 받기 (사이클2)
        JanggiGame game = JanggiGame.of(new InnerSangSetup(), new LeftSangSetup());

        boolean notJangGun = true;
        while (notJangGun) {
            // TODO : view 에서 좌표 선택 받기 (사이클2)
            Position departure = new Position(0, 0);
            Position destination = new Position(1, 0);

            game.move(departure, destination);

            // TODO : 왕이 잡히면 게임 종료 (사이클2)
            notJangGun = false;
        }
    }
}
