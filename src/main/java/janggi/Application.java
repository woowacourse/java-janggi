package janggi;

import janggi.domain.*;

public class Application {

    public static void main(String[] args) {
        JanggiRunner janggiRunner = new JanggiRunner(new JanggiGameService(new GameService(new GameDao()), new TurnService(new TurnDao()), new PieceService(new PieceDao())));
        janggiRunner.execute();
    }
}
