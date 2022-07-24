#!/bin/bash

# define application environment variable
env=$1
# define application absolute path
app_home="$(cd "$(dirname "${BASH_SOURCE}")" && pwd)"
# define application name
app_name="$(basename $(find "${app_home}" -name "*.jar" | xargs ls -ta | head -n 1) ".jar")"
# jar fullname
jar_fullname="${app_name}.jar"
# executable jar path
jar_path="${app_home}/${jar_fullname}"
# nohup
nohup_path="${app_home}/nohup.out"
# java opts
java_opts="-Xms1g -Xmx2g"

# print color message
print() {
  echo "$@" | sed \
    -e "s/\(\(@\(red\|green\|yellow\|blue\|magenta\|cyan\|white\|reset\|b\|u\)\)\+\)[[]\{2\}\(.*\)[]]\{2\}/\1\4@reset/g" \
    -e "s/@red/$(tput setaf 1)/g" \
    -e "s/@green/$(tput setaf 2)/g" \
    -e "s/@yellow/$(tput setaf 3)/g" \
    -e "s/@blue/$(tput setaf 4)/g" \
    -e "s/@magenta/$(tput setaf 5)/g" \
    -e "s/@cyan/$(tput setaf 6)/g" \
    -e "s/@white/$(tput setaf 7)/g" \
    -e "s/@reset/$(tput sgr0)/g" \
    -e "s/@b/$(tput bold)/g" \
    -e "s/@u/$(tput sgr 0 1)/g"
}

# echo
echo_env() {
  print @b "The bootstrap current environment is:@blue ${env}" @reset
  print @b "The bootstrap application name:@blue ${app_name}" @reset
  print @b "The bootstrap application java opts:@magenta ${java_opts}" @reset
  print @b "The bootstrap shell script folder path:@cyan ${app_home}" @reset
  print @b "The bootstrap executable jar package path:@cyan ${jar_path}" @reset
  print @b "The bootstrap nohup path:@cyan ${nohup_path}" @reset
}

# error message
err() {
  print @b@red "$1" @reset
  exit 1
}

# check environment variable available
check_env() {
  if [ -z "${env}" ]; then
    print @b@red environment variable is empty start failed. @reset
    exit 1
  fi

  if [ "${app_name}" == ".jar" ]; then
    print @b@red application name is empty start failed. @reset
    exit 1
  fi

  if [ "${env}"x = "prod"x ]; then
    java_opts="-Xms4g -Xmx4g \
              -XX:+UseG1GC \
              -XX:+HeapDumpOnOutOfMemoryError \
              -XX:HeapDumpPath=dump.hprof \
              -XX:+PrintGCDetails \
              -XX:+PrintGCTimeStamps \
              -XX:+PrintHeapAtGC \
              -XX:+PrintGCApplicationStoppedTime \
              -XX:+PrintReferenceGC \
              -Dcom.sun.management.jmxremote.authenticate=false \
              -Dcom.sun.management.jmxremote.ssl=false \
              -javaagent:/root/.arms/agent/arms-bootstrap-1.7.0-SNAPSHOT.jar \
              -Darms.licenseKey=i0xn2zgi7b@bc166826d291e15 \
              -Darms.appName=pro-${app_name}"
  elif [ "${env}"x = "staging"x ]; then
    java_opts="-Xms2g -Xmx4g"
  elif [ "${env}"x = "testing"x ]; then
    java_opts="-Xms1g -Xmx2g"
  elif [ "${env}"x = "dev"x ]; then
    java_opts="-Xms1g -Xmx2g"
  else
    err "${env} environment that doesn't exist"
    exit 1
  fi
}

# start application
app_start() {
  nohup java ${java_opts} -Dio.netty.noPreferDirect=true -jar ${jar_path} >"${nohup_path}" &
  print @b@green Start application success. @reset
}

# stop application
app_stop() {
  #get application pid
  PID=$(ps -ef | grep "${app_name}".jar | grep -v grep | awk '{ print $2}')

  # check pid and stop
  if [ -z "${PID}" ]; then
    print @b@yellow Application is already stopped. @reset
  else
    print @b Stopping application. @reset
    print @b@red kill "${PID}" @reset
    kill -9 "${PID}"
    print @b@green Stop application success. @reset
  fi
}

main() {
  # check env
  check_env
  # echo env
  echo_env
  #stop application
  app_stop
  # sleep 3 second,linux release memory
  sleep 3
  #start application
  app_start ${env}
}

main
