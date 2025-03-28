package janggi.movement.target;

import janggi.game.Score;
import janggi.game.Team;
import janggi.piece.Piece;

public class AttackedPiece {
    private final Piece attackedPiece;

    public AttackedPiece(Piece attackedPiece) {
        this.attackedPiece = attackedPiece;
    }

    public static AttackedPiece notAttacked() {
        return new AttackedPiece(null); //TODO 해결
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
}
