package service;

import dao.GameEntity;
import dao.JanggiDao;
import dao.JanggiPieceEntity;
import domain.JanggiBoard;
import domain.JanggiBoardFactory;
import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final JanggiDao janggiDao;

    public JanggiService(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
    }

    public void createJanggiGame(Long gameId) {
        if (janggiDao.findGameById(gameId).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 게임 ID 입니다.");
        }
        janggiDao.addGame(new GameEntity(gameId));
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardFactory.createJanggiBoard());
        janggiDao.addPiece(createJanggiEntities(gameId, janggiBoard));
    }

    private List<JanggiPieceEntity> createJanggiEntities(Long gameId, JanggiBoard janggiBoard) {
        Map<JanggiPosition, Piece> board = janggiBoard.getJanggiBoard();

        List<JanggiPieceEntity> entities = new ArrayList<>();
        for (JanggiPosition position : board.keySet()) {
            Piece piece = board.get(position);
            entities.add(new JanggiPieceEntity(gameId, position.rank(), position.file(), piece.getPieceSymbol(), piece.getSide()));
        }
        return entities;
    }

    public List<JanggiPieceEntity> getJanggiPieces(Long gameId) {
        return janggiDao.findAllPiecesById(gameId);
    }

    public Map<JanggiPosition, Piece> loadJanggiBoard(List<JanggiPieceEntity> pieceEntities) {
        Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();
        for (JanggiPieceEntity pieceEntity : pieceEntities) {
            janggiBoard.put(new JanggiPosition(pieceEntity.getRank(), pieceEntity.getFile()), pieceEntity.createPiece());
        }
        return janggiBoard;
    }

    public JanggiBoard move(Long gameId, JanggiPosition beforePosition, JanggiPosition afterPosition) {
//        GameEntity gameEntity = getGameById(gameId);
        List<JanggiPieceEntity> janggiPieceEntities = getJanggiPieces(gameId);
        Map<JanggiPosition, Piece> janggiBoardMap = loadJanggiBoard(janggiPieceEntities);
        JanggiBoard janggiBoard = new JanggiBoard(janggiBoardMap);

        Piece pieceToMove = janggiBoard.getPieceFrom(beforePosition);
        Piece targetPiece = janggiBoard.getPieceFrom(afterPosition);

        if (pieceToMove.isEmpty()) {
            throw new IllegalArgumentException("이동하려는 위치에 기물이 없습니다.");
        }

        janggiBoard.move(beforePosition, afterPosition);

        updateJanggiPieces(gameId, janggiBoard);
        return janggiBoard;
    }

    private void updateJanggiPieces(Long gameId, JanggiBoard janggiBoard) {
        List<JanggiPieceEntity> updatedPieces = new ArrayList<>();
        for (Map.Entry<JanggiPosition, Piece> entry : janggiBoard.getJanggiBoard().entrySet()) {
            JanggiPosition position = entry.getKey();
            Piece piece = entry.getValue();
            updatedPieces.add(new JanggiPieceEntity(gameId, position.rank(), position.file(), piece.getPieceSymbol(), piece.getSide()));
        }

        janggiDao.deletePiecesByGameId(gameId);
        janggiDao.addPiece(updatedPieces);
    }

    public GameEntity getGameById(Long gameId) {
        return janggiDao.findGameById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임 ID 입니다."));
    }

//    public GameEntity getGame(Long gameId) {
//        return janggiDao
//    }
}
