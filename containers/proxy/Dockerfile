FROM nginx:stable-alpine
LABEL version="1.0" \
      maintainer="graas.piaas.support.group@bbva.com" \
      description="Global proxy app"

ARG nginx_path=/etc/nginx/

ADD files/global.conf ${nginx_path}/conf.d/default.conf
ADD files/nginx.conf ${nginx_path}/nginx.conf
