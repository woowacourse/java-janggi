docker-compose up -d
echo "데이터베이스 서버가 완전히 켜지기까지 3초 대기중.."
sleep 3
./gradlew shadowjar &&
docker exec -i janggi-mysql mysql -u root -proot janggi < ./init.sql
java -jar ./build/libs/*.jar
