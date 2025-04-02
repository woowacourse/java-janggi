package janggi.data.dao.test;

import janggi.data.dao.PieceSymbolDao;
import janggi.piece.PieceSymbol;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FakePieceSymbolDao implements PieceSymbolDao {

    private final Map<Integer, String> pieceSymbols = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public void save(PieceSymbol pieceSymbol) {
        this.pieceSymbols.put(id.getAndIncrement(), pieceSymbol.name());
    }

    @Override
    public void saveAll(PieceSymbol... pieceSymbols) {
        for (PieceSymbol pieceSymbol : pieceSymbols) {
            save(pieceSymbol);
        }
    }

    @Override
    public int findIdByName(String name) {
        for (Map.Entry<Integer, String> entry : pieceSymbols.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("해당 이름의 기물 유형이 존재하지 않습니다.");
    }
}
