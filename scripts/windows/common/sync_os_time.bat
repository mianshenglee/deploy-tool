@echo off
rem 已设置过系统时间同步间隔则跳过
if "%OS_TIME_HAS_SYNC%" == "y" goto gotoEnd

echo ----〉准备调整系统时间同步间隔

set REGISTRY_HOME=%~dp0\registry
rem 同步操作系统时间
reg import %REGISTRY_HOME%\sync_os_time.reg

rem 停止基础云服务
echo 准备停止基础云服务（在非公有云环境下，下一行会提示“服务名无效”，此为正常现象）
net stop Ec2Config
ping -n 3 127.0.0.1 >nul

rem 停止系统时间同步服务
echo 准备停止系统时间同步服务
net stop W32Time
ping -n 3 127.0.0.1 >nul

rem 启动系统时间同步服务
echo 准备启动系统时间同步服务
net start W32Time
ping -n 3 127.0.0.1 >nul

rem 启动基础云服务
echo 准备启动基础云服务（在非公有云环境下，下一行会提示“服务名无效”，此为正常现象）
net start Ec2Config

rem 标记系统时间同步间隔为已设置
set OS_TIME_HAS_SYNC=y

echo ----〉系统时间同步间隔调整完毕

:gotoEnd