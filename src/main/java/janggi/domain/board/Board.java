package janggi.domain.board;

import janggi.domain.space.Destinations;
import janggi.domain.space.Palace;
import janggi.domain.space.Position;
import janggi.domain.Score;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.space.Direction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardReader{
    public static final int MINIMUM_VITAL_PIECES_COUNT = 2;
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = Map.copyOf(board);
    }

    public Destinations findDestinations(Position position) {
        Piece piece = getPiece(position);
        return piece.findDestinations(position, this);
    }

    public Board movePiece(Position source, Position target) {
        findDestinations(source).validateDestinations(target);
        Map<Position, Piece> nextBoardMap = new HashMap<>(this.board);
        Piece movingPiece = nextBoardMap.remove(source);
        nextBoardMap.put(target, movingPiece);
        return new Board(nextBoardMap);
    }

    public boolean isPlaying() {
        return getVitalSides().size() >= MINIMUM_VITAL_PIECES_COUNT;
    }

    public Side getWinnerSide() {
        List<Side> vitalSides = getVitalSides();
        if (vitalSides.size() != 1) {
            throw new IllegalStateException("승리한 진영을 확정할 수 없는 상태입니다.");
        }
        return vitalSides.getFirst();
    }

    private List<Side> getVitalSides() {
        return board.values().stream()
                .filter(Piece::isVital)
                .map(Piece::getSide)
                .distinct()
                .toList();
    }

    public Score calculateScore(Side side) {
        return board.values().stream()
                .filter(piece -> piece.isAlly(side))
                .map(Piece::getScore)
                .reduce(new Score(0.0), Score::plus);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    @Override
    public boolean isEmpty(Position position) {
        return !board.containsKey(position);
    }

    @Override
    public boolean isAlly(Position position, Side side) {
        return getPiece(position).isAlly(side);
    }

    @Override
    public Piece getPiece(Position position) {
        Piece piece = board.get(position);
        if (piece == null) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
        return piece;
    }
    @Override
    public List<Direction> getPalaceDiagonals(Position position) {
        return Palace.getDiagonals(position);

    }

    @Override
    public boolean isInsidePalace(Position position) {
        return Palace.isInsideAny(position);
    }

    @Override
    public boolean isInsidePalace(Position position, Side side) {
        return Palace.isInside(position, side);
    }
}
