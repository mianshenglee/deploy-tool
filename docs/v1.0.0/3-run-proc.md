# 3. 部署工具运行流程
## 3.1 部署工具从制作到使用
先由产品发布人员对部署工具及环境进行制作，然后发布，由项目实施人员进行部署。具体如下图所示：
![部署工具制作与使用][4]

- 确定部署结构：包括安装此产品应包含哪些模块及安装环境，确定它们存放的位置。
- 提供部署环境：为安装此产品所需要的环境包，如jdk，tomcat等。
- 制作安装脚本：使用shell(linux)或bat(windows)进行安装部署脚本编写。全部模块及环境的安装均依赖此安装脚本，因此，产品发布人员主要工作是保证这些安装脚本的正常运行。
- 发布部署工具及环境：产品发布人员先自行测试，完成后发布此部署工具及环境，以提供项目实施人员使用。
- 下载部署工具及环境：项目实施人员在项目实施时，先规划好产品安装的服务器划分（如web服务器，后端服务器等）。
- 下载程序及配置信息：下载程序包，根据实际服务器信息填写配置信息。
- 选择模块部署：根据实际情况选择需要安装的模块进行部署。

## 3.2 部署工具目录结构
部署工具运行有其相对固定的目录结构（可自定义但不建议），建议使用时目录结构按默认结构即可。结构如下：
![部署包结构][5]

说明：
> * **部署包**是完整一套产品部署的环境+程序，它可能会包含多个产品，多个产品会共用某些环境（如数据库）。如图上所示，可以是产品1+产品2+DB。当然，若只有一个产品，则此产品作为独立部署包即可。
> * 产品可能包含多个**模块**，建议这些模块可以按服务器划分，然后再根据依赖软件或环境（software）及程序(program)划分。如前后端分离部署实例，分为前台服务器frontend及后台服务器backend，然后分别设置所需软件及程序目录。当然用户也可以自定义存放结构，只要部署工具及相应的部署脚本可找到这些目录即可。
> * `install`目录，此目录就是部署工具的完整目录，此目录名称用户不可修改，按已有的结构进行文件存放即可。
> * `config`目录，目录名称为"config"，不能修改。此目录存放部署工具运行所需要的流程配置文件、统一配置文件、共用配置文件等，部署工具的启动会先读取此目录的配置文件，再根据配置文件定义的结构运行。
> * `scripts`目录，存放各程序的安装脚本，建议按程序模块划分目录。
> * `systemfile`目录，存放部署工具使用的第三方工具。
> * `template`目录，存放各程序或环境的配置文件模板，动态配置部分使用占位符（$${}）替换。
> * `deploy-tool.jar`，部署工具程序包，需运行它来启动，它依赖JRE8运行环境。
> * `run-deploy-tool.sh`/`run-deploy-tool.bat`，运行部署工具脚本，注意:**运行部署工具依赖JRE8运行环境，因此建议在`共用环境`中存放绿色版本jre8或jdk8目录，并在此脚本中指定路径**。

## 3.3 运行流程
部署工具的运行方式有两种，一种是以交互式界面运行，一种是使用命令自动运行。在执行`deploy-tool.jar`时，通过是否添加参数决定使用哪一种运行方式。前者不加参数，后者添加执行操作的ID。如：

- 直接执行`run-deploy-tool.sh`或`run-deploy-tool.bat`，出现用户选择操作界面。它的执行命令是`java -jar deploy-tool.jar`。
- 通过添加参数直接执行某操作，如`java -jar /opt/bingodrive/install/deploy-tool.jar installRedis` 表示执行安装Redis服务。

部署工具启动需要加载相应的配置文件，启动流程如下所示：
![启动加载流程][6]

有以下几点需要注意
> * 读取属性配置文件：部署工具会读取`install/config`下全部文件，其中必须包含两个文件：`global_config.properties`及`custom_config.properties`。详细介绍见章节`配置文件概述`
> * 加载属性信息：部署工具会把读取到的全部properties文件的内容加载到内存，因此，各文件中不能有重复的key，若重复，则会覆盖。
> * 读取流程配置文件：流程配置文件为`{deploy_product}-{system}.xml`，如在linux系统部署db，则xml文件名为`db-linux.xml`。其中`deploy_product`在`global_config.properties`中定义。

## 3.4 配置文件概述
配置文件存放目录为`install/config`，其中需要运行前加载的属性文件在此目录下，流程配置文件xml及xsd则存放在`install/config/deploy-config`目录。在`install/config`下的properties文件会全部加载，因此，此目录下只能存放properties文件。其中`global_config.properties`及`custom_config.properties`必须存在。

### 3.4.1 全局属性配置文件global_config
全局属性配置文件`global_config.properties`主要设置部署前本产品需要指定的内容，当前只设置产品的类型（`deploy_product`），此类型主要用于部署工具寻找相应的流程配置文件（xml），寻找规则为：在`install/config/deploy-config`目录下寻找`{deploy_product}-{system}.xml`。

如在linux系统部署db，则在`global_config.properties`中设置`deploy_product=db`，则部署工具寻找流程配置文件xml文件路径为`install/config/deploy-config/db-linux.xml`。

产品类型可自定义，只要deploy_product与相应的xml名称对应上即可。

### 3.4.2 用户属性配置文件custom_config
用户属性配置文件`custom_config.properties`是产品配置的主要内容，此文件应由产品发布人员经过整理，合并，简化所有产品模块的配置项而形成的。后面所有需要替换的配置内容，都从此文件中读取并替换。

如假设产品包含模块1和模块2，它们都需要配置数据库连接信息，而且连接信息是一样的，则只需要在此文件中配置一次，然后在部署工具的流程配置文件中对模块1和模块2的配置文件进行替换即可。如下示例：

```
#数据库IP
server_mysql_ip = 127.0.0.1
#数据库端口
server_mysql_port = 3306
#数据库名
server_mysql_dbname = mydb
#数据库用户名
server_mysql_db_username = root
#数据库密码
server_mysql_db_password = 111111
```

### 3.4.3 其它属性配置文件
若需要对产品添加其它特殊配置，可在`install/config`目录下新的properties文件，部署工具也会把它一并读到内存，然后在配置替换的时候进行使用。

### 3.4.4 流程配置文件
流程配置文件是部署工具的核心，存放在`install/config/deploy-config`目录，它包含xml及xsd，前者是部署工具的流程配置，xsd是XML Schema文件，用于描述及规范xml的文件结构，**一般不修改此文件**。

产品发布人员需要编写及设计的是xml文件。它是部署工具运行的依赖，产品发布人员通过此文件定义部署的结构，模块，操作，显示。文件名为`{deploy_product}-{system}.xml`。其中deploy_product在`global_config.properties`，system由部署工具根据实际运行的环境自动识别。

流程配置文件的详细说明见章节`部署工具使用详解`

### 3.4.5 占位符
部署工具从属性配置文件加载配置内容后，在配置文件替换操作、流程配置文件中均可使用占位符。占位符格式为`$${key}`，其中`key`是属性配置文件中的key或在流程配置文件中property元素中加载的key。如属性`server_mysql_ip = 127.0.0.1`，在使用占位符`$${server_mysql_ip}`的地方会补替换为`127.0.0.1`。

> * 部署工具启动后，会自动添加一个`key`为`deployment_home`，值为`deploy-tool.jar`所在目录(`install/config`)的上一级目录。如在C盘部署test，test目录下是install目录，则`$${deployment_home}`值为`c:\test`。用户可使用此key定位部署目录。
> * 占位符若在流程配置文件（xml）中使用，在元素的值中建议使用`<![CDATA[]]`进行转义。如`<![CDATA[jdbc:mysql://$${server_mysql_ip}:$${server_mysql_port}/$${server_mysql_dbname}?autoReconnect=true]]`




  [1]: http://ww1.sinaimg.cn/large/72d660a7gw1fblvz0xetxj20dv0e3gm1.jpg
  [2]: http://ww1.sinaimg.cn/large/72d660a7gw1fblxc4tgfvj20gh0dv3z5.jpg
  [3]: http://static.zybuluo.com/miansheng/4w773aw8b3hd2pkqrex51blt/TIM%E6%88%AA%E5%9B%BE20181219172902.png
  [4]: http://ww4.sinaimg.cn/large/72d660a7gw1fbmver90ecj20dl0cyjru.jpg
  [5]: http://static.zybuluo.com/miansheng/m5wrzg08ucraqdv5w5xnxut3/TIM%E6%88%AA%E5%9B%BE20181219174105.png
  [6]: http://ww2.sinaimg.cn/large/72d660a7gw1fbmx1uhft0j20fk0g60t4.jpg
  [7]: http://ww4.sinaimg.cn/large/72d660a7gw1fbn2sbcxpaj20fi03e749.jpg
  [8]: http://ww1.sinaimg.cn/large/72d660a7gw1fbn38984b0j20nj0f6dgg.jpg
  [9]: http://static.zybuluo.com/miansheng/4w6yglcq3yvubaay1d24tvjk/property%E5%85%83%E7%B4%A0.jpg
  [10]: http://ww3.sinaimg.cn/large/72d660a7gw1fbn5c11xi0j20cw057t8o.jpg
  [11]: http://ww1.sinaimg.cn/large/72d660a7gw1fbn5pmnun2j209s0b0mxe.jpg
  [12]: http://ww3.sinaimg.cn/large/72d660a7gw1fbn6wc446xj209104t3yj.jpg
  [13]: http://ww4.sinaimg.cn/large/72d660a7gw1fbn6wqvdtlj20gy07faa8.jpg
  [14]: http://ww2.sinaimg.cn/large/72d660a7gw1fbn6x1paruj20c806074a.jpg
  [15]: http://ww1.sinaimg.cn/large/72d660a7gw1fbn8ivn9oyj20ev053q2x.jpg
  [16]: http://ww1.sinaimg.cn/large/72d660a7gw1fbn8j79ddkj20or0d5wf1.jpg
  [17]: http://ww2.sinaimg.cn/large/72d660a7gw1fbn8jk0ye0j20bf05sq30.jpg
  [18]: http://ww2.sinaimg.cn/large/72d660a7gw1fbn8jw9j09j205q01y3yb.jpg
  [19]: http://static.zybuluo.com/miansheng/kn284paa1wxeupk2xdzp4b9i/mariadb-install.png
  [20]: http://static.zybuluo.com/miansheng/819dok04g21zcuv8autzcb6b/base.png
  [21]: http://static.zybuluo.com/miansheng/oyvszacmgyuxazkk7p80w56w/mysql.png
  [22]: http://static.zybuluo.com/miansheng/1k94mxfgurvsxthirvg1lyv7/dbstructure.png
  [23]: https://pan.baidu.com/s/1Da5fCkbwZpckiQVfOrBwhQ
  [24]: https://pan.baidu.com/s/1lvSRXa0zN7Hpy_vZFhyubA