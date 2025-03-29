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

    public PieceService() {
        this.pieceDao = new PieceDao();
    }

    public void initializePieceTable() {
        if (findAllPieces().isEmpty()) {
            for (PieceType pieceType : PieceType.valuesNotEmpty()) {
                for (Side side : Side.getSides()) {
                    pieceDao.addPiece(pieceType, side);
                }
            }
        }
    }

    public Piece createPiece(String pieceSymbol, String side) {
        return PieceType.createPiece(pieceSymbol, Side.findSideByName(side));
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
