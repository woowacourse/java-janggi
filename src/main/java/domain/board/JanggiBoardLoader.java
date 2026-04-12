package domain.board;

import db.PieceFactory;
import domain.dto.JanggiBoardDto;
import domain.dto.PieceDto;
import domain.piece.Blank;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardLoader implements BoardInitializer {
    private final JanggiBoardDto boardDto;

    public JanggiBoardLoader(JanggiBoardDto boardDto) {
        this.boardDto = boardDto;
    }

    @Override
    public Map<Position, Piece> init() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 9; col++) {
                board.put(new Position(row, col), new Blank());
            }
        }

        for (PieceDto pieceDto : boardDto.getPieces()) {
            Position pos = new Position(pieceDto.row(), pieceDto.col());
            Piece piece = PieceFactory.create(pieceDto.pieceType(), pieceDto.team());
            board.put(pos, piece);
        }

        return board;
    }
}
