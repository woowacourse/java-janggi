package domain.chessPiece;

import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessTeam;

import java.util.Objects;

public abstract class JanggiChessPiece implements ChessPiece {

    private final ChessPosition position;
    private final ChessTeam team;

    protected JanggiChessPiece(final ChessPosition position, final ChessTeam team) {
        this.position = position;
        this.team = team;
    }

    protected JanggiChessPiece(final ChessTeam team) {
        this.team = team;
        this.position = null;
    }

    @Override
    public final ChessTeam getTeam() {
        return team;
    }

    @Override
    public final Score getScore() {
        return getChessPieceType().score;
    }

    @Override
    public boolean matchPosition(final ChessPosition position) {
        return Objects.equals(position, this.position);
    }

    @Override
    public ChessPosition getPosition() {
        return position;
    }
}
