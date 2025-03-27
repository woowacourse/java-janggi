package janggi.board;

import janggi.coordinate.Position;
import janggi.piece.Country;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements VisibleBoard{

    private static final int ALL_GENERAL_COUNT = 2;

    private final Map<Position, Piece> janggiBoard;

    public Board(final Map<Position, Piece> janggiBoard) {
        this.janggiBoard = new HashMap<>(janggiBoard);
    }

    public static Board createInitializedJanggiBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();

        for (final PieceInitialPosition pieceType : PieceInitialPosition.values()) {
            initMap.putAll(pieceType.makeInitPieces(Country.CHO));
            initMap.putAll(pieceType.makeInitPieces(Country.HAN));
        }

        return new Board(initMap);
    }

    public void updatePosition(final Position source, final Position destination, final Country currentTurnCountry) {
        validateExistsPieceInPosition(source);
        validateIsCurrentCountry(source, currentTurnCountry);
        validatePieceCanMove(source, destination);

        movePieceToDestination(source, destination);
    }

    private void validateExistsPieceInPosition(final Position source) {
        if (!janggiBoard.containsKey(source)) {
            throw new IllegalArgumentException("scr 좌표에 기물이 존재하지 않습니다.");
        }
    }

    private void validateIsCurrentCountry(final Position source, final Country country){
        if (!janggiBoard.get(source).equalsCountry(country)) {
            throw new IllegalArgumentException("현재 턴에 해당하는 기물이 아닙니다.");
        }
    }

    private void validatePieceCanMove(final Position source, final Position destination) {
        final Piece piece = janggiBoard.get(source);

        if (!piece.isAbleToMove(source, destination, this)) {
            throw new IllegalArgumentException("해당 기물은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private void movePieceToDestination(final Position source, final Position destination) {
        janggiBoard.put(destination, janggiBoard.get(source));
        janggiBoard.remove(source);
    }

    public boolean isAliveAllGenerals() {
        return janggiBoard.values().stream()
                .filter(Piece::isGeneral)
                .count() == ALL_GENERAL_COUNT;
    }

    @Override
    public boolean existPieceByPosition(final Position position) {
        return janggiBoard.containsKey(position);
    }

    @Override
    public boolean isCannonByPosition(final Position position) {
        if (janggiBoard.containsKey(position)) {
            final Piece piece = janggiBoard.get(position);
            return piece.isCannon();
        }
        return false;
    }

    @Override
    public boolean containsCannonByPositions(final List<Position> positions){
        return positions.stream()
                .filter(janggiBoard::containsKey)
                .map(janggiBoard::get)
                .anyMatch(Piece::isCannon);
    }

    @Override
    public boolean equalsTeamTypeByPosition(final Position position, final Country country) {
        if (janggiBoard.containsKey(position)) {
            final Piece piece = janggiBoard.get(position);
            return piece.equalsCountry(country);
        }
        return false;
    }

    @Override
    public int calculatePieceCountByPositions(final List<Position> positions) {
        return (int) positions.stream()
                .filter(janggiBoard::containsKey)
                .count();
    }

    public Map<Position, Piece> getJanggiBoard() {
        return new HashMap<>(janggiBoard);
    }

    public Piece getWinner(){
        return janggiBoard.values().stream()
                .filter(Piece::isGeneral)
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
