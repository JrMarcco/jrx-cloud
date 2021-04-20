#!/bin/bash

appName=

set -e

pid=`ps auxw | grep $appName | grep -v grep | awk '{print $2}'`

if ! [ -z "$pid" ]; then
  kill -n 9 $pid
fi

nohup java -jar $appName -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector --spring.profiles.active=test -Xms256m -Xmx256m >nohup.out 2>&1 &

tail -500f nohup.out