package config;

public record DatabaseProperties(
    String url,
    String user,
    String password
) {

    public DatabaseProperties() {
        this(
            requireEnv("JANGGI_DB_URL"),
            requireEnv("JANGGI_DB_USER"),
            requireEnv("JANGGI_DB_PASSWORD")
        );
    }

    private static String requireEnv(String key) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(key + " 환경변수가 설정되지 않았습니다.");
        }
        return value;
    }
}
