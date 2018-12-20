/**
 * Copyright © 2018 mason (mianshenglee@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mason.deploytool.util;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * telnet操作
 *
 * @author mason
 */
public class TelnetUtils {
    private static Logger logger = LoggerFactory.getLogger(TelnetUtils.class);
    /**
     * [0]ip,[1]port,[2]interval,[3]timeout
     *
     * @param args
     */
    public static void main(String[] args) {
        //若无参数，返回
        if (args == null || args.length == 0) {
            logger.error("TelnetUtil参数不能为空");
            return;
        } else if (args.length != 4) {
            logger.error("TelnetUtil需设置4个参数");
            return;
        } else {
            TelnetUtils.waitTelnetPass(args[0], Integer.parseInt(args[1]), Long.parseLong(args[2]), Long.parseLong(args[3]));
        }
    }

    /**
     * 使用telnet连接，按间隔进行连接测试，直到超时
     *
     * @param ip       ip地址
     * @param port     端口
     * @param interval 时间间隔
     * @param timeout  超时时间
     * @return
     */
    public static boolean waitTelnetPass(String ip, Integer port, long interval, long timeout) {
        /** 新建一个TelnetClient对象 */
        TelnetClient telnetClient = new TelnetClient();
        long timeCount = 0L;
        boolean isTelnetPass = false;
        // 若telnet不成功，在超时时间内以一定间隔进行telnet连接测试
        while (!isTelnetPass) {
            try {
                logger.debug("尝试telnet ip:" + ip + " port:" + port);
                telnetClient.connect(ip, port);
                isTelnetPass = true;
            } catch (IOException e) {
                isTelnetPass = false;
                // 按时间间隔暂停
                try {
                    Thread.sleep(interval);
                    // 时间累加
                    timeCount += interval;
                    // 若时间超时，则退出测试
                    if (timeCount >= timeout) {
                        logger.error("telnet ip:" + ip + " port:" + port + "不成功!");
                        break;
                    }
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }

        if (isTelnetPass) {
            logger.debug("telnet ip:" + ip + " port:" + port + " 成功。");
        }
        try {
            telnetClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isTelnetPass;
    }
}
