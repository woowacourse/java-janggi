docker-compose down -v && docker-compose up

docker exec -i janggi-mysql mysql -u root -proot janggi < ./init.sql

