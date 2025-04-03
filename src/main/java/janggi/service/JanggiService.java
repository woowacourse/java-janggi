package janggi.service;

import janggi.GameState;
import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.PieceEntity;
import janggi.dao.game.GameDao;
import janggi.dao.piece.PieceDao;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(final GameDao gameDao, final PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public GameEntity findInProgressGame(final GameState gameState) {
        return gameDao.findByStatus(gameState);
    }

    public GameEntity creatGame(final Board board, final GameState gameState) {
        gameDao.addGame();
        final GameEntity gameEntity = findInProgressGame(gameState);
        pieceDao.addPieces(createPieceEntities(board.getJanggiBoard(), gameEntity.getId()));
        return findInProgressGame(gameState);
    }

    private List<PieceEntity> createPieceEntities(final Map<Position, Piece> janggiBoard, final Long gameId) {
        final List<PieceEntity> pieceEntities = new ArrayList<>();
        for (final Entry<Position, Piece> pieceEntry : janggiBoard.entrySet()) {
            final PieceEntity pieceEntity = new PieceEntity(
                    null,
                    pieceEntry.getValue().getPieceType(),
                    pieceEntry.getValue().getTeam(),
                    pieceEntry.getKey().row(),
                    pieceEntry.getKey().col(),
                    gameId
            );
            pieceEntities.add(pieceEntity);
        }
        return pieceEntities;
    }

    public Board findBoardById(final Long id) {
        final List<PieceEntity> pieceEntities = pieceDao.findPiecesById(id);
        return new BoardGenerator().generate(pieceEntities);
    }

    public void updateGameStatue(final Long id, final Team currentTurnTeam, final Board board) {
        final double chuScore = board.calculateTotalScore(Team.CHU);
        final double hanScore = board.calculateTotalScore(Team.HAN);

        gameDao.updateGameStatus(id, currentTurnTeam, chuScore, hanScore);
    }

    public void deleteGame(final Long id) {
        pieceDao.deletePiecesBy(id);
        gameDao.deleteGameBy(id);
    }

    public void deletePiece(final Long id, final Position position) {
        pieceDao.removePieceByPosition(id, position);
    }

    public void updatePiece(final GameEntity gameEntity, final Piece movePiece, final Position targetPosition) {
        pieceDao.updatePiece(new PieceEntity(
                null,
                movePiece.getPieceType(),
                movePiece.getTeam(),
                targetPosition.row(),
                targetPosition.col(),
                gameEntity.getId()
        ));
    }

}
