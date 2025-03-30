package domain.position;

import dao.BoardDao;
import domain.janggiPiece.JanggiChessPiece;
import domain.position.generator.DefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;
import util.Database;

import java.util.Collections;
import java.util.Map;

public class JanggiPositions {
    private final Map<JanggiPosition, JanggiChessPiece> pieces;
    private final BoardDao janggiBoardDao;

    public JanggiPositions(DefaultPositionsGenerator generator, BoardDao boardDao) {
        this.janggiBoardDao = boardDao;
        Map<JanggiPosition, JanggiChessPiece> all = Database.doDatabaseWorkWithReturn(janggiBoardDao::findAll);
        if (all == null || all.isEmpty()) {
            pieces = generator.generate();
            saveAllPositions();
            return;
        }
        pieces = all;
    }

    private void saveAllPositions() {
        for (JanggiPosition position : pieces.keySet()) {
            Database.doDatabaseWork(() -> janggiBoardDao.save(position, pieces.get(position)));
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
        Database.doDatabaseWork(() -> janggiBoardDao.delete(position));
    }

    private void putJanggiPiece(final JanggiPosition position, final JanggiChessPiece chessPiece) {
        validateEmptyPosition(position);
        pieces.put(position, chessPiece);
        Database.doDatabaseWork(() -> janggiBoardDao.save(position, chessPiece));
    }

    public void reset() {
        pieces.clear();
        Database.doDatabaseWork(janggiBoardDao::deleteAll);
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
}
