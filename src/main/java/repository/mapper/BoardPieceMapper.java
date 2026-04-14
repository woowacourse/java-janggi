package repository.mapper;

import dao.BoardPieceRawData;
import db.PieceTypeMapper;
import domain.board.Board;
import domain.board.BoardSnapshot;
import domain.game.Team;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BoardPieceMapper {

    private BoardPieceMapper() {
    }

    public static List<BoardPieceRawData> toRawPieces(BoardSnapshot board) {
        return Position.allPositions().stream()
                .filter(position -> board.pieceAt(position).isNotEmpty())
                .map(position -> toRawPiece(position, board.pieceAt(position)))
                .toList();
    }

    public static BoardPieceRawData toRawPiece(Position position, Piece piece) {
        return new BoardPieceRawData(
                position.row(),
                position.column(),
                PieceTypeMapper.toTypeName(piece),
                piece.getTeam().name()
        );
    }

    public static Board toBoard(List<BoardPieceRawData> pieceData) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (BoardPieceRawData data : pieceData) {
            Position position = new Position(data.rowPos(), data.colPos());
            Piece piece = PieceTypeMapper.toPiece(data.pieceType(), Team.valueOf(data.team()));
            pieces.put(position, piece);
        }
        return new Board(pieces);
    }
}
