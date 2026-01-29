FROM busybox as source

RUN mkdir -p /axonserver/config /axonserver/data /axonserver/events /axonserver/log /axonserver/exts

FROM gcr.io/distroless/java:11

COPY --from=source /axonserver /axonserver
COPY axonserver.jar axonserver.properties /axonserver/

WORKDIR /axonserver

VOLUME [ "/axonserver/config", "/axonserver/data", "/axonserver/events", "/axonserver/log", "/axonserver/exts", "/axonserver/plugins"  ]
EXPOSE 8024/tcp 8124/tcp 8224/tcp

ENTRYPOINT [ "java", "-jar", "./axonserver.jar" ]