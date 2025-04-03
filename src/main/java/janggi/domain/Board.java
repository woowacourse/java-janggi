package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;
    private Turn turn;

    public Board(Map<Position, Piece> pieces, Turn turn) {
        this.pieces = new HashMap<>(pieces);
        this.turn = turn;
    }

    public Piece getPieceByPosition(final Position position) {
        return pieces.get(position);
    }

    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    public void movePiece(Position beforePosition, Position afterPosition) {
        Piece piece = pieces.get(beforePosition);
        validateTurn(piece);
        try {
            validateMove(beforePosition, afterPosition);
            pieces.put(beforePosition, new None(beforePosition));
            Piece movedPiece = piece.move(getPieces(), afterPosition);
            pieces.put(afterPosition, movedPiece);
            turn = turn.getNextTurn();
        } catch (IllegalArgumentException e) {
            pieces.put(piece.getPosition(), piece);
            System.out.println(e.getMessage());
        }
    }

    private void validateTurn(Piece piece) {
        if (piece.getTeam() != turn.getTeam()) {
            throw new IllegalArgumentException("현재 이동가능한 팀 기물이 아닙니다");
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

    public TotalScore getScore(Team team) {
        return TotalScore.from(
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
            .filter(piece -> piece.getPieceType() == PieceType.GENERAL)
            .anyMatch(piece ->
                piece.getTeam() == team);
    }

    public Team getWinner() {
        if (isGameNotEnd()) {
            throw new IllegalArgumentException("게임이 끝나지 않았습니다.");
        }
        if (isKingAlive(Team.BLUE)) {
            return Team.BLUE;
        }
        if (isKingAlive(Team.RED)) {
            return Team.RED;
        }
        return Team.NONE;
    }

    public boolean isGameNotEnd() {
        return isKingAlive(Team.BLUE) && isKingAlive(Team.RED);
    }

    public boolean isGameEnd() {
        return !isGameNotEnd();
    }

    public Turn getTurn() {
        return turn;
    }
}
