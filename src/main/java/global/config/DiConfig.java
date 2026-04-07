package global.config;

import controller.JanggiController;
import infra.JDBCBoardRepository;
import infra.JDBCGameRepository;
import infra.JDBCPlayerRepository;
import repository.BoardRepository;
import repository.GameRepository;
import repository.PlayerRepository;
import service.BoardService;
import service.JanggiService;

public class DiConfig {

    private final BoardRepository boardRepository = new JDBCBoardRepository();
    private final PlayerRepository playerRepository = new JDBCPlayerRepository();
    private final GameRepository gameRepository = new JDBCGameRepository();

    public JanggiController janggiController() {
        return new JanggiController(janggiService());
    }

    public JanggiService janggiService() {
        return new JanggiService(
                gameRepository,
                boardService()
        );
    }

    public BoardService boardService() {
        return new BoardService(
                boardRepository,
                playerRepository
        );
    }
}
