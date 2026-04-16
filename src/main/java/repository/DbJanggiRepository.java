package repository;

import db.BoardDao;
import db.PieceDao;
import domain.dto.JanggiBoardDto;

public class DbJanggiRepository implements JanggiRepository {
    private final BoardDao boardDao = new BoardDao();
    private final PieceDao pieceDao = new PieceDao();

    @Override
    public long save(JanggiBoardDto boardDto) {
        long id = boardDao.create();
        pieceDao.saveAll(id, boardDto);
        return id;
    }

    @Override
    public void updateMove(long boardId, int fromRow, int fromCol, int toRow, int toCol) {
        pieceDao.move(boardId, fromRow, fromCol, toRow, toCol);
    }

    @Override
    public void updateTurn(long boardId, String turn) {
        boardDao.updateTurn(boardId, turn);
    }

    @Override
    public void finish(long boardId) {
        boardDao.finish(boardId);
    }

    @Override
    public JanggiBoardDto findAllPieces(long boardId) {
        return pieceDao.findAll(boardId);
    }

    @Override
    public String findTurn(long boardId) {
        return boardDao.findTurn(boardId);
    }

    @Override
    public long findLatestPlayingId() {
        return boardDao.findLatestPlaying();
    }
}
