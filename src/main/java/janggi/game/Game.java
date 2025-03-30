package janggi.game;

import janggi.dto.MovementDto;
import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import janggi.point.Point;
import janggi.score.ScoreResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final List<AttackedPiece> attackedPieces;
    private Team turn;
    private final LocalDateTime createdAt;

    public Game() {
        this(Board.init(), new ArrayList<>(), Team.CHO, LocalDateTime.now());
    }

    public Game(Board board) {
        this(board, new ArrayList<>(), Team.CHO, LocalDateTime.now());
    }

    public Game(Board board, List<AttackedPiece> attackedPieces, Team turn, LocalDateTime createdAt) {
        this.board = board;
        this.attackedPieces = attackedPieces;
        this.createdAt = createdAt;
        this.turn = turn;
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

    public MovementDto move(Piece movingPiece, Point targetPoint) {
        MovementDto movement = board.move(movingPiece, targetPoint);
        if (movement.attackedPiece().exists()) {
            AttackedPiece attackedPiece = movement.attackedPiece();
            attackedPieces.add(attackedPiece);
        }
        return movement;
    }

    public ScoreResult calculateScore() {
        ScoreResult scoreResult = ScoreResult.initialize();
        scoreResult.aggregate(attackedPieces);
        return scoreResult;
    }

    public void reverseTurn() {
        this.turn = turn.reverse();
    }

    public boolean canContinue() {
        return attackedPieces.stream().noneMatch(AttackedPiece::isGung);
    }

    public Team findWinner() {
        return attackedPieces.stream()
                .filter(AttackedPiece::isGung)
                .map(piece -> piece.getTeam().reverse())
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public List<Piece> getRunningPieces() {
        return board.getRunningPieces();
    }

    public Board getBoard() {
        return board;
    }

    public Team getTurn() {
        return turn;

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
