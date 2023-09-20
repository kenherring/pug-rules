#!/bin/bash
set -eou -pipefail

# docker compose up -d
docker cp target/project-local-repo/ken.herring.pug.rules/pug-rules-plugin/2023.09.20/pug-rules-plugin-2023.09.20.jar \
       sonarqube:/opt/sonarqube/extensions/downloads/
docker cp deps/sonar-openedge-plugin-2.22.3.jar \
       sonarqube:/opt/sonarqube/extensions/downloads/
docker compose down
docker compose up -d

echo "TODO: wait for successful start"

WAIT_COUNT=0
while [ "$(docker logs sonarqube | grep "SonarQube is operational")" = "" ]; do
  WAIT_COUNT=$((WAIT_COUNT+1))
  echo "waiting ($WAIT_COUNT) for SonarQube to start..."
  tail -1 < <(docker logs sonarqube)
  sleep 3
done

echo "docker_deploy.sh: complete!"
