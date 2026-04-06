package controller;

import database.GameRepository;
import database.entity.GameEntity;
import database.jdbc.JdbcGameDao;
import database.jdbc.JdbcPieceDao;
import domain.board.AbstractBoardFactory;
import domain.board.Board;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import java.util.Optional;
import util.Retry;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    private JanggiController(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public static JanggiController of(InputView inputView, OutputView outputView) {
        return new JanggiController(inputView, outputView, new GameRepository(new JdbcGameDao(), new JdbcPieceDao()));
    }

    public void run() {
        GameState state = initializeGame();
        Board board = state.board();
        Turn turn = state.turn();
        int gameId = state.gameId();

        outputView.printBoard(board);

        while (board.isGeneralAlive()) {
            turn = playTurn(board, turn, gameId);
        }

        board.calculateScore();
        outputView.printWinner(board.decideWinner());
        outputView.printScore(Team.CHO);
        outputView.printScore(Team.HAN);
        gameRepository.deleteGame(gameId);
    }

    private Board createBoard() {
        int choFormationNumber = Retry.untilSuccess(() -> inputView.initialFormation(Team.CHO));
        int hanFormationNumber = Retry.untilSuccess(() -> inputView.initialFormation(Team.HAN));
        AbstractBoardFactory choAbstractBoardFactory = AbstractBoardFactory.from(choFormationNumber);
        AbstractBoardFactory hanAbstractBoardFactory = AbstractBoardFactory.from(hanFormationNumber);
        Map<Position, Piece> board = AbstractBoardFactory.createFormation(choAbstractBoardFactory,
                hanAbstractBoardFactory);
        return new Board(board);
    }

    private Turn playTurn(Board board, Turn turn, int gameId) {
        return Retry.untilSuccess(() -> {
            Position[] positions = inputView.askMovePiecePosition(turn.current());
            boolean isCapture = board.getState().containsKey(positions[1]);
            board.move(positions[0], positions[1], turn.current());
            Turn next = turn.next();
            gameRepository.saveMove(gameId, positions[0], positions[1], isCapture, next.current());
            outputView.printBoard(board);
            return next;
        });
    }

    private GameState initializeGame() {
        Optional<GameEntity> savedGame = gameRepository.findLatestGame();

        Board board = savedGame
                .map(entity -> new Board(gameRepository.loadPieces(entity.id())))
                .orElseGet(this::createBoard);

        Turn turn = savedGame
                .map(entity -> Turn.of(entity.currentTurn()))
                .orElse(Turn.first());

        Team initialTeam = turn.current();
        int gameId = savedGame
                .map(GameEntity::id)
                .orElseGet(() -> gameRepository.startNewGame(initialTeam, board.getState()));

        return new GameState(board, turn, gameId);
    }

    private record GameState(Board board, Turn turn, int gameId) {
    }
}
