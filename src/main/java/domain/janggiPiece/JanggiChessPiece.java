package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.List;

public abstract class JanggiChessPiece {

    private final JanggiTeam team;

    protected JanggiChessPiece(JanggiTeam team) {
        this.team = team;
    }

    abstract public List<Path> getCoordinatePaths(JanggiPosition startPosition);

    abstract public HurdlePolicy getHurdlePolicy();

    abstract public Piece getChessPieceType();

    public final JanggiTeam getTeam() {
        return team;
    }

    public final Score getScore() {
        return getChessPieceType().score;
    }
}
