#!/bin/bash
#定位目录
BASE_DIR=$(dirname $(pwd))
MYSQL_HOME=${BASE_DIR}/software/mariadb
COMMON_HOME=${BASE_DIR}/install/scripts/linux/common

db_username=$1
db_password=$2

echo "准备卸载mysql数据库"
${MYSQL_HOME}/bin/mysqladmin  -u${db_username}  -p${db_password} --socket=${MYSQL_HOME}/var/mysql.sock shutdown
echo  "正在停止mysql..................................[ok]"

#删除自启动
bash ${COMMON_HOME}/delete_auto_start.sh mysqld

echo "卸载mysql数据库完毕"
