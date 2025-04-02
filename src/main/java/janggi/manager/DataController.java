package janggi.manager;

import janggi.dao.JanggiDao;
import janggi.domain.PieceName;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataController {

    private final JanggiDao janggiDao;

    public DataController(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
    }

    public List<PieceDto> loadFromDatabase() {
        return janggiDao.getPieces();
    }

    public void setupDatabase(Map<Position, Piece> initialData) {
        List<PieceDto> pieceDtos = convertToDto(initialData);
        janggiDao.addPieces(pieceDtos);
    }

    public List<PieceDto> convertToDto(Map<Position, Piece> pieces) {
        List<PieceDto> pieceDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            PieceDto pieceDto = createPieceDto(entry.getKey(), entry.getValue());
            pieceDtos.add(pieceDto);
        }
        return pieceDtos;
    }

    public Map<Position, Piece> convertFromDto(List<PieceDto> pieceDtos) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto pieceDto : pieceDtos) {
            Piece piece = PieceName.getPiece(pieceDto.name(), pieceDto.side());
            Position position = Position.of(pieceDto.row(), pieceDto.column());
            pieces.put(position, piece);
        }
        return pieces;
    }

    public Side loadCurrentTurn() {
        PieceDto lastMovedPieceDto = janggiDao.getLastMovedPiece();
        Piece lastMovedPiece = PieceName.getPiece(lastMovedPieceDto.name(), lastMovedPieceDto.side());
        Side lastTurn = lastMovedPiece.getSide();
        return lastTurn.reverse();
    }

    public void update(Position oldPosition, Position newPosition, Piece piece) {
        PositionDto positionDto = new PositionDto(oldPosition.getRow(), oldPosition.getColumn());
        PieceDto pieceDto = createPieceDto(newPosition, piece);
        janggiDao.updatePiece(positionDto, pieceDto);
    }

    public void resetDatabase() {
        janggiDao.deleteAllPieces();
    }

    private PieceDto createPieceDto(Position position, Piece piece) {
        return new PieceDto(PieceName.getName(piece), piece.getSide().toString(), position.getRow(), position.getColumn());
    }
}
