<h2 align="center">deploy-tool</h2>

<p align="center">
  deploy tool for products and software installation
</p>

<p align="center">
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

![Total visitor](https://visitor-count-badge.herokuapp.com/total.svg?repo_id=mianshenglee.deploy-tool)
![Visitors in today](https://visitor-count-badge.herokuapp.com/today.svg?repo_id=mianshenglee.deploy-tool)

---

## 部署工具介绍

对于产品部署，特别是项目型的产品在客户环境私有化部署，若是手动部署，需要自己打包产品，配置程序，安装环境，相对来讲部署过程复杂，配置文件繁琐，配置容易出错。部署工具的主要目的是固化原来手工部署的流程，并提供简化、统一的配置项，分产品、环境、模块进行自动部署。本部署工具主要用于帮助产品实施人员更快，更好，更有条理部署应用产品。

## 面向用户

使用部署工具，主要是固化原来手工部署的流程，并提供简化、统一的配置项，分产品、环境、模块进行自动部署，面向的对象主要包括：

- 产品发布人员：管理产品版本，管理产品分发，根据情况修改部署工具环境、流程、配置项。
- 产品部署人员：内部产品部署、测试。
- 项目实施人员：项目实施部署。

## 功能特性

部署工具主要有以下功能：

- 固化部署流程：各个产品部署流程都不一样，部署什么环境、产品包含什么模块，各模块安装顺序如何，均在在部署工具中进行设置、固化，以便部署实施人员使用。
- 提供简化、统一配置项：在一套产品中，需要配置的项可以集中在统一、简化的配置文件，产品中各模块需要修改的配置均可在配置此文件中进行读取，替换即可。
- 分产品、环境、模块进行操作：部署需要安装的环境、产品模块均可自定义，并在部署的命令行界面中显示及运行。
- 支持windows及linux下运行部署。
- 提供shell/bat执行功能：可自定义部署脚本，并在部署工具中运行。
- 提供数据库脚本执行功能：包括数据CURD操作。
- 提供ssl证书生成功能：可对https需要的证书进行自签操作。
- 提供配置文件替换功能：可按模板替换/按xml局部查找替换/properties文件key匹配替换。
- 提供生成二维码功能：可根据参数生成二维码。
- 提供动态配置项功能：某些配置需要根据已的配置计算或变换得出。
- 查看统一配置文件内容。

## 在线文档

-   [`github`在线帮助文档](https://mianshenglee.github.io/deploy-tool/#/)
-   [`看云`在线帮助文档](https://www.kancloud.cn/masonlee/deploy-tool-help-doc/883192)
-   [linux使用示例](https://pan.baidu.com/s/1Da5fCkbwZpckiQVfOrBwhQ)    提取码：`l8qu`，压缩包中有使用帮助文档`readme.txt`，按里面说明操作即可。
-   [windows使用示例](https://pan.baidu.com/s/1lvSRXa0zN7Hpy_vZFhyubA)  提取码：`kohq`，压缩包中有使用帮助文档`readme.txt`，按里面说明操作即可。

## 快速使用

-   下载代码到本地
-   使用idea或eclipse引入工程，使用`mvn clean package`进行打包
-   打包出来的deploy-tool.jar即为部署工具
-   具体使用示例请见`在线文档`及相关示例说明

## 问题及反馈

	使用过程中问题可发邮件到mianshenglee@foxmail.com

## License

 deploy-tool 使用 Apache 2.0 license. 具体请查看 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) 。