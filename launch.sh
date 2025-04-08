#!/bin/sh

# Si l'exécutable n'existe pas, on build
if [ ! -f "build/install/tournament/bin/tournament" ]; then
  ./gradlew installDist
fi

# Lancement de l'application
build/install/tournament/bin/tournament
