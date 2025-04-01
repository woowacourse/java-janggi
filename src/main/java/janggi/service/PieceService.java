package janggi.service;

import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.repository.PieceRepository;
import java.util.List;

public class PieceService {

    private final PieceRepository pieceRepository;

    public PieceService(final PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    public Long add(final Piece piece) {
        return pieceRepository.add(piece);
    }

    public void addAll(final List<Piece> pieces) {
        pieceRepository.addAll(pieces);
    }

    public List<Piece> findAll() {
        return pieceRepository.findAll();
    }

    public void deleteAll() {
        pieceRepository.deleteAll();
    }

    public void delete(final Position position) {
        pieceRepository.delete(position);
    }
}
