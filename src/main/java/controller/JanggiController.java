package controller;

import db.repository.GameRepository;
import db.session.Session;
import domain.board.Board;
import domain.board.ChoWings;
import domain.board.HanWings;
import domain.board.InitialPieces;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import util.RetryUtil;
import view.ApplicationView;
import view.dto.ScoreDto;

public class JanggiController {

    private final ApplicationView view = new ApplicationView();
    private final GameRepository gameRepository;

    public JanggiController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void run() {
        Session<JanggiGame> gameSession = retryOnIllegalArgument(this::startGame);
        JanggiGame game = gameSession.payload();

        while (game.isPlaying()) {
            retryOnIllegalArgument(() -> progressTurn(game));
            gameRepository.update(gameSession);
        }

        finishGame(gameSession);
    }

    private Session<JanggiGame> startGame() {
        boolean shouldStartNewGame = view.askStartNewGame();
        if (shouldStartNewGame) {
            return startNewGame();
        }
        return resumeExistGame();
    }

    private Session<JanggiGame> startNewGame() {
        ChoWings choWings = retryOnIllegalArgument(view::readChowings);
        HanWings hanWings = retryOnIllegalArgument(view::readHanWings);

        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);
        AlivePieces alivePieces = initialPieces.get();

        JanggiGame game = new JanggiGame(new Board(alivePieces));

        Session<JanggiGame> newGame = gameRepository.save(game);
        view.printNewGameId(newGame.id());

        return newGame;
    }

    private Session<JanggiGame> resumeExistGame() {
        List<Integer> gameIds = gameRepository.findAllIds();
        int gameId = view.readExistGameId(gameIds);

        return gameRepository.findById(gameId);
    }

    private void progressTurn(JanggiGame game) {
        Map<Intersection, Piece> board = game.getBoard();
        Side currentTurn = game.getCurrentTurn();

        Intersection startIntersection = view.readSelectPieceToMove(board, currentTurn);
        List<Intersection> movableIntersections = game.getMovableIntersections(startIntersection);
        Intersection destination = view.readMovePiece(board, movableIntersections);

        game.movePiece(startIntersection, destination);
    }

    private void finishGame(Session<JanggiGame> gameSession) {
        JanggiGame game = gameSession.payload();

        Side winner = game.getWinner();
        List<ScoreDto> scores = calculateTotalScore(game);
        view.printWinner(winner, scores);

        gameRepository.delete(gameSession);
    }

    private List<ScoreDto> calculateTotalScore(JanggiGame game) {
        return Arrays.stream(Side.values())
                .map(side -> new ScoreDto(side, game.getTotalScore(side)))
                .toList();
    }

    private <T> T retryOnIllegalArgument(Supplier<T> retryableAction) {
        return RetryUtil.retryOnInvalidInput(retryableAction, view::printError);
    }

    private void retryOnIllegalArgument(Runnable retryableAction) {
        RetryUtil.retryOnInvalidInput(retryableAction, view::printError);
    }
}
