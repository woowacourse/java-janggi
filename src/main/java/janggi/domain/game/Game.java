package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.player.Player;
import janggi.domain.player.PlayerSetUp;
import janggi.domain.side.Side;

public class Game {
    private final PlayerSetUp choPlayerSetUp;
    private final PlayerSetUp hanPlayerSetUp;
    private final Board board;

    private Game(PlayerSetUp choPlayerSetUp, PlayerSetUp hanPlayerSetUp, Board board) {
        this.choPlayerSetUp = choPlayerSetUp;
        this.hanPlayerSetUp = hanPlayerSetUp;
        this.board = board;
    }

    public static Game createGame(Player choPlayer, Player hanPlayer,
                                  BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(
                new PlayerSetUp(choPlayer, Side.CHO, choBoardSetUp),
                new PlayerSetUp(hanPlayer, Side.HAN, hanBoardSetUp),
                Board.setUp(choBoardSetUp, hanBoardSetUp));
    }
}
