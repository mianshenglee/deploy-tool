# 5. 完整db(mariadb及redis)部署示例
## 5.1 mariadb及redis部署结构分析
以linux部署为例，db分为软件安装包和资源，软件安装包如mariadb及redis的二进制绿色安装包，资源则是数据库的初始化脚本。

### 5.1.1 模块划分
db分为mariadb安装卸载及redis安装卸载，可一起部署，也可分别部署在不同的服务器。两个安装包均是绿色免安装版。安装数据库时需要初始化sql脚本，可把这些额外文件作为资源文件，独立一个文件夹。

另外部署工具及程序运行需要使用jre8，可以把这些共用的环境作为软件统一存放到固定位置。

基于上述分析，db部署结构划分三部分,install(部署工具相关),resource(资源相关)，software（软件包），如下所示：
![db结构][22]

### 5.1.2 部署环境包制作
由产品发布人员对部署环境包进行制作，按前面的规划，制作流程是：
(1). 创建好相应的目录结构（如`resource/software/install`）
(2). 存放公共使用的环境到`software`目录。如存放`jre8`。
(3). 存放各模块软件包到`software`，如存放mariadb及redis软件包。
(4). 存放部署工具(`deploy-tool.jar`)到`install`目录，并提供运行部署工具的脚本，如`run-deploy-tool.sh`。
(5). 在`install/config`目录下添加`global_config.properties`配置，指定产品名称,如`db`。同时需要确定各程序需要修改的配置统一到`custom_config.properties`中，并存放在此目录。若有其它默认配置，也可在此目录下进行添加。
(6). 创建`install/config/deploy-config`，添加流程配置文件`{deploy_product}-{system}.xml`，根据产品名称及部署系统填写。如当前示例为`db-linux.xml`。
(7). 按实际部署流程设计及编写流程配置文件。
(8). 在`install/scripts`目录下，添加部署需要使用的各种脚本，由于支持多种系统，建议在`install/scripts`目录下按系统类型建立文件夹，如当前是linux的，应新建`install/scripts/linux`文件夹，并把相应的sh脚本按模块存放在此目录下。

通过上面的处理，部署环境包就已完成。环境包完成后，产品发布人员需要先自己经过验证。因此，下面是验证过程：

- 下载对应版本的程序或软件包（可从开发/运维人员、jenkins或打包平台中获取）。把程序放到相应的目录中，如mariadb的软件存放在`software`目录下。

- 编辑`custom_config.properties`文件，按实际情况修改相应的配置信息，以便在占位符替换时使用实际的配置。

- 至此，部署工具+程序包已完整，可以验证部署，在linux下，修改`run-deploy-tool.sh`执行权限(chmod)，然后运行，在交互界面中选择进行安装即可。

验证通过后，部署环境即可发布，以供项目实施人员使用。在发布前，建议先程序去除，这样规范项目实施人员在部署时，需先下载程序包，根据实际情况修改`custom_config.properties`文件，然后才能正常启动部署。

### 5.1.3 项目实施人员使用流程
(1). 下载部署环境包
(2). 下载对应版本程序包
(3). 根据实际情况修改`custom_config.properties`文件。（若是从打包平台下载，此步骤可省略）。
(4). 运行`run-deploy-tool.sh`或`run-deploy-tool.bat`，在交互界面中选择进行安装即可。

## 5.2 db部署包示例及脚本

### 5.2.1 linux部署

- mariadb及redis安装linux部署示例，链接地址：[linux部署db示例][23]
 提取码：`l8qu`
 
- 压缩包中有使用帮助文档`help.txt`，按里面说明操作即可。
 
### 5.2.2 windows部署

- mariadb及redis安装windows部署示例，链接地址：[windows部署db示例][24]
 提取码：`kohq`
 
- 压缩包中有使用帮助文档`help.txt`，按里面说明操作即可。

## 5.3 部署环境升级
由于已使用模块化的方式进行安装部署，部分环境的升级，产品发布人员可直接修改相应的目录及脚本，验证通过后进行发布，并说明此部署环境的更新时间，对应的程序版本。



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