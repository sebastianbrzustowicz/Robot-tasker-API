FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=mysecretpassword
ENV MYSQL_DATABASE=robotTaskerApi

COPY ./Database_init.sql /docker-entrypoint-initdb.d/
