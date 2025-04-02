package janggi.service;

import janggi.dao.PieceDao;
import janggi.dao.dto.PieceFindDto;
import janggi.domain.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PieceService {

    private final PieceDao pieceDao;

    public PieceService() {
        this.pieceDao = new PieceDao();
    }

    public void initializePieceTable() {
        if (findAllPieces().isEmpty()) {
            for (PieceType pieceType : PieceType.valuesNotEmpty()) {
                for (Turn side : Turn.getSides()) {
                    pieceDao.addPiece(pieceType, side);
                }
            }
        }
    }

    public Piece createPiece(String pieceSymbol, String side) {
        return PieceType.createPiece(pieceSymbol, Turn.getStateByName(side));
    }

    public List<Piece> findAllPieces() {
        List<PieceFindDto> pieceFindResponse = pieceDao.findAllPieces();

        List<Piece> pieces = new ArrayList<>();
        for (PieceFindDto pieceFindDto : pieceFindResponse) {
            pieces.add(createPiece(pieceFindDto.pieceType(), pieceFindDto.side()));
        }
        return pieces;
    }

    public int findPieceIdByPosition(final Piece piece) {
        return pieceDao.findPieceByTypeAndSide(piece.getType(), piece.getTurn());
    }
}
