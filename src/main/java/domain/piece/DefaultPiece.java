package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;

public class DefaultPiece extends Piece{

    public DefaultPiece(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation target) {
        return;
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target) {
        return List.of();
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        return ;
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        return;
    }

    @Override
    public PieceType getType() {
        return PieceType.DEFAULT;
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
