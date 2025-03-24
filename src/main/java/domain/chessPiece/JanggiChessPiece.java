package domain.chessPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessTeam;
import domain.path.Path;

import java.util.List;

public abstract class JanggiChessPiece implements ChessPiece {

    private final ChessTeam team;

    protected JanggiChessPiece(ChessTeam team) {
        this.team = team;
    }

    @Override
    public final List<ChessPosition> getDestinations(ChessPosition startPosition, ChessPiecePositions positions) {
        List<Path> coordinates = getCoordinatePaths(startPosition);
        HurdlePolicy hurdlePolicy = getHurdlePolicy();
        return hurdlePolicy.pickDestinations(team, coordinates, positions);
    }


    @Override
    public abstract List<Path> getCoordinatePaths(ChessPosition startPosition);

    @Override
    public abstract HurdlePolicy getHurdlePolicy();

    @Override
    public final ChessTeam getTeam() {
        return team;
    }

    @Override
    public final Score getScore() {
        return getChessPieceType().score;
    }
}
