#!/bin/bash

#set -eo pipefail

echo "process start ... "

echo "setp 1/5: kill pidfile.txt"
if [ -e ./pidfile.txt ]; then
  kill -9 `cat pidfile.txt`
fi

cd ../

echo "setp 2/5: git push"
git pull

# 编译项目
echo "setp 3/5: mvn package"
cd ./webServer/
mvn clean package -Dmaven.test.skip=true

echo "setp 4/5: run jar"
nohup java -jar ./target/oa-0.0.1.jar & echo $! > pidfile.txt

echo "finshed."
