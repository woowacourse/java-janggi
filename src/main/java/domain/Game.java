package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constant.BoardConstant.*;
import static constant.ErrorMessage.*;

public class Game {

    private static final int BACK_Y = 0;
    private static final int KING_Y = 1;
    private static final int CANNON_Y = 2;
    private static final int SOLDIER_Y = 3;

    private static final List<Integer> CANNON_X = List.of(2, 8);
    private static final List<Integer> CHARIOT_X = List.of(1, 9);
    private static final List<Integer> GUARD_X = List.of(4, 6);
    private static final List<Integer> KING_X = List.of(5);
    private static final List<Integer> SOLIDER_X = List.of(1, 3, 5, 7, 9);
    private static final List<Integer> FORMATION_X = List.of(2, 3, 7, 8);

    private final Map<Position, Piece> board = new HashMap<>();
    private final Long id;
    private final Turn turn;

    public Game(Long id, Turn turn, Formation choFormation, Formation hanFormation) {
        createBoard(choFormation, hanFormation);
        this.id = id;
        this.turn = turn;
    }

    public Game(Long id, Turn turn, Map<Position, Piece> board) {
        this.board.putAll(board);
        this.id = id;
        this.turn = turn;
    }

    public List<Position> findPath(Position sourcePosition, Turn turn) {
        Piece piece = board.get(sourcePosition);
        validatePieceExists(piece);
        validateOwnPiece(piece, turn);
        return filterValidTargets(piece, sourcePosition);
    }

    public void movePiece(Position source, Position target, List<Position> availableTargets) {
        if (!availableTargets.contains(target)) {
            throw new IllegalArgumentException(INVALID_TARGET_POSITION);
        }
        Piece piece = board.get(source);
        board.put(target, piece);
        board.put(source, new Empty());
    }

    public ScoreStatus calculateScore() {
        return ScoreStatus.from(board.values());
    }

    public boolean isFinished() {
        return board.values().stream().filter(Piece::isKing).count() < 2;
    }

    public Long getId() {
        return id;
    }

    public Turn getTurn() {
        return turn;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    private void createBoard(Formation choFormation, Formation hanFormation) {
        for (int x = MIN_X; x <= MAX_X; x++) {
            for (int y = MIN_Y; y <= MAX_Y; y++) {
                placePiece(Position.of(x, y), PieceType.EMPTY.create(Side.NONE));
            }
        }
        placePieces(choFormation, Side.CHO);
        placePieces(hanFormation, Side.HAN);
    }

    private void placePieces(Formation formation, Side side) {
        List<Integer> rows = getRowForSide(side);
        placePiece(CANNON_X, rows.get(CANNON_Y), PieceType.CANNON, side);
        placePiece(CHARIOT_X, rows.get(BACK_Y), PieceType.CHARIOT, side);
        placePiece(GUARD_X, rows.get(BACK_Y), PieceType.GUARD, side);
        placePiece(KING_X, rows.get(KING_Y), PieceType.KING, side);
        placePiece(SOLIDER_X, rows.get(SOLDIER_Y), PieceType.SOLDIER, side);
        placeFormationPiece(formation, rows.get(BACK_Y), side);
    }

    private List<Integer> getRowForSide(Side side) {
        if(side == Side.HAN) {
            return List.of(1,2,3,4);
        }
        return List.of(10,9,8,7);
    }

    private void placePiece(Position position, Piece piece) {
        board.put(position, piece);
    }

    private void placePiece(List<Integer> xPositions, int y, PieceType pieceType, Side side) {
        for(int x : xPositions) {
            board.put(Position.of(x, y), pieceType.create(side));
        }
    }

    private void placeFormationPiece(Formation formation, int y, Side side) {
        List<PieceType> pieceTypes = formation.getPieceTypes();
        for(int i = 0; i < pieceTypes.size(); i++) {
            board.put(Position.of(FORMATION_X.get(i), y), pieceTypes.get(i).create(side));
        }
    }

    private List<Position> filterValidTargets(Piece piece, Position source) {
        List<Position> allTargets = piece.findRoute(source);
        List<Position> validTargets = new ArrayList<>();
        for (Position target : allTargets) {
            if (isValidMove(piece, source, target)) {
                validTargets.add(target);
            }
        }
        return validTargets;
    }

    private boolean isValidMove(Piece piece, Position source, Position target) {
        List<Piece> piecesOnPath = collectPiecesOnPath(piece, source, target);
        if (!piece.isValidRoute(piecesOnPath)) {
            return false;
        }
        Piece targetPiece = board.get(target);
        return piece.isValidTarget(targetPiece);
    }

    private List<Piece> collectPiecesOnPath(Piece piece, Position source, Position target) {
        List<Position> path = piece.findPathTo(source, target);
        List<Piece> pieces = new ArrayList<>();
        for (Position position : path) {
            pieces.add(board.get(position));
        }
        return pieces;
    }

    private void validatePieceExists(Piece piece) {
        if(piece.isEmpty()) {
            throw new IllegalArgumentException(PIECE_NOT_FOUND);
        }
    }

    private void validateOwnPiece(Piece piece, Turn turn) {
        if (!piece.getSide().equals(turn.current())) {
            throw new IllegalArgumentException(NOT_OWN_PIECE);
        }
    }
}
