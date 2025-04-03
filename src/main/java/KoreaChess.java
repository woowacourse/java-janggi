import dao.BoardDao;
import dao.PieceDao;
import dao.PlayerDao;
import domain.game.Board;
import domain.game.SetUp;
import domain.piece.Piece;
import domain.piece.PieceInitializer;
import domain.piece.Pieces;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import dto.MoveResultDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import view.InputView;
import view.OutputView;

public class KoreaChess {

    private final OutputView outputView;
    private final InputView inputView;
    private final PieceDao pieceDao;
    private final PlayerDao playerDao;
    private final BoardDao boardDao;

    public KoreaChess(final OutputView outputView, final InputView inputView,
                      final PieceDao pieceDao, final PlayerDao playerDao, final BoardDao boardDao) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.pieceDao = pieceDao;
        this.playerDao = playerDao;
        this.boardDao = boardDao;
    }

    public void run() {
        Player han = getPlayer(Team.HAN);
        Player cho = getPlayer(Team.CHO);
        Board board = getBoard(han, cho);
        process(board, han, cho);

        Player winner = board.getWinner();
        board.calculateScores();
        outputView.printScores(han, cho);
        outputView.printWinner(winner);

        end();
    }

    private Player getPlayer(final Team team) {
        Optional<Player> optionalPlayer = playerDao.findPlayerByTeam(team);
        if (optionalPlayer.isEmpty()) {
            String name = inputView.getName(team);
            Player player = new Player(name, team);
            playerDao.addPlayer(player);
            return player;
        }

        return optionalPlayer.get();
    }

    private Board getBoard(final Player han, final Player cho) {
        Board board;
        Optional<Board> optionalBoard = findBoard(han, cho);

        if (optionalBoard.isEmpty()) {
            SetUp hanSetUp = inputView.readSetUp(Team.HAN);
            SetUp choSetUp = inputView.readSetUp(Team.CHO);
            board = createBoard(han, cho, hanSetUp, choSetUp);
            outputView.printGameStart();
            outputView.printBoard(board);
            return board;
        }

        board = optionalBoard.get();
        outputView.printBoard(board);
        return board;
    }

    private Optional<Board> findBoard(final Player han, final Player cho) {
        if (boardDao.findCurrentTurn().isPresent()) {
            List<Piece> hanPieces = pieceDao.findAllByTeam(Team.HAN);
            List<Piece> choPieces = pieceDao.findAllByTeam(Team.CHO);

            Map<Player, Pieces> boardElements = new HashMap<>();
            boardElements.put(han, new Pieces(hanPieces));
            boardElements.put(cho, new Pieces(choPieces));

            return Optional.of(new Board(boardElements));
        }

        return Optional.empty();
    }

    private Board createBoard(final Player han, final Player cho, final SetUp hanSetUp, final SetUp choSetUp) {
        List<Piece> hanPiecesElements = PieceInitializer.createTeamPieces(Team.HAN, hanSetUp);
        List<Piece> choPiecesElements = PieceInitializer.createTeamPieces(Team.CHO, choSetUp);
        hanPiecesElements.forEach(piece -> pieceDao.save(piece, han));
        choPiecesElements.forEach(piece -> pieceDao.save(piece, cho));

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPiecesElements));
        boardElements.put(cho, new Pieces(choPiecesElements));

        boardDao.addBoard(Team.CHO);
        return new Board(boardElements);
    }

    private void process(final Board board, final Player han, final Player cho) {
        Team team = boardDao.findCurrentTurn()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 턴 정보입니다."));
        while (!board.isFinish()) {
            Player currentPlayer = getCurrentTurn(han, cho, team);
            processTurn(currentPlayer, board);
            team = switchTurn(team, boardDao);
        }
    }

    private Player getCurrentTurn(final Player han, final Player cho, final Team team) {
        if (han.team() == team) {
            return han;
        }
        return cho;
    }

    private void processTurn(final Player player, final Board board) {
        Position movingPosition = inputView.readMovingPiecePosition(player);
        Position targetPosition = inputView.readTargetPiecePosition();
        MoveResultDto moveResultDto = board.move(player, movingPosition, targetPosition);
        syncMoveResult(player, moveResultDto, targetPosition);
        outputView.printBoard(board);
    }

    private void syncMoveResult(Player player, MoveResultDto moveResultDto, Position targetPosition) {
        pieceDao.updatePosition(moveResultDto.updatedPiece(), targetPosition);
        moveResultDto.caughtPiece()
                .ifPresent(piece -> pieceDao.delete(piece, Team.getOtherTeam(player.team())));
    }

    private Team switchTurn(final Team team, final BoardDao boardDao) {
        Team other = Team.getOtherTeam(team);
        boardDao.updateCurrentTurn(other);
        return other;
    }

    private void end() {
        boardDao.clear();
        playerDao.clear();
        pieceDao.clear();
    }
}
