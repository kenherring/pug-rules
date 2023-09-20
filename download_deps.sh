#!/bin/bash

if [ ! -f deps/sonar-openedge-plugin-2.22.3.jar ]; then
  mkdir -p deps
  curl https://github.com/Riverside-Software/sonar-openedge/releases/download/V2.22.3/sonar-openedge-plugin-2.22.3.jar \
    -o deps/sonar-openedge-plugin-2.22.3.jar
fi

echo "download_deps.sh: complete!"