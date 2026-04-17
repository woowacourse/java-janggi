package repository;

import domain.dto.JanggiBoardDto;

public interface JanggiRepository {
    long save(JanggiBoardDto boardDto);
    void updateMove(long boardId, int fromRow, int fromCol, int toRow, int toCol);
    void updateTurn(long boardId, String turn);
    void finish(long boardId);

    JanggiBoardDto findAllPieces(long boardId);
    String findTurn(long boardId);
    long findLatestPlayingId();
}
