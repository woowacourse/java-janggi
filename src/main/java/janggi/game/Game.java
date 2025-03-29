package janggi.game;

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
    private final LocalDateTime createdAt; //TODO dao로 옮길수 이씅면 좋겠다..

    public Game() {
        this(Board.init(), new ArrayList<>(), Team.CHO, LocalDateTime.now());
    }

    public Game(Board board) {
        this(board, new ArrayList<>(), Team.CHO, LocalDateTime.now());
    }

    public Game(LocalDateTime createdAt) {
        this(Board.init(), new ArrayList<>(), Team.CHO, createdAt);
    }

    private Game(Board board, List<AttackedPiece> attackedPieces, Team turn, LocalDateTime createdAt) {
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

    public void move(Piece movingPiece, Point targetPoint) {
        AttackedPiece attackedPiece = board.move(movingPiece, targetPoint);
        if (attackedPiece.exists()) {
            attackedPieces.add(attackedPiece);
        }
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
