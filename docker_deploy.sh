#!/bin/bash
set -eou pipefail

echo 100
# docker compose up -d
echo 101.1
if [ "$(docker compose ps -a -q)" = "" ]; then
  docker compose up -d
fi
echo 101.2
docker cp target/pug-rules-plugin-2023.09.20.jar sonarqube:/opt/sonarqube/extensions/downloads/pug-rules-plugin-2023.09.20.jar
echo 102
docker cp deps/sonar-openedge-plugin-2.22.3.jar sonarqube:/opt/sonarqube/extensions/downloads/sonar-openedge-plugin-2.22.3.jar
echo 103
docker compose down
echo 104
docker compose up -d --force-recreate
echo 105

echo "TODO: wait for successful start"

WAIT_COUNT=0
while [ "$(docker logs sonarqube | grep "SonarQube is operational")" = "" ]; do
  WAIT_COUNT=$((WAIT_COUNT+1))
  echo "waiting ($WAIT_COUNT) for SonarQube to start..."
  tail -1 < <(docker logs sonarqube)
  sleep 3
done

echo "docker_deploy.sh: complete!"
