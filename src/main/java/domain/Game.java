package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Position;

import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private Side turn;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize(), boardInitializer.createTopology());
        this.turn = boardInitializer.getFirstTurnSide();
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
        return board.toSnapshotMap().values().stream()
                .filter(cell -> cell.type() == PieceType.KING)
                .map(CellSnapshot::side)
                .distinct()
                .count() < 2;
    }

    public Side getWinner() {
        if (!isGameOver()) {
            throw new IllegalStateException("게임이 종료되지 않았습니다.");
        }
        return board.toSnapshotMap().values().stream()
                .filter(cell -> cell.type() == PieceType.KING)
                .map(CellSnapshot::side)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
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

    public Map<Position, CellSnapshot> getBoardSnapshotMap() {
        return board.toSnapshotMap();
    }
}
