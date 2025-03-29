package repository;

public class Schema {
    public static final String CREATE_TEAM = """
            CREATE TABLE IF NOT EXISTS TEAM (
                team_name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );
            
            INSERT IGNORE INTO TEAM (name) VALUES ('CHO'), ('HAN');
            
            """;

    public static final String CREATE_PIECE_TYPE = """
            CREATE TABLE IF NOT EXISTS PIECE_TYPE (
                piece_type_name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
            );
            
            INSERT IGNORE INTO PIECE_TYPE (name) VALUES ('KING'), ('CHARIOT'), ('CANNON'), ('HORSE'), ('ELEPHANT'), ('SOLDIER'), ('PAWN'), ('BLANK');
            
            """;

    public static final String CREATE_PIECE = """
            CREATE TABLE IF NOT EXISTS PIECE (
                 piece_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                 row_index INT NOT NULL,
                 column_index INT NOT NULL,
                 piece_type_name VARCHAR(20) NOT NULL,
                 team_name VARCHAR(20) NOT NULL,
                 UNIQUE(row_index, column_index),
            
                  FOREIGN KEY (piece_type_name) REFERENCES piece_type(name)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE,
                  FOREIGN KEY (team_name) REFERENCES team(name)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE,
             );""";

    public static final String CREATE_TABLE = CREATE_TEAM + CREATE_PIECE_TYPE
            + CREATE_PIECE;
}
