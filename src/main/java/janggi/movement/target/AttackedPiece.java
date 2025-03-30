package janggi.movement.target;

import janggi.score.Score;
import janggi.game.Team;
import janggi.piece.Piece;

public class AttackedPiece {
    private final Piece attackedPiece;

    public AttackedPiece(Piece attackedPiece) {
        this.attackedPiece = attackedPiece;
    }

    public static AttackedPiece notAttacked() {
        return new AttackedPiece(null);
    }

    public boolean isGung() {
        return attackedPiece.isGung();
    }

    public boolean exists() {
        return attackedPiece != null;
    }

    public Team getTeam() {
        return attackedPiece.getTeam();
    }

    public Score getScore() {
        return attackedPiece.getScore();
    }

    public Piece getPiece() {
        if (attackedPiece == null) {
            throw new IllegalArgumentException("공격 받은 기물이 아닙니다.");
        }
        return attackedPiece;
    }
}
