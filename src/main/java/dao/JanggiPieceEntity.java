package dao;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Empty;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceSymbol;
import domain.piece.Side;
import domain.piece.Soldier;

public class JanggiPieceEntity {
    private Long id;
    private Long gameId;
    private Integer rank;
    private Integer file;
    private PieceSymbol pieceSymbol;
    private Side side;

    public JanggiPieceEntity(Long id, Long gameId, Integer rank, Integer file, PieceSymbol pieceSymbol, Side side) {
        this.id = id;
        this.gameId = gameId;
        this.rank = rank;
        this.file = file;
        this.pieceSymbol = pieceSymbol;
        this.side = side;
    }

    public JanggiPieceEntity(Long gameId, Integer rank, Integer file, PieceSymbol pieceSymbol, Side side) {
        this(null, gameId, rank, file, pieceSymbol, side);
    }

    public Long getGameId() {
        return gameId;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getFile() {
        return file;
    }

    public PieceSymbol getPieceSymbol() {
        return pieceSymbol;
    }

    public Side getSide() {
        return side;
    }

    public Piece createPiece() {
        return createPieceFromType(pieceSymbol.getSymbol(), side);
    }

    private Piece createPieceFromType(String pieceType, Side side) {
        return switch (pieceType) {
            case "포" -> new Cannon(side);
            case "차" -> new Chariot(side);
            case "상" -> new Elephant(side);
            case "사" -> new Guard(side);
            case "마" -> new Horse(side);
            case "궁" -> new General(side);
            case "병" -> new Soldier(side);
            case "졸" -> new Soldier(side);
            default -> new Empty();
        };
    }
}
