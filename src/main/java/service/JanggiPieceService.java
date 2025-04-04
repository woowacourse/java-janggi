package service;

import dao.PieceDao;
import domain.hurdlePolicy.HurdlePolicy;
import domain.janggiPiece.JanggiChessPiece;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.position.JanggiPositions;
import domain.position.generator.DefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;
import entity.PieceEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiPieceService {
    private final JanggiPositions janggiPositions;
    private final PieceDao pieceDao;

    public JanggiPieceService(
            DefaultPositionsGenerator defaultPositionsGenerator,
            PieceDao pieceDao
    ) {
        this.pieceDao = pieceDao;
        this.janggiPositions = getInitPositions(defaultPositionsGenerator);
    }

    private JanggiPositions getInitPositions(DefaultPositionsGenerator defaultPositionsGenerator) {
        List<PieceEntity> all = pieceDao.findAll();
        if (all.isEmpty()) {
            Map<JanggiPosition, JanggiChessPiece> pieces = defaultPositionsGenerator.generate();
            saveAll(pieces);
            return new JanggiPositions(pieces);
        }
        Map<JanggiPosition, JanggiChessPiece> result = new HashMap<>();
        for (PieceEntity entity : all) {
            JanggiChessPiece piece = entity.createPiece();
            result.put(JanggiPositionFactory.of(entity.row(), entity.col()), piece);
        }
        return new JanggiPositions(result);
    }

    private void saveAll(Map<JanggiPosition, JanggiChessPiece> pieces) {
        List<PieceEntity> entities = pieces.entrySet().stream()
                .map(entry -> new PieceEntity(
                        entry.getKey().getRow(),
                        entry.getKey().getCol(),
                        entry.getValue().getTeam(),
                        entry.getValue().getChessPieceType()
                ))
                .toList();
        for (PieceEntity entity : entities) {
            pieceDao.save(entity);
        }
    }

    public void move(final JanggiPosition from, final JanggiPosition to) {
        PieceEntity pieceEntity = pieceDao.findByPosition(from).get();
        janggiPositions.move(from, to);
        pieceDao.deleteByPosition(from);
        pieceDao.save(pieceEntity.move(to));
    }

    public void kill(final JanggiPosition targetPosition) {
        pieceDao.deleteByPosition(targetPosition);
    }

    public List<JanggiPosition> getAvailableDestination(final JanggiPosition position) {
        JanggiChessPiece piece = janggiPositions.getJanggiPieceByPosition(position);
        List<Path> coordinatePaths = piece.getCoordinatePaths(position);
        HurdlePolicy hurdlePolicy = piece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(piece.getTeam(), coordinatePaths, janggiPositions);
    }

    public JanggiChessPiece getPieceByPosition(final JanggiPosition position) {
        return janggiPositions.getJanggiPieceByPosition(position);
    }

    public boolean isExistAt(final JanggiPosition targetPosition) {
        return janggiPositions.existChessPieceByPosition(targetPosition);
    }

    public void reset() {
        janggiPositions.reset();
        pieceDao.deleteAll();
    }

    public Score getScoreWith(final JanggiTeam team) {
        return janggiPositions.calculateScoreWith(team);
    }

    public Map<JanggiPosition, JanggiChessPiece> getPieces() {
        return janggiPositions.getJanggiPieces();
    }
}
