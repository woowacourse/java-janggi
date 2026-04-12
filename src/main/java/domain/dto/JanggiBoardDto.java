package domain.dto;

import domain.board.JanggiBoard;
import domain.piece.Piece;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiBoardDto {
    private final List<PieceDto> pieces;

    public JanggiBoardDto(List<PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static JanggiBoardDto from(JanggiBoard janggiBoard) {
        Map<Position, Piece> board = janggiBoard.getJanggiBoard();
        int size = board.size();
        List<PieceDto> pieces = new ArrayList<>(size);

        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            pieces.add(new PieceDto(position.row(), position.col(), piece.getTeam(), piece.getPieceType()));
        }
        return new JanggiBoardDto(pieces);
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
