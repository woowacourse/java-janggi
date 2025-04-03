package dao;

import java.util.List;
import java.util.Map;

import board.Board;
import board.BoardInitializer;
import board.Position;
import game.Turn;
import piece.Piece;

public class DaoService {

    private final PieceDao pieceDao;
    private final TurnDao turnDao;

    public DaoService(final PieceDao pieceDao, final TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.turnDao = turnDao;
    }

    public Board findBoard() {
        List<PieceEntity> pieceEntities = pieceDao.findAll();
        if (pieceEntities.isEmpty()) {
            BoardInitializer boardInitializer = new BoardInitializer();
            Board board = new Board(boardInitializer.init());
            List<PieceEntity> entitiesToSave = createPieceEntities(board.getPieces());
            pieceDao.saveAll(entitiesToSave);
            return board;
        }
        return new Board(PieceConverter.toPieces(pieceEntities));
    }

    private List<PieceEntity> createPieceEntities(final Map<Position, Piece> pieces) {
        return pieces.entrySet()
                .stream()
                .map(entry -> PieceConverter.toEntity(entry.getKey(), entry.getValue()))
                .toList();
    }

    public Turn findTurn() {
        if (turnDao.exists()) {
            return TurnConverter.toTurn(turnDao.find());
        }
        Turn turn = new Turn();
        turnDao.save(TurnConverter.toEntity(turn));
        return turn;
    }

    public void updateTurn(final TurnEntity entity) {
        turnDao.update(entity);
    }

    public void removeAllGameData() {
        pieceDao.removeAll();
        turnDao.removeAll();
    }

    public void removeAndUpdatePosition(final Position startPosition, final Position destinationPosition) {
        pieceDao.removeAndUpdatePosition(startPosition, destinationPosition);
    }

}
