package janggi.controller;

import janggi.dao.PieceDao;
import janggi.domain.Piece;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardBuilder;
import janggi.domain.board.maSangStrategy.MaSangMaSang;
import janggi.domain.board.maSangStrategy.MaSangSangMa;
import janggi.domain.board.maSangStrategy.MaSangStrategy;
import janggi.domain.board.maSangStrategy.SangMaMaSang;
import janggi.domain.board.maSangStrategy.SangMaSangMa;
import janggi.repository.DockerRepository;
import janggi.repository.Repository;
import janggi.service.GameService;
import janggi.service.LocalGameService;
import janggi.service.OnlineGameService;
import janggi.service.PlayingTurn;
import janggi.view.BoardInitiliazeView;
import java.util.Map;
import java.util.Set;

public class ApplicationConfigurer {

    private final Map<Integer, MaSangStrategy> boardCreateStrategy = Map.of(
        1, new MaSangSangMa(),
        2, new MaSangMaSang(),
        3, new SangMaSangMa(),
        4, new SangMaMaSang()
    );

    private final BoardInitiliazeView boardInitiliazeView;

    public ApplicationConfigurer(final BoardInitiliazeView view) {
        this.boardInitiliazeView = view;
    }

    public GameService appropriateGameService() {
        if (successfullyConnectedDB()) {
            Repository repository = new DockerRepository(new PieceDao());
            Board board = loadBoard(repository);
            return new OnlineGameService(board, repository);
        }

        boardInitiliazeView.printConnectionFailed();
        Board board = createBoard();
        return new LocalGameService(board);
    }

    private boolean successfullyConnectedDB() {
        final var pieceDao = new PieceDao();
        try {
            if (pieceDao.getConnection() != null) {
                return true;
            }

        } catch (RuntimeException e) {
        }
        return false;
    }

    private Board loadBoard(Repository repository) {
        Set<Piece> existingPieces = repository.findAll();
        final var continuePreviousGame = boardInitiliazeView.readRenewGame();
        if (continuePreviousGame) {
            boardInitiliazeView.printContinueGame();
            return new Board(existingPieces);
        }

        repository.clear();
        repository.updateTurn(new PlayingTurn());

        Board board = createBoard();
        board.getPieces().values().forEach(repository::save);
        return board;
    }

    private Board createBoard() {
        int hanTableSetting = boardInitiliazeView.readTableSetting(Team.HAN);
        int choTableSetting = boardInitiliazeView.readTableSetting(Team.CHO);
        return createBoard(hanTableSetting, choTableSetting);
    }

    private Board createBoard(final int hanTableSetting, final int choTableSetting) {
        final var hanBoardStrategy = boardCreateStrategy.get(hanTableSetting);
        final var choBoardStrategy = boardCreateStrategy.get(choTableSetting);

        return new BoardBuilder()
            .initTeam(Team.HAN, hanBoardStrategy)
            .initTeam(Team.CHO, choBoardStrategy)
            .build();
    }
}
