package janggi.manager;

import janggi.dao.JanggiDao;
import janggi.domain.PieceName;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import janggi.view.Viewer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataController {

    private final JanggiDao janggiDao;
    private final int gameId;

    public DataController(JanggiDao janggiDao, Viewer viewer) {
        this.janggiDao = janggiDao;
        this.gameId = viewer.readGameId();
    }

    public boolean hasExistingGame() {
        return janggiDao.hasGameId(gameId);
    }

    public List<PieceDto> loadPieces() {
        return janggiDao.getPieces(gameId);
    }

    public void saveNewPieces(Map<Position, Piece> pieces) {
        List<PieceDto> pieceDtos = convertToDto(pieces);
        janggiDao.addGameId(gameId);
        janggiDao.addPieces(gameId, pieceDtos);
    }

    private List<PieceDto> convertToDto(Map<Position, Piece> pieces) {
        List<PieceDto> pieceDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            PieceDto pieceDto = createPieceDto(entry.getKey(), entry.getValue());
            pieceDtos.add(pieceDto);
        }
        return pieceDtos;
    }

    private PieceDto createPieceDto(Position position, Piece piece) {
        return new PieceDto(PieceName.getName(piece), piece.getSide().toString(), position.getRow(), position.getColumn());
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
        String currentTurn = janggiDao.getCurrentTurn(gameId);
        return Side.getSide(currentTurn);
    }

    public void update(Position oldPosition, Position newPosition, Side currentTurn) {
        PositionDto oldPositionDto = new PositionDto(oldPosition.getRow(), oldPosition.getColumn());
        PositionDto newPositionDto = new PositionDto(newPosition.getRow(), newPosition.getColumn());
        janggiDao.updatePiece(gameId, oldPositionDto, newPositionDto);
        janggiDao.updateCurrentTurn(gameId, currentTurn.toString());
    }

    public void deleteGameData() {
        janggiDao.deleteGamePieces(gameId);
        janggiDao.deleteGameId(gameId);
    }
}
