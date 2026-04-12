package domain.board;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardStates {
    private static final String NOT_FOUNT_PIECE_FROM_POSITION = "[ERROR] 해당 좌표에 기물이 존재하지 않습니다.";
    private static final int GENERAL_COUNT = 2;

    private final Map<Position, Piece> boardStates;

    public BoardStates(Map<Position, Piece> boardPieces) {
        this.boardStates = new HashMap<>(boardPieces);
    }

    public void changePiecePosition(Position from, Position to) {
        Piece fromPiece = boardStates.get(from);
        boardStates.remove(from);
        boardStates.put(to, fromPiece);
    }

    public boolean isGeneralCaught() {
        long generalCount = boardStates.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.GENERAL)
                .count();
        return generalCount != GENERAL_COUNT;
    }

    public boolean isEmpty(Position position) {
        return !boardStates.containsKey(position);
    }

    public void validatePieceMove(Position from, Position to, Path path) {
        PieceInfos wayPointPieceInfos = getPieceInfos(path.getPath());
        boardStates.get(from).validateMove(wayPointPieceInfos, from, to);
    }

    public PieceInfos getBoardStates() {
        List<Position> positions = boardStates.keySet().stream()
                .toList();
        return getPieceInfos(positions);
    }

    private PieceInfos getPieceInfos(List<Position> positions) {
        Map<Position, PieceInfo> pieceInfos = new HashMap<>();
        for (Position position : positions) {
            adjustPieceInfo(position, pieceInfos);
        }
        return new PieceInfos(pieceInfos);
    }

    private void adjustPieceInfo(Position position, Map<Position, PieceInfo> pieceInfos) {
        if (isEmpty(position)) {
            return;
        }
        pieceInfos.put(position, boardStates.get(position).pieceInfo());
    }

    public Path getPiecePath(Position from, Position to) {
        return boardStates.get(from).path(from, to);
    }

    public CountryType getPieceCountryType(Position position) {
        if (!boardStates.containsKey(position)) {
            throw new IllegalArgumentException(NOT_FOUNT_PIECE_FROM_POSITION);
        }
        return boardStates.get(position).getPieceCountryType();
    }

    public double calculateScore(CountryType countryType) {
        return boardStates.values().stream()
                .filter(piece -> piece.getPieceCountryType() == countryType)
                .mapToDouble(Piece::getPieceScore)
                .sum();
    }
}
