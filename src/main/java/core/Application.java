package core;

import board.InnerSangSetup;
import board.LeftSangSetup;
import pieces.Side;
import position.Position;

public class Application {
    public static void main(String[] args) {
        // TODO : view 에서 받아서 처리
        JanggiGame game = JanggiGame.of(new InnerSangSetup(), new LeftSangSetup());

        Side curSide = Side.CHO;
        boolean isChoTurn = true;
        boolean notJangGun = true;
        // TODO : 왕이 잡히면 게임 종료 (사이클 2)
        while (notJangGun) {
            // TODO : view 에서 받아서 처리
            Position departure = new Position(1, 1);
            Position destination = new Position(1, 2);

            game.move(departure, destination, curSide);
            if (isChoTurn) {
                curSide = Side.HAN;
            } else {
                curSide = Side.CHO;
            }
            isChoTurn = !isChoTurn;
        }
    }
}
