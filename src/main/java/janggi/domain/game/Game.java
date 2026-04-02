package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

import janggi.domain.board.Board;
import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private final CurrentTurn currentTurn;

    private Game(Board board, CurrentTurn currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public static Game initGame(Map<Dynasty, HorseElephantPosition> horseElephantPositions) {
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(horseElephantPositions);
        return new Game(new Board(policy), new CurrentTurn(CHO));
    }

    public List<Position> canMovePosition(Position from) {
        List<Position> positions = board.canMovePosition(from, currentTurn.currentDynasty());
        if (positions.isEmpty()) {
            throw new IllegalStateException("선택된 기물이 이동할 수 있는 위치가 없습니다.");
        }
        return positions;
    }

    public void movePiece(Position from, Position to) {
        board.movePiece(from, to, currentTurn.currentDynasty());
        currentTurn.changeTurn();
    }

    public Dynasty judgeWinner() {
        if (isLessThan30()) {
            return findWinner();
        }
        if (board.hasGeneral(HAN)) {
            return HAN;
        }
        return CHO;
    }

    public boolean isFinished() {
        return isGeneralCaptured() || isLessThan30();
    }

    public Map<Position, Piece> boardMap() {
        return board.board();
    }

    public CurrentTurn currentTurn() {
        return currentTurn;
    }

    private boolean isGeneralCaptured() {
        return !board.hasGeneral(HAN) || !board.hasGeneral(CHO);
    }

    private boolean isLessThan30() {
        double pointsOfHan = board.sumPointsOf(HAN) + 1.5;
        double pointsOfCho = board.sumPointsOf(CHO);

        return pointsOfHan < 30 && pointsOfCho < 30;
    }

    private Dynasty findWinner() {
        double pointsOfHan = board.sumPointsOf(HAN) + 1.5;
        double pointsOfCho = board.sumPointsOf(CHO);

        if (pointsOfHan < pointsOfCho) {
            return CHO;
        }
        return HAN;
    }

}
