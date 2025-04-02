package janggi.dao.fake;

import janggi.dao.PieceTypeDao;
import janggi.domain.piece.PieceType;
import janggi.dto.PieceTypeDto;
import java.util.ArrayList;
import java.util.List;

public class FakePieceTypeDao extends PieceTypeDao {

    private final List<PieceTypeDto> pieceTypes;

    public FakePieceTypeDao() {
        super(null);
        pieceTypes = new ArrayList<>();
    }

    @Override
    public void insertInitialPieceType() {
        int index = 1;

        for (PieceType pieceType : PieceType.values()) {
            pieceTypes.add(new PieceTypeDto(index, pieceType.getTitle()));
        }
    }

    @Override
    public PieceTypeDto findPieceTypeById(int id) {
        return pieceTypes.get(id - 1);
    }

    @Override
    public void deleteAllPieceTypeIfExists() {
        pieceTypes.clear();
    }

    public List<PieceTypeDto> getPieceTypes() {
        return pieceTypes;
    }
}
