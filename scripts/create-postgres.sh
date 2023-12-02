
docker rm -f balances-db

docker run --name balances-db \
           -p "7001:5432" \
           -v "./../init-db.sh:/docker-entrypoint-initdb.d/init-user-db.sh" \
           -e POSTGRES_URL=database \
           -e POSTGRES_USER=postgres \
           -e POSTGRES_PASSWORD=postgres \
           -d postgres
