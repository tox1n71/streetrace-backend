FROM postgres:latest
COPY db2.sql /docker-entrypoint-initdb.d/db.sql
