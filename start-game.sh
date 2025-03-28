docker-compose up -d
echo "데이터베이스 서버가 완전히 켜지기까지 3초 대기중.."
sleep 3
./gradlew shadowjar &&
java -jar ./build/libs/java-janggi-1.0-SNAPSHOT-all.jar
