package domain.board;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfos;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CANNOT_MOVE_SAME_POSITION = "[ERROR] 기물을 동일한 위치로 이동시킬 수 없습니다.";

    private final BoardStates boardStates;
    private final BoardSnapshots boardSnapshots;
    private CountryType turn;

    public Board(BoardStates boardStates, BoardSnapshots boardSnapshots, CountryType turn) {
        this.boardStates = boardStates;
        this.boardSnapshots = boardSnapshots;
        this.turn = turn;
    }

    public void validateFromPosition(Position from) {
        if (boardStates.getPieceCountryType(from) != turn) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void movePiece(Position from, Position to) {
        validateMoveSamePosition(from, to);
        Path path = boardStates.getPiecePath(from, to);

        boardStates.validatePieceMove(from, to, path);
        boardStates.changePiecePosition(from, to);
    }

    public void validateMoveSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException(CANNOT_MOVE_SAME_POSITION);
        }
    }

    public PieceInfos getPieceInfos() {
        return boardStates.getBoardStates();
    }

    public double calculateScore(CountryType countryType) {
        double score = boardStates.calculateScore(countryType);
        if (countryType == CountryType.HAN) {
            score += 1.5;
        }
        return score;
    }

    public boolean checkEndWithGeneralCaught() {
        return boardStates.isGeneralCaught();
    }

    public boolean checkEndWithBoardRepeat() {
        return boardSnapshots.appearSamePositionThreeTurn();
    }

    public void addBoardSnapshot(BoardSnapshot boardSnapshot) {
        boardSnapshots.addBoardSnapshot(boardSnapshot);
    }

    public void changeTurn() {
        turn = turn.anotherCountryType();
    }

    public CountryType getTurn() {
        return turn;
    }
}
