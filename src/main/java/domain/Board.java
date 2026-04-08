package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.enums.Country;
import domain.enums.MaSangPosition;
import domain.enums.PieceType;
import domain.pieces.None;
import domain.pieces.Piece;
import domain.pieces.PieceFactory;
import exception.Validator;

public class Board {
    private final Map<Position, Piece> board;

    public Board(List<PieceType> choHan) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type == PieceType.MA || type == PieceType.SANG || type == PieceType.NONE) {
                continue;
            }
            for (Position p : type.getChoPosition()) {
                pieces.put(p, PieceFactory.createPiece(type, Country.CHO));
            }
            for (Position p : type.getHanPosition()) {
                pieces.put(p, PieceFactory.createPiece(type, Country.HAN));
            }
        }
        for (MaSangPosition maSangPosition : MaSangPosition.values()) {
            PieceType pieceType = choHan.get(maSangPosition.getIndex());
            pieces.put(maSangPosition.getPosition(), PieceFactory.createPiece(pieceType, maSangPosition.getCountry()));
        }
        this.board = pieces;
    }

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public List<Position> findAvailablePositions(Position start) {
        Piece startPiece = board.getOrDefault(start, None.INSTANCE);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return board.getOrDefault(position, None.INSTANCE);
            }
        };
        return startPiece.getAvailableRoute(start,finder);
    }

    public PieceType move(Position start, Position end) {
        Piece startPiece = board.getOrDefault(start, None.INSTANCE);
        if (!findAvailablePositions(start).contains(end)){
            throw new IllegalArgumentException("말을 이동할 수 없습니다.");
        }
        killPiece(start);
        PieceType pieceType=killPiece(end);
        board.put(end, startPiece);
        return pieceType;
    }

    private PieceType killPiece(Position endPosition) {
        PieceType pieceType = board.getOrDefault(endPosition, None.INSTANCE).getPieceType();
        board.remove(endPosition);
        return pieceType;
    }

    public int calculateScore(Country country){
        return board.values().stream()
                .filter(piece -> piece.getCountry()==country)
                .mapToInt(Piece::getPieceScore)
                .sum();
    }

    public boolean isKingAlive(Country country){
        return board.values().stream()
                .filter(piece -> piece.getCountry()==country )
                .anyMatch(piece -> piece.getPieceType()==PieceType.JANG);
    }

    public PieceType getPiece(Position position) {
        if (!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).getPieceType();
    }

    public Country getPieceCountry(Position position) {
        if (!board.containsKey(position)) {
            return Country.NONE;
        }
        return board.get(position).getCountry();
    }

    public List<Position> getPiecesNowPosition(Country country, PieceType pieceType) {
        List<Position> positions = board.entrySet().stream()
                .filter(entry ->
                        entry.getValue().getPieceType() == pieceType &&
                                entry.getValue().getCountry() == country)
                .map(Map.Entry::getKey)
                .toList();
        Validator.validateDataExist(!positions.isEmpty(),"존재하지 않는 기물입니다. 장기판 위의 기물을 입력해주세요.");
        return positions;
    }

    public Map<Position,Piece> getBoard() {
        return board;
    }

}
