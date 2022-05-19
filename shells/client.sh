#!/bin/bash

DIA=`date +"%Y%m%d"`

if [ ! -d ./log ]; then
	mkdir log
	sleep 10
fi

java -DconnectionType=client -Dip=$1 -Dport=$2 -jar connection-1.1.0.4.jar > ./log/client-$1:$2-$DIA.log &