package janggi.service;

import janggi.dao.PieceDao;
import janggi.dao.dto.PieceFindResponse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;

import java.util.ArrayList;
import java.util.List;

public class PieceService {

    private final PieceDao pieceDao;

    public PieceService(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public void initializePieceTable() {
        for (PieceType pieceType : PieceType.valuesNotEmpty()) {
            for (Side side : Side.getSides()) {
                pieceDao.addPiece(pieceType, side);
            }
        }
    }

    public Piece createPiece(String pieceSymbol, String side) {
        PieceType pieceType = PieceType.findPieceTypeBySymbol(pieceSymbol);
        Side sideByName = Side.findSideByName(side);

        return pieceType.createPiece(sideByName);
    }

    public List<Piece> findAllPieces() {
        List<PieceFindResponse> pieceFindResponses = pieceDao.findAllPieces();

        List<Piece> pieces = new ArrayList<>();
        for (PieceFindResponse pieceFindResponse : pieceFindResponses) {
            pieces.add(createPiece(pieceFindResponse.pieceType(), pieceFindResponse.side()));
        }
        return pieces;
    }

}
