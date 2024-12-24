FROM postgres:latest
COPY db4.sql /docker-entrypoint-initdb.d/db.sql
