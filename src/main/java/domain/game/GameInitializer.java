package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Players;

public class GameInitializer {

    public static Game initialize(Players players, int choPositionInput, int hanPositionInput) {
        Board board = createBoard(choPositionInput, hanPositionInput);
        return new Game(players, board);
    }

    private static Board createBoard(int choPositionInput, int hanPositionInput) {
        Formation choFormation = createFormation(choPositionInput);
        Formation hanFormation = createFormation(hanPositionInput);

        return BoardFactory.createWithFormation(choFormation, hanFormation);
    }

    private static Formation createFormation(int positionInput) {
        return Formation.from(positionInput);
    }
}
