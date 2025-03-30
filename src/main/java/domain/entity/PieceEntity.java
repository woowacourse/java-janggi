package domain.entity;

import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;

public class PieceEntity {

    private Long id;

    private int x;

    private int y;

    private PieceType type;

    private Team team;

    private Score score;

    private Long janggiGameId;

    public PieceEntity(Long id, int x, int y, PieceType type, Team team, Score score, Long janggiGameId) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.team = team;
        this.score = score;
        this.janggiGameId = janggiGameId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
}
