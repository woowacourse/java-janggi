package janggi.domain.board;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.board.strategy.BoardAssembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int FIRST_ROW_INDEX = 0;

    private final int height;
    private final int width;
    private final Map<Location, Piece> boardState;

    private Board(int height, int width, Map<Location, Piece> boardState) {
        this.height = height;
        this.width = width;
        this.boardState = boardState;
    }

    public static Board create(BoardAssembler assembler) {
        Piece[][] pieces = assembler.assemble();
        Map<Location, Piece> boardState = new HashMap<>();

        int height = pieces.length;
        int width = pieces[FIRST_ROW_INDEX].length;

        for (int row = 0; row < height; row++) {
            mapRowToBoardState(boardState, pieces[row], row);
        }

        return new Board(height, width, boardState);
    }

    private static void mapRowToBoardState(Map<Location, Piece> boardState, Piece[] rowPieces, int row) {
        for (int col = 0; col < rowPieces.length; col++) {
            boardState.put(new Location(row, col), rowPieces[col]);
        }
    }

    public void validateLocationOfPiece(Side currentSide, Location locationOfPiece) {
        validateLocation(locationOfPiece);
        validatePieceExist(locationOfPiece);
        Piece piece = boardState.get(locationOfPiece);
        if (isNotSameSide(piece, currentSide)) {
            throw new IllegalArgumentException("본인 팀의 기물만 선택할 수 있습니다.");
        }
    }

    private void validateLocation(Location location) {
        if (!boardState.containsKey(location)) {
            throw new IllegalArgumentException("해당 좌표는 보드판에 존재하지 않습니다.");
        }
    }

    private void validatePieceExist(Location location) {
        if (boardState.get(location).isEmpty()) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
        }
    }

    public void move(Location from, Location to) {
        Piece piece = boardState.get(from);
        List<Piece> piecesOnPath = getPiecesOnRoute(piece, from, to);

        piece.detectCollision(piecesOnPath);

        executeMove(from, to, piece);
    }

    private List<Piece> getPiecesOnRoute(Piece piece, Location from, Location to) {
        return piece.calculateRoute(from, to).stream()
                .map(boardState::get)
                .toList();
    }

    private void executeMove(Location from, Location to, Piece piece) {
        boardState.put(to, piece);
        boardState.put(from, EmptyPiece.getInstance());
    }

    public void validateLocationToMove(Location startingLocation, Location locationToMove) {
        validateLocation(locationToMove);
        validateMovementOccurrence(startingLocation, locationToMove);
    }

    private void validateMovementOccurrence(Location from, Location to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("기물의 도착 위치는 출발 위치와 일치할 수 없습니다.");
        }
    }

    public boolean isNotEmpty() {
        return !boardState.values().stream()
                .allMatch(Piece::isEmpty);
    }

    public List<List<Piece>> to2DArray() {
        List<List<Piece>> pieces = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            pieces.add(createRows(row));
        }
        return List.copyOf(pieces);
    }

    private List<Piece> createRows(int row) {
        List<Piece> line = new ArrayList<>();
        for (int col = 0; col < width; col++) {
            Piece piece = boardState.get(new Location(row, col));
            line.add(piece);
        }
        return List.copyOf(line);
    }

    private boolean isNotSameSide(Piece piece, Side side) {
        return !piece.isSameSide(side);
    }
}
