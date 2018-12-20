#!/bin/bash

base_dir=$(dirname $(pwd))

#根据目录处理需要chmod的文件
chmod_files=(
"${base_dir}/install/scripts/linux/common/*"
"${base_dir}/software/jre8/bin/*"
"${base_dir}/software/mariadb/bin/*"
"${base_dir}/software/mariadb/lib/*"
"${base_dir}/software/mariadb/support-files/mysql*"
"${base_dir}/software/mariadb/support-files/binary-configure"
"${base_dir}/software/mariadb/support-files/wsrep_notify"
"${base_dir}/software/mariadb/scripts/*"
"${base_dir}/software/mariadb/sql-bench/*"
"${base_dir}/software/redis/bin/*"
"${base_dir}/software/redis/runtest*"
)

#循环设置权限
for file in ${chmod_files[@]}
do
	chmod +x ${file}
	echo "修改文件可执行权限：${file}"
done