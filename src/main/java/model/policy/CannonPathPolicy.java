package model.policy;

import java.util.List;
import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;

public class CannonPathPolicy extends PathPolicy {
    private static final int JUMP_PIECE = 1;

    @Override
    public boolean validatePath(List<Position> path, Board board) {
        if (board.countPiecesOnPath(path) != JUMP_PIECE) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 1개 뛰어넘어야 합니다.");
        }

        if (board.hasPieceTypeOnPath(path, PieceType.CANNON)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
        }

        return true;
    }

    @Override
    public boolean validateDestination(Move move, Board board, Country country) {
        board.findPiece(move.to())
                .ifPresent(piece -> validatePiece(piece, country));

        return true;
    }

    void validatePiece(Piece toPiece, Country country) {
        if ((country != toPiece.country()
                && toPiece.pieceType() == PieceType.CANNON)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 먹을 수 없습니다.");
        }

        if (country == toPiece.country()) {
            throw new IllegalArgumentException("[ERROR] 아군 기물입니다.");
        }
    }
}
