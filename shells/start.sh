#!/usr/bin/env bash

java -DConnectionType=$1 -DPort=$2 -DIPClient=$3 -jar connection-1.0.0.1.jar