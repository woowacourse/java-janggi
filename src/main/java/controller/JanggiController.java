package controller;

import db.repository.GameRepository;
import db.persistence.Persisted;
import domain.board.Board;
import domain.board.ChoWings;
import domain.board.HanWings;
import domain.board.InitialPieces;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.movement.Move;
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
        Persisted<JanggiGame> persistedGame = retryOnIllegalArgument(this::startGame);
        JanggiGame game = persistedGame.getData();

        while (game.isPlaying()) {
            JanggiGame gameSnapshot = game.copyOf();
            game = retryOnIllegalArgument(() -> progressTurn(gameSnapshot));

            persistedGame.update(game);
            gameRepository.update(persistedGame);
        }

        finishGame(persistedGame);
    }

    private Persisted<JanggiGame> startGame() {
        boolean shouldStartNewGame = view.askStartNewGame();
        if (shouldStartNewGame) {
            return startNewGame();
        }
        return resumeExistGame();
    }

    private Persisted<JanggiGame> startNewGame() {
        ChoWings choWings = retryOnIllegalArgument(view::readChowings);
        HanWings hanWings = retryOnIllegalArgument(view::readHanWings);

        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);
        AlivePieces alivePieces = initialPieces.get();

        JanggiGame game = new JanggiGame(new Board(alivePieces));

        Persisted<JanggiGame> newGame = gameRepository.save(game);
        view.printNewGameId(newGame.getId());

        return newGame;
    }

    private Persisted<JanggiGame> resumeExistGame() {
        List<Integer> gameIds = gameRepository.findAllIds();
        int gameId = view.readExistGameId(gameIds);

        return gameRepository.findById(gameId);
    }

    private JanggiGame progressTurn(JanggiGame game) {
        Map<Intersection, Piece> board = game.getPieces();
        Side currentTurn = game.getCurrentTurn();

        Intersection startIntersection = view.readSelectPieceToMove(board, currentTurn);
        List<Intersection> movableIntersections = game.getMovableIntersections(startIntersection);
        Intersection destination = view.readMovePiece(board, movableIntersections);

        Move move = new Move(startIntersection, destination);

        return game.movePiece(move);
    }

    private void finishGame(Persisted<JanggiGame> persistedGame) {
        JanggiGame game = persistedGame.getData();

        Side winner = game.getWinner();
        List<ScoreDto> scores = calculateTotalScore(game);
        view.printWinner(winner, scores);

        gameRepository.deleteById(persistedGame.getId());
    }

    private List<ScoreDto> calculateTotalScore(JanggiGame game) {
        return Arrays.stream(Side.values())
                .map(side -> new ScoreDto(side, game.getTotalScore(side)))
                .toList();
    }

    private <T> T retryOnIllegalArgument(Supplier<T> retryableAction) {
        return RetryUtil.retryOnInvalidInput(retryableAction, view::printError);
    }
}
