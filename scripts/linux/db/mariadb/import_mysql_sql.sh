#!/bin/bash
BASE_DIR=$(dirname $(pwd))
MYSQL_HOME=${BASE_DIR}/software/mariadb
SQL_HOME=${BASE_DIR}/resource/sql
JRE_HOME=${BASE_DIR}/software/jre8
TOOL_HOME=${BASE_DIR}/install/systemfile/tool

db_username=$1
db_password=$2
db_port=$3

#模拟telnet命令检测某端口是否通的，当发现没连通时会不断检测，直到超时
#%1为ip，%2为端口，%3为telnet间隔，%4为超时时间
${JRE_HOME}/bin/java -jar ${TOOL_HOME}/telnet-tool.jar 127.0.0.1 ${db_port} 1000 60000

#执行建库脚本
echo "正在创建dorcst库,时间可能会较长,请稍候..."
${MYSQL_HOME}/bin/mysql -u${db_username} -p${db_password} --socket=${MYSQL_HOME}/var/mysql.sock < ${SQL_HOME}/dorcst.sql

echo "dorcst库安装成功"
