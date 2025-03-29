package janggi;

import janggi.dao.Game;
import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dao.PieceEntity;
import janggi.dao.Status;
import janggi.domain.JanggiRuned;
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
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;
import janggi.view.JanggiBoardView.Movement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final InitializeView initializeView;
    private final JanggiBoardView janggiBoardView;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiGame(InitializeView initializeView, JanggiBoardView janggiBoardView, GameDao gameDao,
                      PieceDao pieceDao) {
        this.initializeView = initializeView;
        this.janggiBoardView = janggiBoardView;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public void start() {
        try {
            Game game = gameDao.findByStatus(Status.RUN);
            if (game == null) {
                gameDao.addGame(new Game(Status.RUN, Dynasty.CHU));
                game = gameDao.findByStatus(Status.RUN);
                initializeJanggiBoard(game);
            }
            play(game);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void play(Game game) {
        List<PieceEntity> pieceEntities = pieceDao.findAllByGameId(game.getId());
        JanggiBoard janggiBoard = createJanggiBoard(pieceEntities);
        janggiBoardView.printGameStartMessage();
        janggiBoardView.printBoard(janggiBoard.getPieces());

        JanggiStatus janggiStatus = new JanggiRuned(game.getCurrentTurn(), janggiBoard);
        while (!janggiStatus.isEndGame()) {
            try {
                Movement movement = janggiBoardView.readPlayerMove(janggiStatus.currentTurn());
                Point from = new Point(movement.startX(), movement.startY());
                Point to = new Point(movement.endX(), movement.endY());

                janggiStatus = janggiStatus.play(from, to);
                pieceDao.updatePiece(game.getId(), from, to);
                pieceDao.deletePiece(game.getId(), from);
                if (!janggiStatus.isEndGame()) {
                    gameDao.updateCurrentTurn(game.getId(), janggiStatus.currentTurn());
                }
                janggiBoardView.printBoard(janggiBoard.getPieces());
                janggiBoardView.printScore(janggiBoard);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        janggiBoardView.printResult(janggiStatus.winner(), janggiBoard);
    }

    private void initializeJanggiBoard(Game game) {
        BoardSetUp chuPlayerBoardSetUp = initializeView.readBoardSetUp(Dynasty.CHU);
        BoardSetUp hanPlayerBoardSetUp = initializeView.readBoardSetUp(Dynasty.HAN);
        JanggiBoard janggiBoard = JanggiBoard.of(hanPlayerBoardSetUp, chuPlayerBoardSetUp);
        pieceDao.addPieces(createPieceEntities(game, janggiBoard));
    }

    private JanggiBoard createJanggiBoard(List<PieceEntity> pieceEntities) {
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

    private List<PieceEntity> createPieceEntities(Game game, JanggiBoard janggiBoard) {
        Map<Point, Piece> pieces = janggiBoard.getPieces();
        List<PieceEntity> pieceEntities = new ArrayList<>();
        for (Point point : pieces.keySet()) {
            Piece piece = pieces.get(point);
            pieceEntities.add(new PieceEntity(point, piece.getDynasty(), piece.pieceType(), game.getId()));
        }
        return pieceEntities;
    }
}
