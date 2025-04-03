package janggi.board;

import janggi.coordinate.JanggiPosition;
import janggi.piece.Country;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board implements VisibleBoard {

    private static final int ALL_GENERAL_COUNT = 2;

    private final Map<JanggiPosition, Piece> janggiBoard;

    public Board(final Map<JanggiPosition, Piece> janggiBoard) {
        this.janggiBoard = new HashMap<>(janggiBoard);
    }

    public static Board createInitializedJanggiBoard() {
        final Map<JanggiPosition, Piece> initMap = new HashMap<>();

        for (final PieceInitialPosition pieceType : PieceInitialPosition.values()) {
            initMap.putAll(pieceType.makeInitPieces(Country.CHO));
            initMap.putAll(pieceType.makeInitPieces(Country.HAN));
        }

        return new Board(initMap);
    }

    public void updatePosition(final JanggiPosition source, final JanggiPosition destination, final Country currentTurnCountry) {
        validateExistsPieceInPosition(source);
        validateIsCurrentCountry(source, currentTurnCountry);
        validatePieceCanMove(source, destination);

        movePieceToDestination(source, destination);
    }

    private void validateExistsPieceInPosition(final JanggiPosition source) {
        if (!janggiBoard.containsKey(source)) {
            throw new IllegalArgumentException("scr 좌표에 기물이 존재하지 않습니다.");
        }
    }

    private void validateIsCurrentCountry(final JanggiPosition source, final Country country) {
        if (!janggiBoard.get(source).equalsCountry(country)) {
            throw new IllegalArgumentException("현재 턴에 해당하는 기물이 아닙니다.");
        }
    }

    private void validatePieceCanMove(final JanggiPosition source, final JanggiPosition destination) {
        final Piece piece = janggiBoard.get(source);

        if (!piece.isAbleToMove(source, destination, this)) {
            throw new IllegalArgumentException("해당 기물은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private void movePieceToDestination(final JanggiPosition source, final JanggiPosition destination) {
        janggiBoard.put(destination, janggiBoard.get(source));
        janggiBoard.remove(source);
    }

    public boolean isAliveAllGenerals() {
        return janggiBoard.values().stream()
                .filter(Piece::isGeneral)
                .count() == ALL_GENERAL_COUNT;
    }

    public JanggiScore calculateScore(final Country country) {
        final Country countryOfEnemy = country.toggleCountry();

        final Pieces piecesByCountry = findAllByCountry(countryOfEnemy);
        return piecesByCountry.calculateAllScoreByCountry(country);
    }

    public Country findWinnerCountry(){
        final Pieces piecesOfHan = findAllByCountry(Country.HAN);
        final Pieces piecesOfCho = findAllByCountry(Country.CHO);
        final JanggiScore scoreOfHan = piecesOfHan.calculateAllScoreByCountry(Country.HAN);
        final JanggiScore scoreOfCho = piecesOfCho.calculateAllScoreByCountry(Country.CHO);

        if(scoreOfHan.isGreaterThan(scoreOfCho)){
            return Country.HAN;
        }
        return Country.CHO;
    }

    private Pieces findAllByCountry(final Country country) {
        return new Pieces(janggiBoard.values().stream()
                .filter(piece -> piece.equalsCountry(country))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean existPieceByPosition(final JanggiPosition janggiPosition) {
        return janggiBoard.containsKey(janggiPosition);
    }

    @Override
    public boolean isCannonByPosition(final JanggiPosition janggiPosition) {
        if (janggiBoard.containsKey(janggiPosition)) {
            final Piece piece = janggiBoard.get(janggiPosition);
            return piece.isCannon();
        }
        return false;
    }

    @Override
    public boolean containsCannonByPositions(final List<JanggiPosition> janggiPositions) {
        return janggiPositions.stream()
                .filter(janggiBoard::containsKey)
                .map(janggiBoard::get)
                .anyMatch(Piece::isCannon);
    }

    @Override
    public boolean equalsTeamTypeByPosition(final JanggiPosition janggiPosition, final Country country) {
        if (janggiBoard.containsKey(janggiPosition)) {
            final Piece piece = janggiBoard.get(janggiPosition);
            return piece.equalsCountry(country);
        }
        return false;
    }

    @Override
    public int calculatePieceCountByPositions(final List<JanggiPosition> janggiPositions) {
        return (int) janggiPositions.stream()
                .filter(janggiBoard::containsKey)
                .count();
    }

    public Map<JanggiPosition, Piece> getJanggiBoard() {
        return new HashMap<>(janggiBoard);
    }

}
