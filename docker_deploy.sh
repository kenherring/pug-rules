#!/bin/bash
set eou -pipefail

# docker compose up -d
docker cp target/project-local-repo/ken.herring.pug.rules/pug-rules-plugin/2023.09.20/pug-rules-plugin-2023.09.20.jar \
       sonarqube:/opt/sonarqube/extensions/plugins/
docker cp deps/sonar-openedge-plugin-2.22.3.jar \
       sonarqube:/opt/sonarqube/extensions/plugins/
docker compose down
docker compose up -d

echo "TODO!!!"
