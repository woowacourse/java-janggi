package domain.position;

import dao.BoardDao;
import domain.janggiPiece.JanggiChessPiece;
import domain.position.generator.DefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiPiecePositions {
    private final Map<JanggiPosition, JanggiChessPiece> chessPieces;
    private final BoardDao janggiBoardDao;

    public JanggiPiecePositions(DefaultPositionsGenerator generator, BoardDao boardDao) {
        this.janggiBoardDao = boardDao;
        Map<JanggiPosition, JanggiChessPiece> all = doDatabaseWorkWithReturn(janggiBoardDao::findAll);
        if (all == null || all.isEmpty()) {
            chessPieces = generator.generate();
            saveAllPositions();
            return;
        }
        chessPieces = all;
    }

    private void saveAllPositions() {
        for (JanggiPosition position : chessPieces.keySet()) {
            doDatabaseWork(() -> janggiBoardDao.save(position, chessPieces.get(position)));
        }
    }

    public boolean existChessPieceByPosition(final JanggiPosition position) {
        return chessPieces.containsKey(position);
    }

    public JanggiChessPiece getJanggiPieceByPosition(final JanggiPosition position) {
        validateExistPiece(position);
        return chessPieces.get(position);
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
        chessPieces.remove(position);
        doDatabaseWork(() -> janggiBoardDao.delete(position));
    }

    private void putJanggiPiece(final JanggiPosition position, final JanggiChessPiece chessPiece) {
        validateEmptyPosition(position);
        chessPieces.put(position, chessPiece);
        doDatabaseWork(() -> janggiBoardDao.save(position, chessPiece));
    }

    public void reset() {
        chessPieces.clear();
        doDatabaseWork(janggiBoardDao::deleteAll);
    }

    public Score calculateScoreWith(JanggiTeam team) {
        final int total = chessPieces.values().stream()
                .filter(p -> p.getTeam() == team)
                .map(JanggiChessPiece::getScore)
                .mapToInt(Score::value)
                .sum();
        return new Score(total);
    }

    public Map<JanggiPosition, JanggiChessPiece> getJanggiPieces() {
        return Collections.unmodifiableMap(chessPieces);
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
