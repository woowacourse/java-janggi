package janggi;

import janggi.dao.JanggiDao;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseController {

    private final JanggiDao janggiDao;

    public DatabaseController(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
    }

    public void setupDatabase(Map<Position, Piece> initialData) {
        List<PieceDto> pieceDtos = convertToData(initialData);
        janggiDao.addPieces(pieceDtos);
    }

    public List<PieceDto> loadFromDatabase() {
        return janggiDao.getPieces();
    }

    public Side loadCurrentTurn() {
        PieceDto lastMovedPieceDto = janggiDao.getLastMovedPiece();
        Piece lastMovedPiece = PieceName.getPiece(lastMovedPieceDto.name(), lastMovedPieceDto.side());
        Side lastTurn = lastMovedPiece.getSide();
        return lastTurn.reverse();
    }

    public Map<Position, Piece> convertFromData(List<PieceDto> pieceDtos) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto pieceDto : pieceDtos) {
            Piece piece = PieceName.getPiece(pieceDto.name(), pieceDto.side());
            Position position = Position.of(pieceDto.row(), pieceDto.column());
            pieces.put(position, piece);
        }
        return pieces;
    }

    public List<PieceDto> convertToData(Map<Position, Piece> pieces) {
        List<PieceDto> pieceDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            PositionDto positionDto = entry.getKey().getPositionDto();
            Piece piece = entry.getValue();
            PieceDto pieceDto = new PieceDto(PieceName.getDatabaseName(piece), piece.getSide().toString(), positionDto.row(), positionDto.column());
            pieceDtos.add(pieceDto);
        }
        return pieceDtos;
    }

    public void update(PositionDto oldPosition, PieceDto pieceDto) {
        janggiDao.updatePiece(oldPosition, pieceDto);
    }
}
