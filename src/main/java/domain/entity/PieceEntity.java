package domain.entity;

import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;

public class PieceEntity {

    private final Long id;

    private final int column;

    private final int row;

    private final PieceType type;

    private final Team team;

    private final Score score;

    private final Long janggiGameId;

    public PieceEntity(Long id, int column, int row, PieceType type, Team team, Score score, Long janggiGameId) {
        this.id = id;
        this.column = column;
        this.row = row;
        this.type = type;
        this.team = team;
        this.score = score;
        this.janggiGameId = janggiGameId;
    }

    public PieceEntity(int column, int row, PieceType type, Team team, Score score, Long janggiGameId) {
        this(null, column, row, type, team, score, janggiGameId);
    }

    public PieceEntity(int column, int row, PieceType type, Team team, Score score) {
        this(null, column, row, type, team, score, null);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public PieceType getType() {
        return type;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public Long getJanggiGameId() {
        return janggiGameId;
    }
}
