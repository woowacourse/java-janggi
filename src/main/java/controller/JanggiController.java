package controller;

import dao.BoardDao;
import dao.JanggiConnection;
import dao.PieceDao;
import dao.PlayerDao;
import dao.TeamDao;
import domain.JanggiGame;
import domain.Player;
import domain.board.Board;
import domain.board.Score;
import dto.MovementRequestDto;
import entity.BoardRepository;
import entity.GameInitializer;
import entity.PieceRepository;
import entity.PlayerRepository;
import entity.TeamRepository;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BoardRepository boardRepository = new BoardRepository(new BoardDao(new JanggiConnection()));
        PlayerRepository playerRepository = new PlayerRepository(new PlayerDao(new JanggiConnection()));
        TeamRepository teamRepository = new TeamRepository(new TeamDao(new JanggiConnection()));

        GameInitializer gameInitializer = new GameInitializer(
                boardRepository,
                new PieceRepository(new PieceDao(new JanggiConnection())),
                teamRepository,
                playerRepository
        );

        Board board = gameInitializer.createBoard();
        List<Player> players = gameInitializer.getAllPlayers();

        final JanggiGame game = new JanggiGame(board, players, boardRepository, playerRepository, teamRepository);

        outputView.printBoard(game.getBoard());
        while (true) {
            if (game.isGeneralDied()) {
                Score score = game.calculateScore();
                OutputView.printGameEndMessage(score);
                break;
            }
            processMove(game);

        }
    }

    private void processMove(final JanggiGame game) {
        final MovementRequestDto movementRequestDto = inputView.readMovementRequest();

        game.move(movementRequestDto.startPoint(), movementRequestDto.arrivalPoint());

        outputView.printBoard(game.getBoard());
    }
}
