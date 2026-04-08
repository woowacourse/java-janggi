package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.state.ChuSide;
import domain.state.Side;
import domain.state.State;

import java.util.List;
import java.util.Map;

public class Game {

    private static final double FIRST_MOVE_BONUS = 1.5;

    private Long id;
    private final Board board;
    private State state;
    private Side firstMover;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize());
        this.state = createFirstTurnSide();
        this.firstMover = state.getSide();
    }

    public Game(Board board, State state) {
        this.board = board;
        this.state = state;
    }

    public void end() {
        state = state.endGame();
    }

    public void pass() {
        state = state.nextTurn();
    }

    public Position validateMoveable(Position position) {
        validateEnsureSameSidePiece(position);
        validateHasPossibleMoves(position);
        return position;
    }

    public List<Position> getPossibleMoves(Position start) {
        return board.calculateLegalMoves(start);
    }

    public void movePiece(Position start, Position dest) {
        board.movePiece(start, dest);

        if (board.hasKing(state.getSide().opposite())) {
            state = state.nextTurn();
        }
    }

    public boolean isKingDead() {
        return !board.hasKing(state.getSide().opposite());
    }

    public double calculateScore(Side side) {
        double score = board.calculateScore(side);

        if (side == firstMover) {
            score += FIRST_MOVE_BONUS;
        }

        return score;
    }

    public boolean isSafe() {
        return board.isSafe(state.getSide());
    }

    public boolean isCheckmate() {
        return board.isCheckmate(state.getSide());
    }

    public boolean isFinished() {
        return state.isFinished();
    }

    public State createFirstTurnSide() {
        return new ChuSide();
    }

    public Position getEndPosition(int index, List<Position> possibleMoves) {
        return possibleMoves.get(index);
    }

    public Side getSide() {
        return state.getSide();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    private void validateEnsureSameSidePiece(Position start) {
        if (board.isOpponentSide(start, state.getSide())) {
            throw new IllegalArgumentException("\n아군 기물만 이동 가능합니다. 다시 입력해주세요.");
        }
    }

    private void validateHasPossibleMoves(Position position) {
        if (getPossibleMoves(position).isEmpty()) {
            throw new IllegalArgumentException("\n해당 기물은 움직일 수 있는 좌표가 없습니다. 다른 기물을 선택해주세요.");
        }
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
