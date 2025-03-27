package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.position.JanggiPosition;
import domain.score.Score;
import domain.type.JanggiTeam;
import domain.path.Path;

import java.util.List;

public abstract class JanggiChessPiece implements JanggiPiece {

    private final JanggiTeam team;

    protected JanggiChessPiece(JanggiTeam team) {
        this.team = team;
    }

    @Override
    abstract public List<Path> getCoordinatePaths(JanggiPosition startPosition);

    @Override
    abstract public HurdlePolicy getHurdlePolicy();

    @Override
    public final JanggiTeam getTeam() {
        return team;
    }

    @Override
    public final Score getScore() {
        return getChessPieceType().score;
    }
}
