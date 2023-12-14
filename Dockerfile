FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=mysecretpassword
ENV MYSQL_DATABASE=mydatabase

COPY ./my-database-init.sql /docker-entrypoint-initdb.d/
