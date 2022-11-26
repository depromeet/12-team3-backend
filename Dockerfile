FROM openjdk:17
ARG JAR_FILE=application/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xms500M","-Xmx500M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/home/ahmatda/HeapDump.dump","-Dspring.profiles.active=production","-jar","/app.jar"]
