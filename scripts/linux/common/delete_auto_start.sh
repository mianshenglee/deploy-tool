#!/bin/bash

#参数1：开机启动名称(如emb-service)
AUTO_RUN_FILE_NAME=$1

echo "删除${AUTO_RUN_FILE_NAME}的开机启动......"

chkconfig --del ${AUTO_RUN_FILE_NAME}
rm -f /etc/init.d/${AUTO_RUN_FILE_NAME}

echo "删除${AUTO_RUN_FILE_NAME}开机启动结束"
