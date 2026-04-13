import domain.Board;
import domain.Camp;
import domain.InvalidMoveException;
import domain.Position;
import dto.BoardStatusDto;
import java.sql.SQLException;
import repository.BoardRepository;
import repository.GameRepository;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;

    JanggiController(InputView inputView, OutputView outputView, BoardRepository boardRepository, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public void run() {
        try {
            Long gameId = gameRepository.findPlayingGame();
            GameContext gameContext = loadOrGenerateBoard(gameId);
            gameContext = playJanggi(gameContext);
            printResult(gameContext);
        } catch (SQLException e) {
            throw new RuntimeException("db오류", e);
        }
    }

    private GameContext loadOrGenerateBoard(Long gameId) {
        if (gameId == null) {
            Board board = generateBoard();
            long newGameId = gameRepository.createGame(board, Camp.CHO);
            boardRepository.updateBoard(newGameId, board);
            return new GameContext(newGameId, board, Camp.CHO);
        }
        Board board = boardRepository.findBoard(gameId);
        Camp camp = gameRepository.findCurrentCamp(gameId);
        return new GameContext(gameId, board, camp);
    }

    private Board generateBoard() {
        int choElephantFormation = inputView.askElephantFormation(Camp.CHO);
        int hanElephantFormation = inputView.askElephantFormation(Camp.HAN);
        Board board = Board.empty();
        board.generatePiecesBy(Camp.CHO, choElephantFormation);
        board.generatePiecesBy(Camp.HAN, hanElephantFormation);
        return board;
    }

    private GameContext playJanggi(GameContext gameContext) throws SQLException {
        Camp camp = gameRepository.findCurrentCamp(gameContext.gameId());
        printBoard(gameContext.board());
        while (!gameContext.board().isGameOver()) {
            try {
                Position fromPosition = askFromPosition(camp, gameContext.board());
                Position toPosition = askToPosition(camp);
                gameContext.board().move(fromPosition, toPosition);
                printBoard(gameContext.board());
                if (!gameContext.board().isGameOver()) {
                    camp = camp.turnCamp();
                }
                gameRepository.updateGame(gameContext.gameId(), gameContext.board(), camp,
                        gameContext.board().isGameOver());
                boardRepository.updateBoard(gameContext.gameId(), gameContext.board());
            } catch (InvalidMoveException e) {
                outputView.printErrorMessage(e);
            }
        }
        return new GameContext(gameContext.gameId(), gameContext.board(), camp);
    }

    private Position askFromPosition(Camp camp, Board board) {
        while (true) {
            Position fromPosition = inputView.readFromPosition(camp);
            if (!board.isPieceOfCamp(fromPosition, camp)) {
                outputView.printWrongChoice();
                continue;
            }
            return fromPosition;
        }
    }

    private Position askToPosition(Camp camp) {
        return inputView.readToPosition(camp);
    }

    private void printBoard(Board board) {
        BoardStatusDto boardStatus = board.getBoardStatus();
        outputView.printBoardStatus(boardStatus);
    }

    private void printResult(GameContext gameContext) {
        double hanScore = gameContext.board().calculateScoreByCamp(Camp.HAN);
        double choScore = gameContext.board().calculateScoreByCamp(Camp.CHO);
        outputView.printResult(gameContext.camp(), hanScore, choScore);
    }
}
