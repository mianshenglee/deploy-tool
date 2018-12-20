#!/bin/bash
#设置目录
BASE_DIR=$(dirname $(pwd))

APP_HOME=${BASE_DIR}/software/redis/bin
COMMON_HOME=${BASE_DIR}/install/scripts/linux/common

echo "安装redis......"

#yum install lsof

#启动服务并设置为开机启动
AUTO_RUN_FILE=${APP_HOME}/redisd.sh
AUTO_RUN_FILE_NAME=redis

#启动服务
bash ${AUTO_RUN_FILE} start
bash ${COMMON_HOME}/set_auto_start.sh ${AUTO_RUN_FILE} ${AUTO_RUN_FILE_NAME}

echo "安装redis完成"
