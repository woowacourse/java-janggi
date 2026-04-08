import domain.board.*;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Status;
import domain.vo.Position;
import entity.GameEntity;
import entity.PieceEntity;
import repository.GameDao;
import repository.PieceDao;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        while (true) {
            GameType gameType = inputView.readGameType();

            if (gameType == GameType.LOAD) {
                List<GameEntity> findGames = gameDao.findAll();
                int gameId = inputView.readGameNumber(findGames.stream()
                        .map(GameEntity::getUpdatedAt)
                        .toList());
                GameEntity findGame = findGames.get(gameId - 1);

                Status status = Status.valueOf(findGame.getStatus());
                if (status != Status.PLAYING) {
                    outputView.printGameResult(status);
                    continue;
                }

                List<PieceEntity> findPieces = pieceDao.findAllByGameId(findGame.getId());
                Board board = convertPieceEntitiesToBoard(findPieces);

                Team team = Team.valueOf(findGame.getCurrentTurn());
                Game game = Game.loadGame(board, team, status);

                playGame(game, findGame);
            }
            if (gameType == GameType.NEW) {
                Game game = initializeGame();
                GameEntity savedGame = saveGame(game);
                playGame(game, savedGame);
            }
            if (gameType == GameType.EXIT) {
                break;
            }
        }
    }

    private Board convertPieceEntitiesToBoard(List<PieceEntity> findPieces) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceEntity piece : findPieces) {
            Position position = Position.of(piece.getPositionRow(), piece.getPositionCol());
            Type type = Type.valueOf(piece.getPieceType());
            board.put(position, Piece.of(Team.valueOf(piece.getTeam()), type, type.createStrategy()));
        }
        return Board.of(board);
    }

    private void playGame(Game game, GameEntity gameEntity) {
        while (true) {
            outputView.printBoard(game.getBoard().getBoard());
            boolean isContinue = move(game, gameEntity.getId());
            gameDao.update(gameEntity.getId(), game.getCurrentTeam().name(), game.getStatus().toString());
            outputView.printScore(game.calculateScore(Team.CHU), game.calculateScore(Team.HAN));

            if (!isContinue) {
                break;
            }
        }
        outputView.printGameResult(game.getStatus());
    }

    private Game initializeGame() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        return Game.of(board);
    }

    private GameEntity saveGame(Game game) {
        GameEntity gameEntity = gameDao.save(
                new GameEntity(game.getCurrentTeam().name(), game.getStatus().toString())
        );

        List<PieceEntity> pieces = convertBoardToPieceEntities(gameEntity.getId(), game.getBoard());
        pieceDao.saveAll(pieces);
        return gameEntity;
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
            String turnName = game.getTurnDisplayName();

            String currentInput = inputView.readPosition(turnName);
            if (isQuitOrSkipCommand(game, turnName, currentInput))
                return false;
            Position from = parsePosition(currentInput);

            validatePieceAndTurn(game, from);

            String targetInput = inputView.readTargetPosition();
            if (isQuitOrSkipCommand(game, turnName, targetInput))
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
        updatePieceEntities(gameId, hasTargetPiece, from, to);
    }

    private boolean isQuitOrSkipCommand(Game game, String turn, String input) {
        if (input.equals(QUIT_COMMAND)) {
            game.lose(turn);
            return true;
        }
        if (input.equals(SKIP_COMMAND)) {
            return true;
        }
        return false;
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
