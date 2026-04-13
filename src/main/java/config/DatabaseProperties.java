package config;

public record DatabaseProperties(
    String url,
    String user,
    String password
) {

    public DatabaseProperties() {
        this(
            "jdbc:mysql://localhost:3306/JANGGI?serverTimezone=Asia/Seoul",
            "root",
            "1234"
        );
    }
}
