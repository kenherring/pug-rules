#!/bin/bash
set -eou pipefail

if ! command -v mvn; then
  mkdir -p ~/maven
  cd ~/maven
  curl https://dlcdn.apache.org/maven/maven-4/4.0.0-alpha-7/binaries/apache-maven-4.0.0-alpha-7-bin.tar.gz -O
  tar -xzvf ~/maven/apache-maven-4.0.0-alpha-7-bin.tar.gz
fi
