#!/bin/bash

#set -eo pipefail

echo "process start ... "

echo "setp 1/5: kill pidfile.txt"
if [ -e ./pidfile.txt ]; then
  kill -9 `cat pidfile.txt`
fi

echo "setp 2/5: kill nohup.out"
if [ -e ./nohup.out ]; then
  rm ./nohup.out
fi

cd ../

echo "setp 3/5: git pull"
git pull > pullResult.text

if [ "Already up to date." = `cat pullResult.text` ];then
    echo "Already up to date."
	exit 0
fi

# 编译项目
echo "setp 4/5: mvn package"
cd ./webServer/
mvn clean package -Dmaven.test.skip=true

echo "setp 5/5: run jar"
nohup java -jar ./target/oa-0.0.1.jar & echo $! > pidfile.txt

echo "finshed."
