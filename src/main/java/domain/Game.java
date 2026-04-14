package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Position;

import java.util.List;
import java.util.Map;

public class Game {

    private Side turn;
    private final Board board;
    private final GameEndJudge gameEndJudge;
    private final ScoreCalculator scoreCalculator;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize(), boardInitializer.createTopology());
        this.turn = boardInitializer.getFirstTurnSide();
        this.gameEndJudge = new GameEndJudge();
        this.scoreCalculator = new ScoreCalculator();
    }

    public void validateStartPosition(Position start) {
        validateCurrentTurnPiece(start);
    }

    public void move(Position start, Position destination) {
        validateCurrentTurnPiece(start);
        board.move(start, destination);
        changeTurn();
    }

    public boolean isGameOver() {
        return gameEndJudge.isGameOver(board.getBoardPiecesPosition());
    }

    public Side getWinner() {
        return gameEndJudge.getWinner(board.getBoardPiecesPosition());
    }

    public double calculateScore(Side side) {
        return scoreCalculator.calculate(board.getBoardPiecesPosition(), side);
    }

    private boolean isFriendlyPiece(Position position) {
        return board.getPiece(position).isFriendly(turn);
    }

    private boolean isOpponentOrEmptyPiece(Position position) {
        return !isFriendlyPiece(position);
    }

    private void validateCurrentTurnPiece(Position start) {
        if (isOpponentOrEmptyPiece(start)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다.");
        }
    }

    private void changeTurn() {
        turn = turn.change();
    }

    public Side getTurn() {
        return turn;
    }

    public List<Position> getPossibleMoves(Position start) {
        return board.getPossibleMoves(start);
    }

    public CellSnapshot[][] getBoardSnapshot() {
        return board.toSnapshot();
    }

    public Map<Position, CellSnapshot> getBoardPiecesPosition() {
        return board.getBoardPiecesPosition();
    }
}
