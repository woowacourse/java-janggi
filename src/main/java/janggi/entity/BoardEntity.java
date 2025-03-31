package janggi.entity;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.stream.Collectors;

public record BoardEntity(long boardId,
                          long janggiId,
                          String pieceType,
                          String team,
                          int row,
                          int column,
                          boolean isAlive) {

    public static BoardEntity of(long janggiId, Piece piece, Position position, boolean isAlive) {
        return of(0, janggiId, piece, position, isAlive);
    }

    public static BoardEntity of(long boardId, long janggiId, Piece piece, Position position, boolean isAlive) {
        return new BoardEntity(boardId,
                janggiId,
                piece.getPieceType().name(),
                piece.getTeam().name(),
                position.getRow(),
                position.getColumn(),
                isAlive);
    }

    public static Board convertToBoard(List<BoardEntity> boardEntities) {
        return new Board(boardEntities.stream()
                .collect(Collectors.toMap(entity -> Position.of(entity.row(), entity.column()),
                        entity -> PieceFactory.create(PieceType.convert(entity.pieceType()),
                                Team.convert(entity.team())))));
    }

    public BoardEntity addBoardId(long boardId) {
        return new BoardEntity(boardId, janggiId, pieceType, team, row, column, isAlive);
    }
}
