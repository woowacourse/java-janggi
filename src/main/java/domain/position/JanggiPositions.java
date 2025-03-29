package domain.position;

import dao.BoardDao;
import domain.janggiPiece.JanggiChessPiece;
import domain.position.generator.DefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiPositions {
    private final Map<JanggiPosition, JanggiChessPiece> pieces;
    private final BoardDao janggiBoardDao;

    public JanggiPositions(DefaultPositionsGenerator generator, BoardDao boardDao) {
        this.janggiBoardDao = boardDao;
        Map<JanggiPosition, JanggiChessPiece> all = doDatabaseWorkWithReturn(janggiBoardDao::findAll);
        if (all == null || all.isEmpty()) {
            pieces = generator.generate();
            saveAllPositions();
            return;
        }
        pieces = all;
    }

    private void saveAllPositions() {
        for (JanggiPosition position : pieces.keySet()) {
            doDatabaseWork(() -> janggiBoardDao.save(position, pieces.get(position)));
        }
    }

    public boolean existChessPieceByPosition(final JanggiPosition position) {
        return pieces.containsKey(position);
    }

    public JanggiChessPiece getJanggiPieceByPosition(final JanggiPosition position) {
        validateExistPiece(position);
        return pieces.get(position);
    }

    public void move(final JanggiPosition from, final JanggiPosition to) {
        validateExistPiece(from);
        validateEmptyPosition(to);
        JanggiChessPiece target = getJanggiPieceByPosition(from);
        removeJanggiPieceByPosition(from);
        putJanggiPiece(to, target);
    }

    private void validateExistPiece(final JanggiPosition position) {
        if (!existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validateEmptyPosition(final JanggiPosition position) {
        if (existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 이미 다른 기물이 존재합니다.");
        }
    }

    public void removeJanggiPieceByPosition(final JanggiPosition position) {
        validateExistPiece(position);
        pieces.remove(position);
        doDatabaseWork(() -> janggiBoardDao.delete(position));
    }

    private void putJanggiPiece(final JanggiPosition position, final JanggiChessPiece chessPiece) {
        validateEmptyPosition(position);
        pieces.put(position, chessPiece);
        doDatabaseWork(() -> janggiBoardDao.save(position, chessPiece));
    }

    public void reset() {
        pieces.clear();
        doDatabaseWork(janggiBoardDao::deleteAll);
    }

    public Score calculateScoreWith(JanggiTeam team) {
        final int total = pieces.values().stream()
                .filter(p -> p.getTeam() == team)
                .map(JanggiChessPiece::getScore)
                .mapToInt(Score::value)
                .sum();
        return new Score(total);
    }

    public Map<JanggiPosition, JanggiChessPiece> getJanggiPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    private void doDatabaseWork(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            System.out.println("데이터베이스 작업에 실패했습니다: " + e.getMessage());
        }
    }

    private <T> T doDatabaseWorkWithReturn(Supplier<T> runnable) {
        try {
            return runnable.get();
        } catch (Exception e) {
            System.out.println("데이터베이스 작업에 실패했습니다: " + e.getMessage());
        }
        return null;
    }
}
