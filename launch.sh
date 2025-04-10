#!/bin/sh

COMPILED_FILE="build/install/tournament/bin/tournament"
TIMESTAMP_FILE=".last-build.timestamp"
needs_rebuild=false


if [ ! -f "$COMPILED_FILE" ] || [ ! -f "$TIMESTAMP_FILE" ]; then
  echo "🧩 First compilation in progress"
  needs_rebuild=true
else
  if find src -type f -newer "$TIMESTAMP_FILE" | grep -q . || \
     [ settings.gradle.kts -nt "$TIMESTAMP_FILE" ] || \
     [ build.gradle.kts -nt "$TIMESTAMP_FILE" ] || \
     [ gradle/libs.versions.toml -nt "$TIMESTAMP_FILE" ]; then
    echo "🎯 Changes detected, a build is necessary"
    needs_rebuild=true
  fi
fi

if [ "$needs_rebuild" = true ]; then
  ./gradlew installDist
  touch "$TIMESTAMP_FILE"
else
  echo "🏆 No changes detected, app will start shortly"
fi

echo "🥏 Launching the application"
"$COMPILED_FILE"
