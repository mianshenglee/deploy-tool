#!/bin/sh
#
#
# chkconfig: 2345 92 90
# description: redis

APP_HOME=$${deployment_home}/software/redis

RUNNING_USER=root

start() {
	$APP_HOME/bin/redis-server $APP_HOME/redis.conf
}

stop() {
	echo "准备关闭redis服务"
	PID=`ps aux|grep "$APP_HOME/bin/redis-server"|grep -v grep|awk '{print $2}'`
	if [ ! -z "${PID}" ];then
		echo "------- 关闭redis进程：${PID}"
		kill -9 ${PID}
		echo "------- 关闭redis服务完毕，结果代码：$?"
	else
		echo "------- 没有发现进程:${PNAME}"
	fi
}


case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
  *)
echo "Usage: $0 {start|stop|restart}"
exit 1
esac 
exit 0