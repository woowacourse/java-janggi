package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

public class EmptyPiece extends Piece {

    public static final String NOT_EXISTS_PIECE_MESSAGE = "해당 위치에 기물이 존재하지 않습니다.";

    public EmptyPiece(Team team) {
        super(team);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public Team team() {
        return Team.NONE;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        throw new PieceException(NOT_EXISTS_PIECE_MESSAGE);
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        throw new PieceException(NOT_EXISTS_PIECE_MESSAGE);
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        throw new PieceException(NOT_EXISTS_PIECE_MESSAGE);
    }

    @Override
    public void validateTarget(Piece target) {
        throw new PieceException(NOT_EXISTS_PIECE_MESSAGE);
    }
}
