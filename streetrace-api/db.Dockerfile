FROM postgres:latest
COPY db3.sql /docker-entrypoint-initdb.d/db.sql
