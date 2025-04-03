package janggi.entity;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.stream.Collectors;

public record PieceEntity(long pieceId,
                          long boardId,
                          String pieceType,
                          String team,
                          int row,
                          int column,
                          boolean isAlive) {

    public static PieceEntity of(long boardId, Piece piece, Position position, boolean isAlive) {
        return of(0, boardId, piece, position, isAlive);
    }

    public static PieceEntity of(long pieceId, long boardId, Piece piece, Position position, boolean isAlive) {
        return new PieceEntity(pieceId,
                boardId,
                piece.getPieceType().name(),
                piece.getTeam().name(),
                position.getRow(),
                position.getColumn(),
                isAlive);
    }

    public static Board convertToBoard(List<PieceEntity> pieceEntities) {
        return new Board(pieceEntities.stream()
                .collect(Collectors.toMap(entity -> Position.of(entity.row(), entity.column()),
                        entity -> PieceFactory.create(PieceType.convert(entity.pieceType()),
                                Team.convert(entity.team())))));
    }

    public PieceEntity addPieceId(long pieceId) {
        return new PieceEntity(pieceId, boardId, pieceType, team, row, column, isAlive);
    }
}
