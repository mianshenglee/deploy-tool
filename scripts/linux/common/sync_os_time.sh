#!/bin/bash

base_dir=$(dirname $(pwd))

repos_file="/etc/yum.repos.d/CentOS6-Base-163.repo"
#若文件不存在
if [ ! -f "${repos_file}" ];then
	#备份yum源
	mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
	
	#复制163的yum源到/etc/yum.repos.d
	\cp -f ${base_dir}/install/scripts/linux/common/yum/CentOS6-Base-163.repo /etc/yum.repos.d
	
	yum clean all
	yum makecache
fi



#测试能否连接网络
ping -c 1 ntp1.aliyun.com &>/dev/null

if [ "$?" -eq 0 ];then
	#安装ntpdate
   yum install -y ntpdate
   ntpdate ntp1.aliyun.com
   
   #安装crontabs
   rpm -qa | grep crontabs &>/dev/null
   if [ "$?" -ne 0 ];then
	   yum install -y crontabs
	   service crond start
	   chkconfig crond on
   fi
 
   #同步时间
   grep ntpdate /var/spool/cron/root  &>/dev/null
   if [ "$?" -ne 0 ];then
     ntpdate ntp1.aliyun.com
     #定时同步时间
     echo "30 * * * * ntpdate ntp1.aliyun.com" >> /var/spool/cron/root
   fi
fi
