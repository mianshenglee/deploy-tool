# 2. 功能概览
## 2.1 部署工具面向的人员
使用部署工具，主要是固化原来手工部署的流程，并提供简化、统一的配置项，分产品、环境、模块进行自动部署，面向的对象主要包括：

- 产品发布人员：管理产品版本，管理产品分发，根据情况修改部署工具环境、流程、配置项。
- 产品部署人员：内部产品部署、测试。
- 项目实施人员：项目实施部署。

## 2.2 部署工具功能
部署工具运行时（交互式界面运行），以linux下安装mysql和redis为例，如下：
![部署工具运行界面][3]

项目实施人员只需根据实际情况选择需要安装的模块即可。

部署工具主要有以下功能点：

- 固化部署流程：各个产品部署流程都不一样，部署什么环境、产品包含什么模块，各模块安装顺序如何，均在在部署工具中进行设置、固化，以便部署实施人员使用。
- 提供简化、统一配置项：在一套产品中，需要配置的项可以集中在统一、简化的配置文件，产品中各模块需要修改的配置均可在配置此文件中进行读取，替换即可。
- 分产品、环境、模块进行操作：部署需要安装的环境、产品模块均可自定义，并在部署的命令行界面中显示及运行。
- 支持windows及linux下运行部署。
- 提供shell/bat执行功能：可自定义部署脚本，并在部署工具中运行。
- 提供数据库脚本执行功能：包括数据CURD操作。
- 提供配置文件替换功能：可按模板替换/按xml局部查找替换/properties文件key匹配替换。
- 提供生成二维码功能：可根据参数生成二维码。
- 提供动态配置项功能：某些配置需要根据已的配置计算或变换得出。
- 查看统一配置文件内容。




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