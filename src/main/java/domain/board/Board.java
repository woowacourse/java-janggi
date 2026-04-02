package domain.board;

import domain.country.Countries;
import domain.country.Country;
import domain.country.CountryType;
import domain.Path;
import domain.Position;
import domain.piece.PieceInfo;
import java.util.Map;

public class Board {
    private static final String NOT_MY_PIECE = "[ERROR] 본인 진영의 기물이 아닙니다.";
    private static final String CANNOT_MOVE_SAME_POSITION = "[ERROR] 기물을 동일한 위치로 이동시킬 수 없습니다.";

    private final BoardStates boardStates;
    private final Countries countries;

    public Board(BoardStates boardStates) {
        this.boardStates = boardStates;
        this.countries = new Countries();
    }

    public void validateFromPosition(Position from, CountryType countryType) {
        if (boardStates.getPieceCountryType(from) != countryType) {
            throw new IllegalArgumentException(NOT_MY_PIECE);
        }
    }

    public void move(Position from, Position to) {
        validateMoveSamePosition(from, to);
        Path path = boardStates.getPiecePath(from, to);

        boardStates.validatePieceMove(from, path);
        if (!boardStates.isEmpty(to)) {
            CountryType countryType = boardStates.getPieceCountryType(to);
            Country country = countries.findCountryByCountryType(countryType);
            boardStates.adjustScore(to, country);
        }
        boardStates.changeState(from, to);
    }

    public void validateMoveSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException(CANNOT_MOVE_SAME_POSITION);
        }
    }

    public Map<Position, PieceInfo> getPieceInfos() {
        return boardStates.getPieceInfos();
    }

    public Map<CountryType, Double> getScores() {
        return countries.getScores();
    }
}
