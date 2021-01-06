#!/bin/bash

#set -eo pipefail

echo "process start ... "

echo "setp 1/4: kill pidfile.txt"
if [ -e ./pidfile.txt ]; then
  kill -9 `cat pidfile.txt`
fi

cd ../

echo "setp 2/4: git push"
git pull

# 编译项目
echo "setp 3/4: mvn package"
cd ./webServer/
mvn clean package -Dmaven.test.skip=true

echo "setp 4/4: run jar"
nohup java -jar ./target/oa-0.0.1.jar & echo $! > pidfile.txt

echo "finshed."
