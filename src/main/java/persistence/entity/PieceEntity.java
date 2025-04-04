package persistence.entity;

import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;

public record PieceEntity(
        Long id,
        int column,
        int row,
        PieceType type,
        Team team,
        Score score,
        Long janggiGameId
) {

    public PieceEntity(int column, int row, PieceType type, Team team, Score score, Long janggiGameId) {
        this(null, column, row, type, team, score, janggiGameId);
    }
}
