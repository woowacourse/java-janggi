package janggi.service;

import static janggi.domain.Team.BLUE;

import janggi.domain.BoardSetup;
import janggi.domain.Game;
import janggi.domain.Pieces;
import janggi.domain.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesInitializer;
import janggi.domain.piece.direction.Position;
import janggi.repository.PieceRepository;
import janggi.repository.TurnRepository;
import java.util.List;

public class GameService {

    private final PieceRepository pieceRepository;
    private final TurnRepository turnRepository;

    public GameService(final PieceRepository pieceRepository, final TurnRepository turnRepository) {
        this.pieceRepository = pieceRepository;
        this.turnRepository = turnRepository;
    }

    public boolean existGame() {
        return pieceRepository.exist();
    }

    public Game setNewGame(final BoardSetup redBoardSetup, final BoardSetup blueBoardSetup) {
        pieceRepository.deleteAll();
        turnRepository.deleteCurrent();
        return generateNewGame(redBoardSetup, blueBoardSetup);
    }

    private Game generateNewGame(final BoardSetup redBoardSetup, final BoardSetup blueBoardSetup) {
        final List<Piece> pieces = PiecesInitializer.initializePieces(redBoardSetup, blueBoardSetup);
        final Turn turn = new Turn(BLUE);
        pieceRepository.addAll(pieces);
        turnRepository.add(turn);
        return new Game(new Pieces(pieces), turn);
    }

    public Game loadGame() {
        if (turnRepository.findCurrent().isPresent()) {
            return new Game(new Pieces(pieceRepository.findAll()), turnRepository.findCurrent().get());
        }
        throw new IllegalArgumentException("턴 정보가 없습니다. 새로운 게임으로 진행해주세요.");
    }

    public void addPiece(final Piece piece) {
        pieceRepository.add(piece);
    }

    public void deletePiece(final Piece piece) {
        pieceRepository.delete(piece.getPosition());
    }

    public void deletePieceByPosition(final Position position) {
        pieceRepository.delete(position);
    }

    public void updateTurn(final Turn turn) {
        turnRepository.change(turn);
    }

    public void deleteGame() {
        pieceRepository.deleteAll();
        turnRepository.deleteCurrent();
    }
}
