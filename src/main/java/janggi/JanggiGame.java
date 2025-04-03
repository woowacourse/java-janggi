package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.entity.GameEntity;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiGame {

    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiService janggiService;
    private GameState gameState;

    public JanggiGame(final OutputView outputView, final InputView inputView, final JanggiService janggiService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.janggiService = janggiService;
        this.gameState = GameState.IN_PROGRESS;
    }

    public void startGame() {
        GameEntity gameEntity = getCallInGame();
        if (gameEntity == null) {
            gameEntity = getNewGame();
        }
        playGame(gameEntity);
    }

    private GameEntity getNewGame() {
        outputView.printNewGame();
        final Board board = setJanggiBoard();
        return janggiService.creatGame(board, GameState.IN_PROGRESS);
    }

    private GameEntity getCallInGame() {
        final GameEntity gameEntity = janggiService.findInProgressGame(GameState.IN_PROGRESS);

        if (gameEntity != null) {
            outputView.printCallInGame();
        }
        return gameEntity;
    }

    private Board setJanggiBoard() {
        final BoardGenerator boardGenerator = new BoardGenerator();
        return boardGenerator.generate();
    }

    private void playGame(final GameEntity gameEntity) {
        Team currentTurnTeam = gameEntity.getCurrentTeam();
        while (isNotEnd()) {
            final Board board = janggiService.findBoardById(gameEntity.getId());

            outputView.printJanggiBoard(board.getJanggiBoard());
            showScore(board);

            playTurn(board, currentTurnTeam, gameEntity);
            currentTurnTeam = changeTurn(currentTurnTeam);
            gameEntity.updateGameTurn(currentTurnTeam);
            janggiService.updateGameStatue(gameEntity.getId(), currentTurnTeam, board);
        }
        endGame(gameEntity, currentTurnTeam);
    }

    private void endGame(final GameEntity gameEntity, final Team currentTurnTeam) {
        final Board board = janggiService.findBoardById(gameEntity.getId());
        showGameResult(board, currentTurnTeam);
        janggiService.deleteGame(gameEntity.getId());
    }

    private void playTurn(final Board janggiBoard, final Team currentTurnTeam, final GameEntity gameEntity) {
        try {
            final Position currentPosition = getReadCurrentPosition(janggiBoard, currentTurnTeam);
            final Position targetPosition = readTargetPosition();

            pieceMove(janggiBoard, currentPosition, targetPosition, gameEntity);
            if (isNotEnd()) {
                outputView.printSuccessMove();
            }
        } catch (final IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            playTurn(janggiBoard, currentTurnTeam, gameEntity);
        }
    }

    private Position getReadCurrentPosition(final Board janggiBoard, final Team currentTurnTeam) {
        final Position currentPosition = readCurrentPosition(currentTurnTeam.getDescription());
        janggiBoard.validateEmptyPieceBy(currentPosition);
        validateCurrentTeamBy(janggiBoard, currentPosition, currentTurnTeam);
        return currentPosition;
    }

    private void pieceMove(final Board janggiBoard, final Position currentPosition, final Position targetPosition,
                           final GameEntity gameEntity) {
        gameState = janggiBoard.pieceMove(currentPosition, targetPosition);

        final Piece movedPiece = janggiBoard.getJanggiBoard().get(targetPosition);
        janggiService.deletePiece(gameEntity.getId(), currentPosition);
        janggiService.deletePiece(gameEntity.getId(), targetPosition);
        janggiService.updatePiece(gameEntity, movedPiece, targetPosition);
    }

    private boolean isNotEnd() {
        return gameState != GameState.END;
    }

    private void showScore(final Board janggiBoard) {
        final double chuScore = janggiBoard.calculateTotalScore(Team.CHU);
        final double hanScore = janggiBoard.calculateTotalScore(Team.HAN);
        outputView.printScore(chuScore, hanScore);
    }

    private void validateCurrentTeamBy(final Board board, final Position currentPosition, final Team currentTurnTeam) {
        final Piece piece = board.getJanggiBoard().get(currentPosition);
        piece.validateTeam(currentTurnTeam);
    }

    private Team changeTurn(final Team currentTurnTeam) {
        return currentTurnTeam.changeTeam();
    }

    private void showGameResult(final Board board, final Team currentTurnTeam) {
        outputView.printJanggiBoard(board.getJanggiBoard());
        outputView.printEndGame();
        outputView.printWinner(currentTurnTeam.changeTeam().getDescription());
        outputView.printScore(
                board.calculateTotalScore(Team.CHU),
                board.calculateTotalScore(Team.HAN));
    }

    private Position readCurrentPosition(final String currentTurnTeam) {
        while (true) {
            try {
                return inputView.readCurrentPosition(currentTurnTeam);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readTargetPosition() {
        while (true) {
            try {
                return inputView.readTargetPosition();
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
