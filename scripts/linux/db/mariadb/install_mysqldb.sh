#!/bin/bash
#安装mysql
BASE_DIR=$(dirname $(pwd))
MYSQL_HOME=${BASE_DIR}/software/mariadb
COMMON_HOME=${BASE_DIR}/install/scripts/linux/common

db_password=$1

AUTO_RUN_FILE=${MYSQL_HOME}/support-files/mysql.server
AUTO_RUN_FILE_NAME=mysqld

#安装mysql的依赖包
if [[ $MYSQLDB_HAS_INSTALL != "y" ]];then
	#1.安装依赖
	yum install -y cmake gcc gcc-c++ ncurses ncurses-devel openssl openssl-devel perl
	
	#2.创建mysql用户，查看是否有mysql用户，没有则创建
	grep mysql /etc/passwd &>/dev/null
	if [ "$?" -ne 0 ];then
	  useradd mysql
	fi
	#授权mysql用户的mysql目录权限
	chown -R mysql:mysql ${MYSQL_HOME}
	
	#3.创建数据库文件
	echo -n "正在安装mysql数据库....."
	cd ${MYSQL_HOME} && ./scripts/mysql_install_db --defaults-file=${MYSQL_HOME}/my.cnf &>/dev/null &
	while true
	do
	   ps -ef | grep -w mysql_install_db | grep -v "grep" &>/dev/null
	   if [ "$?" -ne 0 ];then
		   echo "[ok]"
		   break
	   else
		   echo -n "....."
		   sleep "0.5"
	   fi
	done

	#启动mysql
	cd ${MYSQL_HOME} && ./bin/mysqld_safe --defaults-file=${MYSQL_HOME}/my.cnf &>/dev/null &
	echo -n "正在启动mysql"
	while true
	do
	  if [ ! -e  ${MYSQL_HOME}/var/mysqld.pid ];then
	    echo -n "........"
	    sleep "0.5"
	  else
	    break
	  fi
	done 
	echo "[ok]"

	#开机自启动
	\cp -f ${MYSQL_HOME}/my.cnf /etc/my.cnf
	bash ${COMMON_HOME}/set_auto_start.sh ${AUTO_RUN_FILE} ${AUTO_RUN_FILE_NAME}

	# Update root passwd
	CMD_MYSQL="${MYSQL_HOME}/bin/mysql -uroot -S ${MYSQL_HOME}/var/mysql.sock"
	$CMD_MYSQL -e "use mysql; delete from mysql.user where user='';"
	$CMD_MYSQL -e "use mysql; update user set password=password('${db_password}') where user='root'"
	$CMD_MYSQL -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '${db_password}'";
	$CMD_MYSQL -e "flush privileges;";
	
	echo "安装mysql数据库完成"
	#标记mysql数据库为已安装
	export MYSQLDB_HAS_INSTALL=y
fi
