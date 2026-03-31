package janggi.domain.board;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.exception.ErrorCode;
import janggi.exception.JanggiException;
import janggi.strategy.BoardAssembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Location, Piece> boardState;
    private final int height;
    private final int width;

    private Board(Map<Location, Piece> boardState, int height, int width) {
        this.boardState = boardState;
        this.height = height;
        this.width = width;
    }

    public static Board create(BoardAssembler assembler) {
        Piece[][] pieces = assembler.assemble();
        Map<Location, Piece> boardState = new HashMap<>();

        int height = pieces.length;
        int width = pieces[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                boardState.put(new Location(row, col), pieces[row][col]);
            }
        }

        return new Board(boardState, height, width);
    }

    public void move(Location from, Location to) {
        validateMove(from, to);

        Piece piece = boardState.get(from);
        List<Piece> piecesOnPath = getPiecesOnRoute(piece, from, to);

        piece.detectCollision(piecesOnPath);

        executeMove(from, to, piece);
    }

    public void validateLocationOfPiece(Side currentSide, Location locationOfPiece) {
        validateLocation(locationOfPiece);
        validatePieceExist(locationOfPiece);
        Piece piece = boardState.get(locationOfPiece);
        if (isNotSameSide(piece, currentSide)) {
            throw new IllegalArgumentException("본인 팀의 기물만 선택할 수 있습니다.");
        }
    }

    public boolean isNotEmpty() {
        return !boardState.values().stream()
                .allMatch(Piece::isEmpty);
    }

    public void validateLocationToMove(Side currentSide, Location locationToMove) {
        validateLocation(locationToMove);
        Piece target = boardState.get(locationToMove);
        if (target.isSameSide(currentSide)) {
            throw new JanggiException(ErrorCode.DESTINATION_OCCUPIED_SAME_TEAM_ERROR);
        }
    }

    private void validateMove(Location from, Location to) {
        validateLocation(from);
        validateLocation(to);
        validatePieceExist(from);
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

    public List<List<Piece>> to2DArray() {
        List<List<Piece>> pieces = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            List<Piece> line = new ArrayList<>();
            for (int col = 0; col < width; col++) {
                Piece piece = boardState.get(new Location(row, col));
                line.add(piece);
            }
            pieces.add(List.copyOf(line));
        }
        return List.copyOf(pieces);
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

    private boolean isNotSameSide(Piece piece, Side side) {
        return !piece.isSameSide(side);
    }
}
