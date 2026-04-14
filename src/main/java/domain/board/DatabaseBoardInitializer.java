package domain.board;

import domain.Side;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import domain.piece.Piece;
import java.util.Map;
import repository.GameRepository;
import repository.PieceRepository;

public class DatabaseBoardInitializer implements BoardInitializer {
    private final PieceRepository pieceRepository;
    private final GameRepository gameRepository;

    public DatabaseBoardInitializer(PieceRepository pieceRepository, GameRepository gameRepository) {
        this.pieceRepository = pieceRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Map<Position, Piece> initialize() {
        return pieceRepository.getPiecesPosition(); // 이미 구현됨
    }

    @Override
    public Topology createTopology() {
        return new BasicBoardInitializer().createTopology();
    }

    @Override
    public Side getFirstTurnSide() {
        return gameRepository.getCurrentTurn(); // 추가 필요
    }
}
