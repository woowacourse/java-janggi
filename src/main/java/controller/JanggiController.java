package controller;

import database.GameRepository;
import database.entity.GameEntity;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.game.Score;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import util.Retry;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public JanggiController(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
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

        Map<Team, Score> scores = board.calculateScore();
        outputView.printWinner(board.decideWinner());
        outputView.printScore(Team.CHO, scores.get(Team.CHO));
        outputView.printScore(Team.HAN, scores.get(Team.HAN));
        gameRepository.deleteGame(gameId);
    }

    private Board createBoard() {
        int choFormationNumber = Retry.untilSuccess(
                () -> inputView.initialFormation(Team.CHO),
                e -> outputView.printErrorMessage(e.getMessage())
        );
        int hanFormationNumber = Retry.untilSuccess(
                () -> inputView.initialFormation(Team.HAN),
                e -> outputView.printErrorMessage(e.getMessage())
        );
        Map<Position, Piece> board = BoardFactory.createFormation(choFormationNumber, hanFormationNumber);
        return new Board(board);
    }

    private Turn playTurn(Board board, Turn turn, int gameId) {
        return Retry.untilSuccess(() -> {
            List<Position> positions = inputView.askMovePiecePosition(turn.current());
            Position src = positions.get(0);
            Position dest = positions.get(1);
            boolean isCapture = board.getState().containsKey(dest);
            board.move(src, dest, turn.current());
            Turn next = turn.next();
            gameRepository.saveMove(gameId, src, dest, isCapture, next.current());
            outputView.printBoard(board);
            return next;
        }, e -> outputView.printErrorMessage(e.getMessage()));
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

}
