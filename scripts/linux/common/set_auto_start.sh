#!/bin/bash

#参数1：开机启动脚本路径（如/opt/package/message/emb/emb-service.sh）、参数2：开机启动名称(如emb-service)

AUTO_RUN_FILE=$1
AUTO_RUN_FILE_NAME=$2

echo "设置${AUTO_RUN_FILE_NAME}开机启动......"

#若文件不存在，报错、否则设置开机启动
if [ ! -f "${AUTO_RUN_FILE}" ]; then 
	echo "${AUTO_RUN_FILE} 不存在，请检查文件!"
else
	\cp -f ${AUTO_RUN_FILE} /etc/init.d/${AUTO_RUN_FILE_NAME}
	chmod 777 /etc/init.d/${AUTO_RUN_FILE_NAME} 
	chkconfig --add ${AUTO_RUN_FILE_NAME} 
	chkconfig ${AUTO_RUN_FILE_NAME} on 
fi

echo "设置${AUTO_RUN_FILE_NAME}开机启动结束"
