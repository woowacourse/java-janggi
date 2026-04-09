package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.Map;
import java.util.Optional;

public class Janggi {
    private final Board board;
    private Camp currentCamp;
    private boolean running;

    private Janggi(Board board, Camp currentCamp, boolean running) {
        this.board = board;
        this.currentCamp = currentCamp;
        this.running = running;
    }

    public Janggi(Janggi original) {
        this(original.board.clone(), original.currentCamp, original.running);
    }

    private Janggi(Board board) {
        this(board, Camp.CHO, true);
    }

    public static Janggi start(Board board) {
        return new Janggi(board);
    }

    public static Janggi load(Board board, Camp currentCamp, boolean running) {
        return new Janggi(board, currentCamp, running);
    }

    public void play(Position from, Position to) {
        board.movePiece(from, to);
        if (!board.isAliveEssentialPiece(currentCamp.next())) {
            finish();
            return;
        }
        currentCamp = currentCamp.next();
    }

    public void validateTurn(Position from) {
        Piece piece = board.selectPiece(from);
        if (!piece.isSameCamp(currentCamp)) {
            throw new IllegalArgumentException("자신의 기물만 선택할 수 있습니다.");
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void finish() {
        running = false;
    }

    public Camp currentCamp() {
        return currentCamp;
    }

    public void surrender() {
        currentCamp = currentCamp.next();
        finish();
    }

    public void draw() {
        currentCamp = board.calculateScoreResult();
        finish();
    }

    public Janggi clone(){
        return new Janggi(this);
    }

    public Camp winner() {
        return currentCamp;
    }

    public Map<Position, Piece> getBoard() {
        return board.janggiBoard();
    }
}
