#!/bin/bash
#设置目录
BASE_DIR=$(dirname $(pwd))
APP_HOME=${BASE_DIR}/software/redis/bin
COMMON_HOME=${BASE_DIR}/install/scripts/linux/common

echo "开始卸载redis......"
AUTO_RUN_FILE=${APP_HOME}/redisd.sh
AUTO_RUN_FILE_NAME=redis

#关闭服务
bash ${AUTO_RUN_FILE} stop

#删除自启动
bash ${COMMON_HOME}/delete_auto_start.sh ${AUTO_RUN_FILE_NAME}

echo "卸载redis完成"
