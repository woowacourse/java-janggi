package gameloader;

public class GameDataHandler {
/*
    private final DbConnection dbConnection;

    public GameDataHandler() {
        this.dbConnection = DbConnection.getInstance();
    }

    // 새로운 게임을 시작하고 게임 데이터를 데이터베이스에 저장
    public int startNewGame(int player1Id, int player2Id, Map<Position, Piece> board) throws SQLException {
        String insertGameSql = "INSERT INTO games (game_status, current_turn, player1_id, player2_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement gameStmt = connection.prepareStatement(insertGameSql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            gameStmt.setString(1, "ONGOING");
            gameStmt.setInt(2, 1);  // 현재 턴은 1
            gameStmt.setInt(3, player1Id);
            gameStmt.setInt(4, player2Id);
            gameStmt.executeUpdate();

            // 게임 ID 가져오기
            ResultSet generatedKeys = gameStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newGameId = generatedKeys.getInt(1);
                insertBoardState(newGameId, board);
                return newGameId;  // 새로 생성된 게임 ID 반환
            }
        }
        return -1;  // 오류가 발생한 경우 -1 반환
    }

    // 보드 상태를 데이터베이스에 저장하는 메소드
    private void insertBoardState(int gameId, Map<Position, Piece> board) throws SQLException {
        String insertPieceSql = "INSERT INTO pieces (game_id, player_id, piece_type, position_x, position_y) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pieceStmt = connection.prepareStatement(insertPieceSql)) {

            // 각 Piece를 데이터베이스에 저장
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                pieceStmt.setInt(1, gameId);
                pieceStmt.setInt(2, piece.getPlayer().getId());
                pieceStmt.setString(3, piece.getClass().getSimpleName());  // 예: "Pawn", "Cannon" 등
                pieceStmt.setInt(4, position.getColumn());
                pieceStmt.setInt(5, position.getRow());
                pieceStmt.executeUpdate();
            }
        }
    }

    // 게임 데이터를 불러오는 메소드
    public Map<Position, Piece> loadGameBoard(int gameId) throws SQLException {
        Map<Position, Piece> board = new HashMap<>();

        String selectPiecesSql = "SELECT * FROM pieces WHERE game_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pieceStmt = connection.prepareStatement(selectPiecesSql)) {

            pieceStmt.setInt(1, gameId);
            ResultSet pieceResult = pieceStmt.executeQuery();

            while (pieceResult.next()) {
                int row = pieceResult.getInt("position_x");
                int column = pieceResult.getInt("position_y");
                String pieceType = pieceResult.getString("piece_type");
                String player_id = pieceResult.getString("player_id");

                // Piece 객체 생성 (필요에 따라 수정)
                Piece piece = createPiece(pieceType, player_id);
                Position position = new Position(column, row);
                board.put(position, piece);
            }
        }

        return board;
    }
    */
}
