package janggi.game;

import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final List<AttackedPiece> attackedPieces;
    private Team turn;

    public Game() {
        this.board = Board.init();
        this.attackedPieces = new ArrayList<>();
        this.turn = Team.CHO;
    }

    public Piece findMovingPiece(Point movingPoint) {
        Piece movingPiece = board.findByPoint(movingPoint);
        validatePieceTeam(movingPiece);
        return movingPiece;
    }

    private void validatePieceTeam(Piece movingPiece) {
        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
    }

    public void move(Piece movingPiece, Point targetPoint) {
        AttackedPiece attackedPiece = board.move(movingPiece, targetPoint);
        if (attackedPiece.exists()) {
            attackedPieces.add(attackedPiece);
        }
    }

    public void reverseTurn() {
        this.turn = turn.reverse();
    }

    public boolean canContinue() {
        return attackedPieces.stream().noneMatch(AttackedPiece::isGung);
    }

    public Board getBoard() {
        return board;
    }

    public Team getTurn() {
        return turn;
    }

    public Team findWinner() {
        return attackedPieces.stream()
                .filter(AttackedPiece::isGung)
                .map(piece -> piece.getTeam().reverse())
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
