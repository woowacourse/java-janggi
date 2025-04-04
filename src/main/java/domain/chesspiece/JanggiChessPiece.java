package domain.chesspiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessTeam;

import java.util.Objects;

public abstract class JanggiChessPiece implements ChessPiece {

    private final ChessPosition position;
    private final ChessTeam team;
    private final HurdlePolicy hurdlePolicy;

    protected JanggiChessPiece(final ChessPosition position, final ChessTeam team, final HurdlePolicy hurdlePolicy) {
        this.position = position;
        this.team = team;
        this.hurdlePolicy = hurdlePolicy;
    }

    @Override
    public final ChessTeam getTeam() {
        return team;
    }

    @Override
    public final Score getScore() {
        return getChessPieceType().getScore();
    }

    @Override
    public boolean matchPosition(final ChessPosition position) {
        return Objects.equals(position, this.position);
    }

    @Override
    public ChessPosition getPosition() {
        return position;
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public boolean matchTeam(final ChessTeam team) {
        return this.team == team;
    }
}
