package janggi;

import janggi.domain.*;
import janggi.domain.game.GameDao;
import janggi.domain.game.GameService;
import janggi.domain.piece.PieceDao;
import janggi.domain.piece.PieceService;
import janggi.domain.turn.TurnDao;
import janggi.domain.turn.TurnService;

public class Application {

    public static void main(String[] args) {
        JanggiRunner janggiRunner = new JanggiRunner(new JanggiGameService(new GameService(new GameDao()), new TurnService(new TurnDao()), new PieceService(new PieceDao())));
        janggiRunner.execute();
    }
}
