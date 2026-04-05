package global.config;

import domain.board.Board;
import domain.janggigame.JanggiGame;
import domain.piece.Side;
import domain.player.Player;
import domain.players.Players;
import infra.JDBCBoardRepository;
import infra.JDBCPlayerRepository;
import repository.BoardRepository;
import repository.PlayerRepository;
import service.BoardService;
import service.PlayerService;

public class DiConfig {

    private final Board board = new Board();
    private final BoardRepository boardRepository = new JDBCBoardRepository();
    private final PlayerRepository playerRepository = new JDBCPlayerRepository();

    public JanggiGame janggiGame() {
        return new JanggiGame(
                boardService(),
                playerService()
        );
    }

    public BoardService boardService() {
        return new BoardService(
                board,
                boardRepository
        );
    }

    public PlayerService playerService() {
        return new PlayerService(
                players(),
                playerRepository
        );
    }

    private Players players() {
        return new Players(choPlayer(), hanPlayer());
    }

    private Player choPlayer() {
        return new Player(Side.CHO);
    }

    private Player hanPlayer() {
        return new Player(Side.HAN);
    }
}
