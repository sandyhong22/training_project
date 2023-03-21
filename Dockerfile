FROM mysql:8.0

COPY manifests/setdb/setup.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=12345678
ENV MYSQL_DATABASE=demo


