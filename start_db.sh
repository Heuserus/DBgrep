#!/bin/bash
set -e
dir="$(echo $0 | sed "s/\/filldb\.sh//g")/docker"

cleanup()
{
    echo "cleanup..."
    docker-compose -f "$dir/docker-compose.yml" down
}

trap "cleanup" INT KILL TERM STOP

docker-compose -f "$dir/docker-compose.yml" up -d

docker-compose -f "$dir/docker-compose.yml" logs -f