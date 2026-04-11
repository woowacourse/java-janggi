package controller;

import database.dto.GameDto;
import database.service.GameService;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.game.Score;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import util.Retry;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public JanggiController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        try {
            GameState state = initializeGame();
            outputView.printBoard(state.board());
            playGame(state);
            finishGame(state);
        } catch (RuntimeException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void playGame(GameState state) {
        Turn turn = state.turn();
        while (state.board().isGeneralAlive()) {
            turn = playTurn(state.board(), turn, state.gameId());
        }
    }

    private void finishGame(GameState state) {
        Board board = state.board();
        Map<Team, Score> scores = board.calculateScore();
        outputView.printWinner(board.decideWinner());
        outputView.printScore(Team.CHO, scores.get(Team.CHO));
        outputView.printScore(Team.HAN, scores.get(Team.HAN));
        gameService.deleteGame(state.gameId());
    }

    private GameState initializeGame() {
        return gameService.findLatestGame()
                .map(this::resumeGame)
                .orElseGet(this::startNewGame);
    }

    private GameState resumeGame(GameDto entity) {
        Board board = new Board(gameService.loadPieces(entity.id()));
        Turn turn = Turn.of(entity.currentTurn());
        return new GameState(board, turn, entity.id());
    }

    private GameState startNewGame() {
        Board board = createBoard();
        Turn turn = Turn.first();
        int gameId = gameService.startNewGame(turn.current(), board.getState());
        return new GameState(board, turn, gameId);
    }

    private Board createBoard() {
        int choFormationNumber = getFormationNumber(Team.CHO);
        int hanFormationNumber = getFormationNumber(Team.HAN);
        Map<Position, Piece> board = BoardFactory.createFormation(choFormationNumber, hanFormationNumber);
        return new Board(board);
    }

    private int getFormationNumber(Team team) {
        return Retry.untilSuccess(
                () -> inputView.initialFormation(team),
                e -> outputView.printErrorMessage(e.getMessage())
        );
    }

    private Turn playTurn(Board board, Turn turn, int gameId) {
        return Retry.untilSuccess(
                () -> executeMove(board, turn, gameId),
                e -> outputView.printErrorMessage(e.getMessage())
        );
    }

    private Turn executeMove(Board board, Turn turn, int gameId) {
        List<Position> positions = inputView.askMovePiecePosition(turn.current());
        Position src = positions.get(0);
        Position dest = positions.get(1);
        Turn next = gameService.executeMove(gameId, board, turn, src, dest);
        outputView.printBoard(board);
        return next;
    }
}
