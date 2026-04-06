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
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        outputView.printBoard(board.getBoard());

        Game game = Game.of(board);
        GameEntity savedGame = gameDao.save(
                GameEntity.from(game.getTurnName(), game.getStatus().toString())
        );

        List<PieceEntity> pieces = convertBoardToPieceEntities(savedGame.getId(), game.getBoard());
        pieceDao.saveAll(pieces);

        while (true) {
            boolean isContinue = move(game);
            gameDao.update(savedGame.getId(), game.getTurnName(), game.getStatus().toString());
            outputView.printScore(game.calculateScore(Team.CHU), game.calculateScore(Team.HAN));

            if (!isContinue) {
                break;
            }
            outputView.printBoard(board.getBoard());
        }
        outputView.printGameResult(game.getStatus());
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

    private boolean move(Game game) {
        try {
            Board board = game.getBoard();
            String turnName = game.getTurnName();
            String currentInput = inputView.readPosition(turnName);
            if (currentInput.equals("r")) {
                game.lose(turnName);
                return false;
            }
            Position currentPosition = parsePosition(currentInput);

            Piece piece = board.findPieceByPosition(currentPosition)
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
            game.checkTurn(piece.getTeam());

            String targetInput = inputView.readTargetPosition();
            if (targetInput.equals("n")) {
                return false;
            }
            Position targetPosition = parsePosition(targetInput);

            game.tryToMove(currentPosition, targetPosition);

            if (game.getStatus() != Status.PLAYING) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return move(game);
        }
    }

    private Position parsePosition(String input) {
        String[] tokens = input.split(" ");
        return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
