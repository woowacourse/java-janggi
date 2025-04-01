package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.PieceEntity;
import janggi.dao.entity.Status;
import janggi.domain.board.BoardSetUp;
import janggi.domain.board.JanggiBoard;
import janggi.domain.gamestatus.GameStatus;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public GameEntity getRunningGameByName(String name) {
        GameEntity gameEntity = gameDao.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(name + "에 해당하는 게임이 존재하지 않습니다."));
        if (gameEntity.isEnd()) {
            throw new IllegalArgumentException("이미 종료된 게임입니다.");
        }
        return gameEntity;
    }

    public void createGame(String gameName, BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        if (gameDao.findByName(gameName).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 게임 이름입니다.");
        }
        gameDao.addGame(new GameEntity(gameName, Status.RUN, Dynasty.CHU));
        GameEntity gameEntity = getRunningGameByName(gameName);
        JanggiBoard janggiBoard = JanggiBoard.of(hanBoardSetUp, chuBoardSetUp);
        pieceDao.addPieces(createPieceEntities(gameEntity, janggiBoard));
    }

    public GameStatus findJanggiStatusByGameId(Long gameId) {
        GameEntity gameEntity = getGameById(gameId);
        return GameStatus.of(gameEntity.getCurrentTurn(), findJanggiBoardByGameId(gameEntity.getId()));
    }

    public GameStatus move(Long gameId, Point from, Point to) {
        GameEntity gameEntity = getGameById(gameId);
        JanggiBoard janggiBoard = findJanggiBoardByGameId(gameId);

        GameStatus gameStatus = GameStatus.of(gameEntity.getCurrentTurn(), janggiBoard).move(from, to);

        pieceDao.deletePiece(gameId, to);
        pieceDao.updatePiece(gameId, from, to);
        pieceDao.deletePiece(gameId, from);
        if (gameStatus.isEndGame()) {
            gameDao.updateStatus(gameId, Status.END);
            return gameStatus;
        }
        gameDao.updateCurrentTurn(gameId, gameStatus.currentTurn());
        return gameStatus;
    }

    public JanggiBoard findJanggiBoardByGameId(Long gameId) {
        List<PieceEntity> pieceEntities = pieceDao.findAllByGameId(gameId);
        return toJanggiBoard(pieceEntities);
    }

    private List<PieceEntity> createPieceEntities(GameEntity gameEntity, JanggiBoard janggiBoard) {
        Map<Point, Piece> pieces = janggiBoard.getPieces();
        List<PieceEntity> pieceEntities = new ArrayList<>();
        for (Point point : pieces.keySet()) {
            Piece piece = pieces.get(point);
            pieceEntities.add(new PieceEntity(point, piece.getDynasty(), piece.pieceType(), gameEntity.getId()));
        }
        return pieceEntities;
    }

    private JanggiBoard toJanggiBoard(List<PieceEntity> pieceEntities) {
        Map<Point, Piece> pieces = new HashMap<>();
        for (PieceEntity pieceEntity : pieceEntities) {
            pieces.put(pieceEntity.getPoint(), pieceEntity.createPiece());
        }
        return new JanggiBoard(pieces);
    }

    private GameEntity getGameById(Long gameId) {
        return gameDao.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 게임이 존재하지 않습니다."));
    }
}
