import domain.*;
import domain.vo.Position;
import entity.GameEntity;
import entity.PieceEntity;
import repository.GameDao;
import repository.PieceDao;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class JanggiController {

    private static final String QUIT_COMMAND = "r";
    private static final String SKIP_COMMAND = "n";

    private final InputView inputView;
    private final OutputView outputView;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiController(InputView inputView, OutputView outputView, GameDao gameDao, PieceDao pieceDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public void run() {
        Game game = initializeGame();
        GameEntity savedGame = saveGame(game);

        while (true) {
            boolean isContinue = move(game, savedGame.getId());
            gameDao.update(savedGame.getId(), game.getTurnName(), game.getStatus().toString());
            outputView.printScore(game.calculateScore(Team.CHU), game.calculateScore(Team.HAN));

            if (!isContinue) {
                break;
            }
            outputView.printBoard(game.getBoard().getBoard());
        }
        outputView.printGameResult(game.getStatus());
    }

    private Game initializeGame() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        outputView.printBoard(board.getBoard());
        return Game.of(board);
    }

    private GameEntity saveGame(Game game) {
        GameEntity savedGame = gameDao.save(
                GameEntity.from(game.getTurnName(), game.getStatus().toString())
        );

        List<PieceEntity> pieces = convertBoardToPieceEntities(savedGame.getId(), game.getBoard());
        pieceDao.saveAll(pieces);
        return savedGame;
    }

    private List<PieceEntity> convertBoardToPieceEntities(Long gameId, Board board) {
        List<PieceEntity> pieces = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 9; col++) {
                Position position = Position.of(row, col);
                board.findPieceByPosition(position)
                        .ifPresent(piece -> pieces.add(PieceEntity.from(gameId, piece, position)));
            }
        }
        return pieces;
    }

    private boolean move(Game game, Long gameId) {
        try {
            String currentInput = inputView.readPosition(game.getTurnName());
            if (isQuitOrSkipCommand(currentInput))
                return false;
            Position from = parsePosition(currentInput);

            validatePieceAndTurn(game, from);

            String targetInput = inputView.readTargetPosition();
            if (isQuitOrSkipCommand(targetInput))
                return false;
            Position to = parsePosition(targetInput);

            executeMove(game, gameId, from, to);

            return game.getStatus() == Status.PLAYING;
        } catch (Exception e) {
            outputView.printError(e.getMessage());
            return move(game, gameId);
        }
    }

    private void validatePieceAndTurn(Game game, Position position) {
        Piece piece = game.getBoard().findPieceByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
        game.checkTurn(piece.getTeam());
    }

    private void executeMove(Game game, Long gameId, Position from, Position to) {
        boolean hasTargetPiece = game.getBoard().findPieceByPosition(to).isPresent();
        game.tryToMove(from, to);
        updatePieceEntities(gameId, hasTargetPiece, to, from);
    }

    private boolean isQuitOrSkipCommand(String input) {
        return input.equals(QUIT_COMMAND) || input.equals(SKIP_COMMAND);
    }

    private Position parsePosition(String input) {
        String[] tokens = input.split(" ");
        return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }

    private void updatePieceEntities(Long gameId, boolean hasTargetPiece, Position currentPosition, Position targetPosition) {
        if (hasTargetPiece) {
            pieceDao.deleteByPosition(gameId, targetPosition.getRow(), targetPosition.getCol());
        }
        pieceDao.updatePosition(gameId,
                currentPosition.getRow(), currentPosition.getCol(),
                targetPosition.getRow(), targetPosition.getCol());
    }
}
