FROM openjdk:latest
COPY ./out/artifacts/app/ /usr/src/myapp
EXPOSE 4573
WORKDIR /usr/src/myapp
RUN apt-get update && apt-get install -y --no-install-recommends \
	paxctl
RUN paxctl -c /usr/bin/java && paxctl -m /usr/bin/java
CMD java -jar app.jar
