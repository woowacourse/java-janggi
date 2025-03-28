package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Piece getPieceByPosition(final Position position) {
        return pieces.get(position);
    }

    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    public void movePiece(Position beforePosition, Position afterPosition) {
        Piece piece = pieces.get(beforePosition);
        try {
            validateMove(beforePosition, afterPosition);
            pieces.put(beforePosition, new None());
            Piece movedPiece = piece.move(getPieces(), afterPosition);
            pieces.put(afterPosition, movedPiece);
        } catch (IllegalArgumentException e) {
            pieces.put(piece.getPosition(), piece);
            System.out.println(e.getMessage());
        }
    }

    private void validateMove(Position beforePosition, Position afterPosition) {
        Piece piece = pieces.get(beforePosition);
        if (piece.isNone()) {
            throw new IllegalArgumentException("위치에 이동시킬 기물이 존재하지 않습니다.");
        }
        Piece other = pieces.get(afterPosition);
        if (piece.getTeam().equals(other.getTeam())) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    public Score getScore(Team team) {
        return Score.from(
                pieces.values()
                .stream()
                .filter(piece -> piece.getTeam() == team)
                .mapToInt(Piece::getScore)
                .sum(), team
        );
    }

    public boolean isKingAlive(Team team) {
        return pieces.values()
                .stream()
                .filter(Piece::isGeneral)
                .anyMatch(piece ->
                        piece.getTeam() == team);
    }
}
