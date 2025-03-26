package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public class Board {

    private final List<Piece> positionedPieces;

    public Board(List<Piece> positionedPieces) {
        this.positionedPieces = positionedPieces;
    }

    public void attack(Team turn, Position startPosition, Position arrivedPosition) {
        Piece selectedPiece = findByPosition(startPosition);
        checkTurn(turn, selectedPiece);
        selectedPiece.move(arrivedPosition, positionedPieces);
    }

    private void checkTurn(Team turn, Piece piece) {
        if (piece.isSameTeam(turn)) {
            return;
        }
        throw new IllegalArgumentException("순서를 확인하세요");
    }

    private Piece findByPosition(Position startPosition) {
        return positionedPieces.stream()
                .filter(piece -> piece.matchesPosition(startPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }

    public List<Piece> getPositionedPieces() {
        return positionedPieces;
    }
}
