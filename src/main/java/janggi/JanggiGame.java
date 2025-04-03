package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.PieceEntity;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.multiplemovepiece.Cannon;
import janggi.piece.multiplemovepiece.Chariot;
import janggi.piece.multiplemovepiece.Elephant;
import janggi.piece.multiplemovepiece.Horse;
import janggi.piece.onemovepiece.Guard;
import janggi.piece.onemovepiece.King;
import janggi.piece.onemovepiece.Pawn;
import janggi.piece.onemovepiece.Soldier;
import janggi.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiGame {

    private static final Team FIRST_TURN_TEAM = Team.CHU;

    private final OutputView outputView;
    private final InputView inputView;
    private GameState gameState;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiGame(final OutputView outputView, final InputView inputView, final GameDao gameDao,
                      final PieceDao pieceDao) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
        this.gameState = GameState.IN_PROGRESS;
    }

    public void startGame() {
        GameEntity gameEntity = gameDao.findByStatus(GameState.IN_PROGRESS);
        if (gameEntity == null) {
            gameDao.addGame(FIRST_TURN_TEAM, GameState.IN_PROGRESS);
            gameEntity = gameDao.findByStatus(GameState.IN_PROGRESS);
            final Board board = setJanggiBoard();
            pieceDao.addPieces(pieceDao.createPieceEntities(board.getJanggiBoard(), gameEntity.getId()));
        }
        playJanggi(gameEntity);
    }

    private Board setJanggiBoard() {
        final BoardGenerator boardGenerator = new BoardGenerator();
        return boardGenerator.generate();
    }

    private void playJanggi(final GameEntity gameEntity) {
        Team currentTurnTeam = gameEntity.getCurrentTeam();

        while (isNotEnd()) {
            final List<PieceEntity> pieceEntities = pieceDao.findPiecesBy(gameEntity.getId());
            final Board board = createJanggiBoardBy(pieceEntities);

            outputView.printJanggiBoard(board.getJanggiBoard());
            showScore(board);

            playTurn(board, currentTurnTeam, gameEntity);
            currentTurnTeam = changeTurn(currentTurnTeam);

            final double chuScore = board.calculateTotalScore(Team.CHU);
            final double hanScore = board.calculateTotalScore(Team.HAN);

            gameEntity.updateGameTurn(currentTurnTeam);
            gameDao.updateGameStatus(gameEntity.getId(), currentTurnTeam, chuScore, hanScore);
        }

        final List<PieceEntity> pieceEntities = pieceDao.findPiecesBy(gameEntity.getId());
        final Board board = createJanggiBoardBy(pieceEntities);
        showGameResult(board, currentTurnTeam);

        pieceDao.deletePiecesBy(gameEntity.getId());
        gameDao.deleteGameBy(gameEntity.getId());
    }

    private Board createJanggiBoardBy(final List<PieceEntity> pieceEntities) {
        final Board board = new Board();
        for (final PieceEntity pieceEntity : pieceEntities) {
            if (PieceType.KING.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new King(pieceEntity.getTeam())
                );
            }
            if (PieceType.GUARD.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Guard(pieceEntity.getTeam())
                );
            }
            if (PieceType.HORSE.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Horse(pieceEntity.getTeam())
                );
            }
            if (PieceType.ELEPHANT.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Elephant(pieceEntity.getTeam())
                );
            }
            if (PieceType.CANNON.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Cannon(pieceEntity.getTeam())
                );
            }
            if (PieceType.CHARIOT.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Chariot(pieceEntity.getTeam())
                );
            }
            if (PieceType.PAWN.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Pawn()
                );
            }
            if (PieceType.SOLDIER.equals(pieceEntity.getPieceType())) {
                board.deployPiece(
                        new Position(pieceEntity.getRowIndex(), pieceEntity.getColIndex()),
                        new Soldier()
                );
            }
        }
        return board;
    }

    private void playTurn(final Board janggiBoard, final Team currentTurnTeam, final GameEntity gameEntity) {
        try {
            final Position currentPosition = readCurrentPosition(currentTurnTeam.getDescription());
            janggiBoard.validateEmptyPieceBy(currentPosition);
            validateCurrentTeamBy(janggiBoard, currentPosition, currentTurnTeam);

            final Position targetPosition = readTargetPosition();

            pieceMove(janggiBoard, currentPosition, targetPosition);

            final Piece movedPiece = janggiBoard.getJanggiBoard().get(targetPosition);

            pieceDao.removePieceByPosition(gameEntity.getId(), currentPosition);
            pieceDao.removePieceByPosition(gameEntity.getId(), targetPosition);
            pieceDao.updatePiece(new PieceEntity(
                    null,
                    movedPiece.getPieceType(),
                    movedPiece.getTeam(),
                    targetPosition.row(),
                    targetPosition.col(),
                    gameEntity.getId()
            ));

            if (isNotEnd()) {
                outputView.printSuccessMove();
            }
        } catch (final IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            playTurn(janggiBoard, currentTurnTeam, gameEntity);
        }
    }

    private void pieceMove(final Board janggiBoard, final Position currentPosition, final Position targetPosition) {
        gameState = janggiBoard.pieceMove(currentPosition, targetPosition);
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
