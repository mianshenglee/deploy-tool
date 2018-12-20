#!/bin/bash

ports=($*)

#安装 iptables
yum -y install iptables iptables-services


#添加端口到防火墙
for port in ${ports[@]}
do
iptables -D INPUT -p tcp -m state --state NEW -m tcp --dport ${port} -j ACCEPT
echo "................... 删除端口： ${port}"
done

#保存防火墙
service iptables save
service iptables restart
