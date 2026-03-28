package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.side.Side;

public class Game {
    private final GameSetUp choGameSetUp;
    private final GameSetUp hanGameSetUp;
    private final Board board;

    private Game(GameSetUp choGameSetUp, GameSetUp hanGameSetUp, Board board) {
        this.choGameSetUp = choGameSetUp;
        this.hanGameSetUp = hanGameSetUp;
        this.board = board;
    }

    public static Game createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(
                new GameSetUp(Side.CHO, choBoardSetUp),
                new GameSetUp(Side.HAN, hanBoardSetUp),
                Board.setUp(choBoardSetUp, hanBoardSetUp));
    }
}
