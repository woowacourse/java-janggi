package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.PieceEntity;
import janggi.dao.entity.Status;
import janggi.domain.JanggiStatus;
import janggi.domain.board.BoardSetUp;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanSoldier;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Point;
import janggi.domain.piece.Soldier;
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

    public GameEntity findRunningGame() {
        return gameDao.findByStatus(Status.RUN);
    }

    public void createGame(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        gameDao.addGame(new GameEntity(Status.RUN, Dynasty.CHU));
        GameEntity gameEntity = gameDao.findByStatus(Status.RUN);
        JanggiBoard janggiBoard = JanggiBoard.of(hanBoardSetUp, chuBoardSetUp);
        pieceDao.addPieces(createPieceEntities(gameEntity, janggiBoard));
    }

    public JanggiStatus findJanggiStatusByGameId(Long gameId) {
        GameEntity gameEntity = findByIdOrThrow(gameId);
        return JanggiStatus.of(gameEntity.getCurrentTurn(), findJanggiBoardByGameId(gameEntity.getId()));
    }

    public JanggiStatus move(Long gameId, Point from, Point to) {
        GameEntity gameEntity = findByIdOrThrow(gameId);
        JanggiBoard janggiBoard = findJanggiBoardByGameId(gameId);

        JanggiStatus janggiStatus = JanggiStatus.of(gameEntity.getCurrentTurn(), janggiBoard).move(from, to);

        pieceDao.deletePiece(gameId, to);
        pieceDao.updatePiece(gameId, from, to);
        pieceDao.deletePiece(gameId, from);
        if (janggiStatus.isEndGame()) {
            gameDao.updateStatus(gameId, Status.END);
            return janggiStatus;
        }
        gameDao.updateCurrentTurn(gameId, janggiStatus.currentTurn());
        return janggiStatus;
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
            if (pieceEntity.getPieceType() == PieceType.GENERAL) {
                pieces.put(pieceEntity.getPoint(), new General(pieceEntity.getDynasty()));
            }
            if (pieceEntity.getPieceType() == PieceType.CANNON) {
                pieces.put(pieceEntity.getPoint(), new Cannon(pieceEntity.getDynasty()));
            }
            if (pieceEntity.getPieceType() == PieceType.CHARIOT) {
                pieces.put(pieceEntity.getPoint(), new Chariot(pieceEntity.getDynasty()));
            }
            if (pieceEntity.getPieceType() == PieceType.ELEPHANT) {
                pieces.put(pieceEntity.getPoint(), new Elephant(pieceEntity.getDynasty()));
            }
            if (pieceEntity.getPieceType() == PieceType.GUARD) {
                pieces.put(pieceEntity.getPoint(), new Guard(pieceEntity.getDynasty()));
            }
            if (pieceEntity.getPieceType() == PieceType.HORSE) {
                pieces.put(pieceEntity.getPoint(), new Horse(pieceEntity.getDynasty()));
            }
            if (pieceEntity.getPieceType() == PieceType.SOLDIER) {
                pieces.put(pieceEntity.getPoint(), toSoldier(pieceEntity));
            }
        }
        return new JanggiBoard(pieces);
    }

    private Soldier toSoldier(PieceEntity pieceEntity) {
        if (pieceEntity.getDynasty() == Dynasty.HAN) {
            return new HanSoldier();
        }
        return new ChuSoldier();
    }

    private GameEntity findByIdOrThrow(Long gameId) {
        GameEntity gameEntity = gameDao.findById(gameId);
        if (gameEntity == null) {
            throw new IllegalArgumentException("id에 해당하는 게임이 존재하지 않습니다.");
        }
        return gameEntity;
    }
}
