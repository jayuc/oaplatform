#!/bin/bash

#set -eo pipefail

cd /code/oaplatform/

echo "process start ... " >> ./webServer/nohup.out

echo "setp 1/5: git pull" >> ./webServer/nohup.out
git pull > pullResult.text

# 如果没有代码更新，则不部署
pullR=`cat pullResult.text`
if [ `echo $pullR | sed s/[[:space:]]//g` = "Alreadyuptodate." ];then
    echo "Already up to date." >> ./webServer/nohup.out
	exit 0
fi

cd ./webServer/

echo "setp 2/5: kill pidfile.txt" >> ./nohup.out
runingPid=`cat pidfile.txt`
if ps -p $runingPid > /dev/null
then
   echo "kill -9 $runingPid" >> ./nohup.out
   kill -9 $runingPid
fi

echo "setp 3/5: kill nohup.out" >> ./nohup.out
if [ -e ./nohup.out ]; then
echo "rm -rf ./nohup.out" >> ./nohup.out
#  rm -rf ./nohup.out
fi

# 编译项目
echo "setp 4/5: mvn package" >> ./nohup.out

mvn clean package -Dmaven.test.skip=true >> ./nohup.out

echo "setp 5/5: run jar" >> ./nohup.out
nohup java -jar ./target/oa-0.0.1.jar & echo $! > pidfile.txt

echo "finshed." >> ./nohup.out
