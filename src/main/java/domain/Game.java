package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.state.Side;
import domain.state.GameState;

import java.util.List;
import java.util.Map;

public class Game {

    private static final double FIRST_MOVE_BONUS = 1.5;

    private Long id;
    private final Board board;
    private GameState gameState;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize());
        this.gameState = Side.firstMoveSide();
    }

    public Game(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    public void end() {
        gameState = gameState.endGame();
    }

    public void pass() {
        gameState = gameState.nextTurn();
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
    }

    public boolean isKingDead() {
        return !board.hasKing(gameState.getSide().opposite());
    }

    public double calculateScore(Side side) {
        return board.calculateScore(side) + side.getBonusScore();
    }

    public boolean isSafe() {
        return board.isSafe(gameState.getSide().opposite());
    }

    public boolean isCheckmate() {
        return board.isCheckmate(gameState.getSide().opposite());
    }

    public boolean isFinished() {
        return gameState.isFinished();
    }

    public Position getEndPosition(int index, List<Position> possibleMoves) {
        return possibleMoves.get(index);
    }

    public Side getSide() {
        return gameState.getSide();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    private void validateEnsureSameSidePiece(Position start) {
        if (board.isOpponentSide(start, gameState.getSide())) {
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
