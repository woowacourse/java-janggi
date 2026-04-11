package service;

import domain.Position;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardSnapshot;
import domain.board.BoardSnapshots;
import domain.board.BoardStates;
import domain.board.TableSetting;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import dto.GameInfo;
import dto.PositionState;
import dto.TurnHistory;
import infrastructure.TransactionManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.GameInfoRepository;
import repository.PositionHistoryRepository;
import repository.PositionStateRepository;
import repository.TurnHistoryRepository;
import view.InputParser;

public class JanggiService {
    private final GameInfoRepository gameInfoRepository;
    private final PositionStateRepository positionStateRepository;
    private final PositionHistoryRepository positionHistoryRepository;
    private final TurnHistoryRepository turnHistoryRepository;
    private final TransactionManager transactionManager;

    public JanggiService(
            GameInfoRepository gameInfoRepository,
            PositionStateRepository positionStateRepository,
            PositionHistoryRepository positionHistoryRepository,
            TurnHistoryRepository turnHistoryRepository,
            TransactionManager transactionManager) {
        this.gameInfoRepository = gameInfoRepository;
        this.positionStateRepository = positionStateRepository;
        this.positionHistoryRepository = positionHistoryRepository;
        this.turnHistoryRepository = turnHistoryRepository;
        this.transactionManager = transactionManager;
    }

    public List<Integer> readAllGameInfoIds() {
        List<GameInfo> gameInfos = transactionManager.transaction(gameInfoRepository::findAllGameInfos);
        if (gameInfos.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 현재 저장된 보드가 없습니다.");
        }
        return gameInfos.stream()
                .map(GameInfo::id)
                .toList();
    }

    public int makeBoard(TableSetting choTableSetting, TableSetting hanTableSetting) {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.create(choTableSetting, hanTableSetting);
        int gameInfoId = transactionManager.transaction(gameInfoRepository::saveGameInfo);
        initBoardState(board.getPieceInfos(), gameInfoId);
        return gameInfoId;
    }

    private void initBoardState(PieceInfos pieceInfos, int gameInfoId) {
        for (Position position : pieceInfos.getKeys()) {
            transactionManager.transaction(connection -> {
                positionStateRepository.savePositionState(position, pieceInfos.get(position), gameInfoId, connection);
            });
        }
    }

    public Board readBoard(int gameInfoId) {
        GameInfo gameInfo = findGameInfoById(gameInfoId);
        BoardStates boardStates = readPositionStates(gameInfoId);
        BoardSnapshots boardSnapshots = loadBoardSnapshots(gameInfoId);
        CountryType turn = CountryType.valueOf(gameInfo.turn());
        return new Board(boardStates, boardSnapshots, turn);
    }

    public GameInfo findGameInfoById(int gameInfoId) {
        return transactionManager.transaction(connection -> {
            return gameInfoRepository.findGameInfoById(gameInfoId, connection);
        });
    }

    public int readLoadBoard(String input) {
        int boardId = InputParser.parseBoardId(input);
        if (!readAllGameInfoIds().contains(boardId)) {
            throw new IllegalArgumentException("[ERROR] 해당 번호의 board가 존재하지 않습니다.");
        }
        return boardId;
    }

    private BoardStates readPositionStates(int gameInfoId) {
        List<PositionState> positionStates = transactionManager.transaction(connection -> {
            return positionStateRepository.findAllPositionStatesByGameInfoId(gameInfoId, connection);
        });
        Map<Position, Piece> boardStates = new HashMap<>();
        for (PositionState positionState : positionStates) {
            Position position = new Position(positionState.x(), positionState.y());
            String pieceType = positionState.pieceType();
            CountryType countryType = CountryType.valueOf(positionState.countryType());
            Piece piece = PieceFactory.valueOf(pieceType).create(countryType);
            boardStates.put(position, piece);
        }
        return new BoardStates(boardStates);
    }

    public BoardSnapshots loadBoardSnapshots(int gameInfoId) {
        BoardSnapshots boardSnapshots = new BoardSnapshots();
        for (TurnHistory turnHistory : loadTurnHistories(gameInfoId)) {
            List<PositionState> positionHistories = transactionManager.transaction(connection -> {
                return positionHistoryRepository.findPositionHistoriesByTurnHistoryId(turnHistory.id(), connection);
            });
            BoardSnapshot boardSnapshot = makeBoardSnapshot(positionHistories, turnHistory.turn());
            boardSnapshots.addBoardSnapshot(boardSnapshot);
        }
        return boardSnapshots;
    }

    private List<TurnHistory> loadTurnHistories(int gameInfoId) {
        return transactionManager.transaction(connection -> {
            return turnHistoryRepository.findTurnHistoriesByGameInfoId(gameInfoId, connection);
        });
    }

    private BoardSnapshot makeBoardSnapshot(List<PositionState> positionHistories, String countryTurn) {
        Map<Position, PieceInfo> pieceInfos = new HashMap<>();
        for (PositionState positionHistory : positionHistories) {
            Position position = new Position(positionHistory.x(), positionHistory.y());

            PieceType pieceType = PieceType.valueOf(positionHistory.pieceType());
            CountryType countryType = CountryType.valueOf(positionHistory.countryType());
            PieceInfo pieceInfo = new PieceInfo(pieceType, countryType);

            pieceInfos.put(position, pieceInfo);
        }
        return new BoardSnapshot(new PieceInfos(pieceInfos), CountryType.valueOf(countryTurn));
    }

    public Position makePosition(String input) {
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }

    public void movePiece(Board board, Position from, Position to, int gameInfoId) {
        board.movePiece(from, to);
        BoardSnapshot boardSnapshot = new BoardSnapshot(board.getPieceInfos(), board.getTurn());
        board.addBoardSnapshot(boardSnapshot);
        int turnHistoryId = insertTurnHistory(gameInfoId, board.getTurn());
        insertPositionHistory(board.getPieceInfos(), turnHistoryId);
        changePositionStateToAndFrom(from, to, board.getPieceInfos(), gameInfoId);
    }

    public int insertTurnHistory(int gameInfoId, CountryType turn) {
        return transactionManager.transaction(connection -> {
            return turnHistoryRepository.saveTurnHistory(gameInfoId, turn, connection);
        });
    }

    public void insertPositionHistory(PieceInfos pieceInfos, int turnHistoryId) {
        transactionManager.transaction(connection -> {
            positionHistoryRepository.savePositionHistory(pieceInfos, turnHistoryId, connection);
        });
    }

    public void changePositionStateToAndFrom(Position from, Position to, PieceInfos pieceInfos, int gameInfoId) {
        if (isEmptyPosition(to, gameInfoId)) {
            transactionManager.transaction(connection -> {
                positionStateRepository.savePositionState(to, pieceInfos.get(to), gameInfoId, connection);
                positionStateRepository.deletePositionStateByPosition(from, gameInfoId, connection);
            });
            return;
        }
        transactionManager.transaction(connection -> {
            positionStateRepository.updatePositionState(to, pieceInfos.get(to), gameInfoId, connection);
            positionStateRepository.deletePositionStateByPosition(from, gameInfoId, connection);
        });
    }

    private boolean isEmptyPosition(Position position, int gameInfoId) {
        PositionState positionState = transactionManager.transaction(connection -> {
            return positionStateRepository.findPositionStateByPosition(position, gameInfoId, connection);
        });
        return positionState == null;
    }

    public void updateGameInfo(CountryType countryType, int id) {
        transactionManager.transaction(connection -> {
            gameInfoRepository.updateGameInfo(countryType, id, connection);
        });
    }

    public void deleteAllByGameInfoId(int gameInfoId) {
        transactionManager.transaction(connection -> {
            positionStateRepository.deleteAllPositionStatesByGameInfoId(gameInfoId, connection);
            for (TurnHistory turnHistory : loadTurnHistories(gameInfoId)) {
                positionHistoryRepository.deletePositionHistoriesByTurnHistoryId(turnHistory.id(), connection);
            }
            turnHistoryRepository.deleteAllTurnHistoriesByGameInfoId(gameInfoId, connection);
            gameInfoRepository.deleteGameInfo(gameInfoId, connection);
        });
    }
}
