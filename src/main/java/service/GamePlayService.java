package service;

import core.JanggiGame;
import core.MoveHistory;
import db.repository.JanggiGameRepository;
import pieces.Piece;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;

public class GamePlayService {

    private final JanggiView view;
    private final DbTemplate dbTemplate;
    private final JanggiGameRepository repository;

    public GamePlayService(
        final JanggiView view,
        final DbTemplate dbTemplate,
        final JanggiGameRepository repository
    ) {
        this.view = view;
        this.dbTemplate = dbTemplate;
        this.repository = repository;
    }

    public void play(final PreparedGame prepared) {
        play(prepared.gameId(), prepared.game(), prepared.moveCount());
    }

    private void play(final Long gameId, JanggiGame game, MoveCount moveCount) {
        while (!game.isOver()) {
            final GameTurnResult result = playOneTurn(game, moveCount.canUndo());

            if (result.undoRequested()) {
                game = revertGame(gameId);
                moveCount = moveCount.unDo();
                continue;
            }
            saveTurn(gameId, result);

            game = result.updatedGame();
            if (!result.hasNoPieceMove()) {
                moveCount = moveCount.move();
            }
        }
        view.printGameResult(game.getResult());
    }

    private JanggiGame revertGame(Long gameId) {
        return dbTemplate.inTransaction(connection -> {
            repository.undoLastMove(connection, gameId);
            return repository.findGameById(connection, gameId)
                .orElseThrow(() -> new IllegalStateException("무르기 후 게임 조회에 실패했습니다."));
        });
    }

    private GameTurnResult playOneTurn(final JanggiGame game, final boolean canUndo) {
        return Retry.untilSuccess(() -> {
            printGameStatus(game);

            if (canUndo && view.askUndoRequest(game.getTurnSide())) {
                return GameTurnResult.undoRequested(game);
            }

            if (view.askEndByScore(game.getTurnSide())) {
                return GameTurnResult.endByScore(game.endByScore());
            }

            return move(game);
        });
    }

    private void printGameStatus(final JanggiGame game) {
        view.printBoard(DisplayBoard.of(game.getBoard()));

        final Side turnSide = game.getTurnSide();
        view.printTurnSide(turnSide);

        view.printScore(turnSide, game.calculateScoreOf(turnSide));
        view.printScore(turnSide.other(), game.calculateScoreOf(turnSide.other()));
    }

    private GameTurnResult move(final JanggiGame game) {
        final Position departure = view.askDeparture();
        final Position destination = view.askDestination();

        final Piece movingPiece = game.getPieceAt(departure);
        final Piece capturedPiece = game.getPieceAt(destination);

        final JanggiGame updated = game.move(departure, destination);

        return GameTurnResult.move(updated, new MoveHistory(departure, destination, movingPiece, capturedPiece));
    }

    private void saveTurn(final Long gameId, final GameTurnResult turnResult) {
        dbTemplate.inTransaction(connection -> {
            repository.updateGame(connection, gameId, turnResult.updatedGame());
            if (turnResult.hasNoPieceMove()) {
                return null;
            }

            final MoveHistory moveHistory = turnResult.moveHistory();
            repository.updatePiecePosition(connection, gameId, moveHistory.departure(), moveHistory.destination());
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
    }
}
