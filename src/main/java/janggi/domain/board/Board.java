package janggi.domain.board;

import janggi.exception.ErrorException;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    public static final int MIN_COLUMN = 0;
    public static final int MAX_COLUMN = 9;
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 10;
    private static final Double FIRST_TURN_SCORE = 0.0;
    private static final Double SECOND_TURN_SCORE = 1.5;
    private static final Map<Camp, Position> PALACE_POSITIONS = Map.of(
            Camp.CHO, Position.of(4, 1),
            Camp.HAN, Position.of(4, 8)
    );

    private final Map<Position, Piece> cells;
    private Camp currentCamp;

    public Board(Map<Position, Piece> cells, Camp currentCamp) {
        this.cells = cells;
        this.currentCamp = currentCamp;
    }

    public void placePiece(Position position, Piece piece) {
        cells.put(position, piece);
    }

    public void move(Movement movement) {
        validateTurn(movement);
        validateMove(movement);
        updateCellsAfterMove(movement);
        currentCamp = currentCamp.switchTurn();
    }

    private void validateTurn(Movement movement) {
        Piece originPiece = getOriginPiece(movement);
        if (originPiece.isOppositeCampTo(currentCamp)) {
            throw new ErrorException("현재 턴에 해당하지 않는 진영의 기물을 선택할 수 없습니다.");
        }
    }

    private void validateMove(Movement movement) {
        Piece originPiece = getOriginPiece(movement);
        Piece targetPiece = getTargetPiece(movement);
        originPiece.validateCatch(targetPiece);
        originPiece.validateMove(movement, this);
    }

    private void updateCellsAfterMove(Movement movement) {
        cells.put(movement.target(), getOriginPiece(movement));
        cells.put(movement.origin(), Empty.INSTANCE);
    }

    private Piece getOriginPiece(Movement movement) {
        Position position = movement.origin();
        Piece piece = getPieceByPosition(position);
        if (piece.isEmpty()) {
            throw new ErrorException("해당 위치에 기물이 존재하지 않습니다.");
        }
        return piece;
    }

    private Piece getTargetPiece(Movement movement) {
        Position position = movement.target();
        return getPieceByPosition(position);
    }

    private Piece getPieceByPosition(Position position) {
        return cells.get(position);
    }

    public void validateCampPalace(Position piecePosition, Camp baseCamp) {
        Position palaceCenter = PALACE_POSITIONS.get(baseCamp);
        List<Position> surroundingPositions = findPalacePositions(palaceCenter.x(), palaceCenter.y());
        if (!surroundingPositions.contains(piecePosition)) {
            throw new ErrorException("궁성 안에서 이동해야 합니다.");
        }
    }

    private List<Position> findPalacePositions(int centerX, int centerY) {
        List<Integer> directions = List.of(-1, 0, 1);
        return directions.stream()
                .flatMap(dx -> directions.stream().map(dy -> Position.of(centerX + dx, centerY + dy)))
                .toList();
    }

    public Set<Piece> getPiecesByPosition(Set<Position> route) {
        Set<Piece> pieces = new HashSet<>();
        for (Position position : route) {
            Piece piece = cells.get(position);
            if (!piece.isEmpty()) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public Camp determineWinner() {
        for (Camp camp : Camp.values()) {
            Set<Piece> pieces = filterPiecesByCamp(camp);
            if (!isGeneralAlive(pieces)) {
                return camp.switchTurn();
            }
        }
        return Camp.NONE;
    }

    private Set<Piece> filterPiecesByCamp(Camp camp) {
        return cells.values()
                .stream()
                .filter(piece -> piece.getCamp() == camp)
                .collect(Collectors.toSet());
    }

    private boolean isGeneralAlive(Set<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> piece.getType() == Type.GENERAL)
                .count() == 1;
    }

    public Map<Camp, Double> determineScores(Camp firstTurn) {
        Map<Camp, Double> scores = new EnumMap<>(Camp.class);
        initializeScores(scores, firstTurn);
        calculateScores(scores);
        return scores;
    }

    private void initializeScores(Map<Camp, Double> scores, Camp firstTurn) {
        scores.put(firstTurn, FIRST_TURN_SCORE);
        scores.put(firstTurn.switchTurn(), SECOND_TURN_SCORE);
    }

    private void calculateScores(Map<Camp, Double> scores) {
        for (Piece piece : cells.values()) {
            if (!piece.isEmpty()) {
                Camp camp = piece.getCamp();
                scores.merge(camp, piece.getType().getPoint(), Double::sum);
            }
        }
    }

    public Map<Position, Piece> getCells() {
        return cells;
    }

    public Camp getCurrentCamp() {
        return currentCamp;
    }
}
