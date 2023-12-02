set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE DATABASE "loyalty-program-balances-service";
  GRANT ALL PRIVILEGES ON DATABASE "loyalty-program-balances-service" TO postgres;

  \connect "loyalty-program-balances-service";
  CREATE SCHEMA cards;
EOSQL
