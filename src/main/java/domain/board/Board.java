package domain.board;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfos;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CANNOT_MOVE_SAME_POSITION = "[ERROR] 기물을 동일한 위치로 이동시킬 수 없습니다.";

    private final BoardStates boardStates;

    public Board(BoardStates boardStates) {
        this.boardStates = boardStates;
    }

    public void validateFromPosition(Position from, CountryType countryType) {
        if (boardStates.getPieceCountryType(from) != countryType) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public boolean checkEndAndPlay(Position from, Position to) {
        validateMoveSamePosition(from, to);
        Path path = boardStates.getPiecePath(from, to);

        boardStates.validatePieceMove(from, to, path);
        boolean isGeneralCaught = boardStates.isGeneralCaught(to);
        boardStates.changePiecePosition(from, to);
        return isGeneralCaught;
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
}
