package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Players;

public class GameInitializer {

    public static Game initialize(Players players, Formation choFormation, Formation hanFormation) {
        Board board = BoardFactory.createWithFormation(choFormation, hanFormation);
        return new Game(players, board);
    }
}
