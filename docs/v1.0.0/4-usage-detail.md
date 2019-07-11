# 4. 部署工具使用详解
要使用部署工具，需要两个重要步骤：编写流程配置文件，编写部署脚本。以下对配置文件的编写及部署脚本需要注意的事项进行说明。

## 4.1 流程配置文件简单示例
流程配置文件`{deploy_product}-{system}.xml`允许用户对需要部署的模块进行设计、划分，对安装流程，脚本执行，功能设置进行设定。用户需按照配置文件约定的规则进行编写即可。先看以下示例，此示例功能是对tomcat进行安装与卸载。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<deployment xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="deploy-config-schema.xsd">
	<executions>
		<group name="web服务安装与卸载">
			<execution name="安装Tomcat" id="installWebTomcat" display="true" class-name="deploy.OperRunCommand">
				<configuration>
					<commands>
						<command>
							<exec><![CDATA[scripts/windows/disk/webapp/install_tomcat.bat]]></exec>
							<args>
								<arg><![CDATA[$${web_extranet_port}]]></arg>
							</args>
						</command>
					</commands>
				</configuration>
			</execution>
			<execution name="卸载Tomcat" id="uninstallWebTomcat" display="true" class-name="deploy.OperRunCommand">
				<configuration>
					<commands>
						<command>
							<exec><![CDATA[scripts/windows/disk/webapp/uninstall_tomcat.bat]]></exec>
						</command>
					</commands>
				</configuration>
			</execution>
		</group>
	</executions>
</deployment>
```

由上述配置文件内容片断可以看到xml配置文件的结构与元素。此文件通过设置操作名称/ID等信息，指定安装tomcat需要运行的脚本(`install_tomcat.bat`,`uninstall_tomcat.bat`)。这样，在运行部署工具时，即可显示组名称为*web服务安装与卸载*，其下有两个操作，分别是*安装tomcat*及*卸载tomcat*,如下所示：
![tomcat安装简单示例][7]

下面详细说明xml配置文件的结构规则。

## 4.2 流程配置文件结构
流程配置文件用于配置部署流程，使用xml文件对部署需要做的事情进行描写，各元素功能及使用说明如下：

### 4.2.1 首行及根元素

第一行设置它的版本及编码
```xml
<?xml version="1.0" encoding="UTF-8"?>
```
它需以deployment为根元素，并使用xml schema文件，以此规范xml的格式正确。

```xml
<deployment xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="deploy-config-schema.xsd">
</deployment>
```

### 4.2.2 xml文件结构
配置文件结构如下图所示：
![xml结构][8]

*上图中，虚线表示可选，实线为必需，attributes为属性,方框为元素*

> * 由上图可见，xml主要包括两大元素，分别是`properties`及`executions`元素。如全部配置项已在custom_config.properties文件中设置，`properties`元素可不设置。`executions`是执行器，所有执行流程在此元素下设置。

> * `properties`，可选，下有1或多个`property`。

> * `executions`下有1个或多个`goup`元素，`group`元素下可有1个或多个`execution`。每一个`group`表示一组相关操作，每一个execution表示一个执行操作。

> * `execution`下可以有`name/id/display/class-name/configuration/dependencies/sub-execution`等元素对执行流程进行设置。

下面详细说明各元素的设置及作用。

### 4.2.3 properties/property元素

![property元素][9]

property元素主要用于动态添加需要计算或变换得出的属性值，可使用占位符组合成新的property属性，也可通过`function`对参数值进行处理。以key-value值保存到properties文件对应的属性内存中，以便后续使用。

如下是添加tomcat_webapps_home属性(使用占位符`$${deployment_home}`与其它目录路径组合得出）：
```xml
<properties>
    <property key="tomcat_webapps_home">
        <value><![CDATA[$${deployment_home}/bingodrive_web/program/webapps]]>
        </value>
    </property>
</properties>
```
说明：
> * `properties`元素是可选的，若没有需要可不配置。
> * `properties`元素下须有>=1个`property`元素。
> * `property`元素是一个key-value值，均需要配置。其中key可为属性，也可为元素，写其一即可。value为元素。
> * 若是使用function，则需要设置`function`元素及相应的参数`args`及`arg`元素。
> * 此处配置的property元素的force属性，若force为false，则若在前面读取的配置文件（properties文件）中没有此配置，则添加，已有则忽略此配置。若force为true，则若在前面读取的配置文件（properties文件）中没有此配置，则添加，已有强制覆盖替换成此处配置。

### 4.2.4 executions/group元素
![executions元素][10]

`executions`元素及`group`元素主要用于包含需要配置的操作，对操作进行分类。如产品部署服务器划分、产品模块划分，均可使用group进行分类。

说明：
> * `executions`元素是必需的，此元素下有>=1个`group`元素。
> * `group`元素主要是对产品服务器或产品模块划分，`name`可以是属性，也是可以是元素，写其一即可，以便显示。如上面安装tomcat示例中，*web服务安装与卸载*即为组名，显示时，部署工具会自动根据`group`的数量及位置添加序号。如第1个`group`，显示为**"1-web服务安装与卸载"**
> * `group`元素下有>=1个`execution`元素，`execution`元素是对操作的配置，部署工具就是根据execution的配置进行操作的。

### 4.2.5 execution元素
![execution元素][11]

`execution`元素是需要执行的每一个操作，它通过设置对应的操作类，结合操作配置信息(`configuration`)，依赖(`dependencies`)，子操作(`sub-execution`)，组合成为一个完整的操作，以实现安装部署功能。如上面安装tomcat的示例中，使用`OperRunCommand`操作，并指定要执行的脚本路径或参数，即可完成tomcat安装。
```xml
<execution name="安装Tomcat" id="installWebTomcat" display="true" class-name="deploy.OperRunCommand">
    <configuration>
        <commands>
            <command>
                <exec><![CDATA[scripts/windows/disk/webapp/install_tomcat.bat]]></exec>
                <args>
                    <arg><![CDATA[$${web_extranet_port}]]></arg>
                </args>
            </command>
        </commands>
    </configuration>
</execution>
```

说明：
> * `execution`元素在显示时，会根据所在的`group`序号及当前`execution`在`group`中的位置自动添加序号。如`group`的序号是1，当前`execution`的位置是1，则它显示的序号是"1.1"，后续的`execution`依次递增(1.2/1.3/1.4/...)。
> * `name`,必填，可为属性或元素，写其一即可，根据实际操作命名。
> * `id`，必填，可为属性或元素，写其一即可，此ID全局唯一，即其它execution的ID不能与之重复。此ID命名规则与java变量命名规则一致($ 、字母、下划线开头都行，后面的可以是数字、字母、下划线)。此ID在运行部署工具时，可作为运行参数输入，这样就不会出现交互界面，相当于直接执行此操作。
> * `display`，必填，可为属性或元素，写其一即可，可选值为"true"或"false"。若为"true"，则在交互界面中会显示出来，供用户选择。若为"false"，则不在交互界面显示，用户也无法选择。
> * `class-name`，必填，可为属性或元素，写其一即可。此值填写部署工具提供的操作类，每种操作类均有其相应的功能。当前共有7种操作，包括`OperRunCommand`运行命令操作,`OperGenerateLicense`生成许可证,`OperGenerateQrcode`生成二维码操作,`OperListConfig`显示当前属性配置操作,`OperRunDbStatement`运行sql语句操作,`OperRunDependency`只运行依赖的操作,`OperUpdateFile`更新配置文件操作。在填写`class-name`时，需要填写统一前缀`deploy.`，如`deploy.OperRunCommand`
> * `configuration`元素，针对每种操作，有其相应的配置内容，请根据`class-name`对应的操作进行设置，详细见`configuration`元素的章节说明。
> * `dependencies`元素，若当前`execution`操作是需要依赖其它操作，即在执行本`execution`时，会先执行`dependencies`中设置的依赖操作，待依赖操作完成后再执行本操作。详细见`dependencies`元素的说明。
> * `sub-execution`元素，若此操作可分解为多个子操作，可在这里进行设置。同样，部署工具会自动添加序号，规则与`execution`的序号一致。如假设当前`execution`是2.1，`sub-execution`在中的有2个`execution`，则它们的序号分别是2.1.1及2.1.2。详细见`sub-execution`说明。

### 4.2.6 configuration元素
![configuration元素][12]

`configuration`元素是针对具体的每种操作，需要设置相应的配置信息，以完成此操作完成的功能。如上面的安装tomcat的示例中，是实现命令执行操作的配置信息。根据当前部署工具已经提供的操作类，主要配置内容如下。

- `commands`元素，适用于`OperRunCommand`运行命令操作。此操作可以运行脚本文件，执行单个shell语句，bat语句等。它使用`commands`元素的配置。其它可不配置。
- `datasource`元素及`statements`元素，适用于`OperRunDbStatement`运行sql语句操作,此操作可对数据库执行指定的sql语句，它使用`datasource`元素及`statements`元素，分别设置数据库连接信息，需要执行的sql语句。
- `OperRunDependency`只运行依赖的操作,此操作不需要使用`configuration`元素，只需要使用`dependencies`元素即可。因此可不配置。
- `replace-files`元素，适用于`OperUpdateFile`更新配置文件操作，此操作只使用`replace-files`元素。其它可不配置。
- `OperGenerateQrcode`生成二维码操作,此操作使用`args`元素，输入相应的参数（如二维码内容，图片宽度、图片高度，输出路径），即可生成二维码图片。
- `OperListConfig`显示当前属性配置操作,此操作不需要配置。


### 4.2.7 dependencies元素
![dependencies元素][13]

`dependencies`元素主要设置当前`execution`元素需要依赖的操作，若设置了此依赖，在运行`execution`前会先按顺序执行依赖项，完成后再执行当前的`execution`。它主要在`OperRunDependency`操作及`OperRunCommand`操作中设置。如下所示是设置安装redis前，需要先更新配置文件、变更文件权限：
```xml
<execution name="安装Redis" id="installRedis" display="true" class-name="deploy.OperRunCommand">
				<configuration>
					<commands>
						<command charset="utf-8">
							<exec><![CDATA[scripts/linux/db/redis/install_redis.sh]]></exec>
						</command>
						<!-- 开通端口 -->
						<command charset="utf-8">
							<exec><![CDATA[scripts/linux/common/add_port.sh]]></exec>
							<args>
								<arg><![CDATA[$${server.redis.port}]]></arg>
							</args>
						</command>
					</commands>
				</configuration>
				<dependencies>
					<dependency ref-id="updateConfigFiles"/>
					<dependency ref-id="chmodFile"/>
				</dependencies>
			</execution>
```

说明：
> * `dependencies`元素下有>=1个`dependency`元素。
> * `dependency`元素使用`ref-id`表示依赖的`execution`，`ref-id`值为要执行的`execution`的id。`ref-id`可为属性或元素，写其一即可。
> * `dependency`元素可使用`condiction`属性设置条件，对符合此条件的才执行此`ref-id`的操作，不符合条件则不操作。如使用`condiction="$${server_web_extranet_protocol}==https"`，表示当用户使用https部署，设置`server_web_extranet_protocol`的值为https时符合条，若不等于https，则不符合，跳过此依赖。
> * `dependency`元素下可使用`result`元素设置此依赖的操作执行后，根据它的执行结果如何进行下一步。若不设置此元素，则采用默认操作。即
`<result skip="false">
<success>move</success>
<fail>stop</fail>
</result>`
即当此依赖执行成功后，才进行下一个依赖操作，失败后即停止，返回到交互界面。如果设置为`skip`为true，则不管此依赖是否执行成功，都会进行下一步操作。当`skip`为true时，`success`及`fail`元素不用设置。

### 4.2.8 sub-execution元素
![sub-execution元素][14]

`sub-execution`元素用于对当前execution的分解,若此操作可分解为多个子操作，可在这里进行设置。`sub-execution`元素下可有>=1个`execution`元素。`execution`元素的定义与前面介绍的`execution`元素一致。

部署工具会对子操作自动添加序号，规则与`execution`的序号一致。如假设当前`execution`是2.1，`sub-execution`在中的有2个`execution`，则它们的序号分别是2.1.1及2.1.2。在交互界面中，当用户选择2.1.1时，即会执行对应的子操作。

一般有子操作时，会把当前`execution`元素设置为`OperRunDependency`操作类型，然后使用`dependencies`元素，把此操作依赖它的`sub-execution`，这样，即可在用户选择2.1时，自动执行2.1.1和2.1.2。如下面示例，安装消息服务，就是"安装Erlang与RabbitMQ"和"安装MsgService"，因此把`installMessageService`设置为`OperRunDependency`，然后依赖这两个操作：
```xml
<execution name="安装消息服务" id="installMessageService" display="true" class-name="deploy.OperRunDependency">
	<dependencies>
		<dependency ref-id="installErlangAndRabbitMQ">
			<result skip="true" />
		</dependency>
		<dependency ref-id="installMsgService"/>
	</dependencies>
	<sub-execution>
		<execution name="安装Erlang与RabbitMQ" id="installErlangAndRabbitMQ" display="true" class-name="deploy.OperRunCommand">
			<configuration>
				<commands>
					<command>
						<exec><![CDATA[scripts/windows/test/message/install_message_erlang_mq.bat]]></exec>
					</command>
				</commands>
			</configuration>
		</execution>
		<execution name="安装MsgService" id="installMsgService" display="true" class-name="deploy.OperRunCommand">
			<configuration>
				<commands>
					<command>
						<exec><![CDATA[scripts/windows/test/message/install_msg_service.bat]]></exec>
						<args>
							<arg><![CDATA[$${server_msg_tcp_port}]]></arg>
						</args>
					</command>
				</commands>
			</configuration>
			<dependencies>
				<dependency ref-id="updateConfigFiles"/>
			</dependencies>
		</execution>
	<sub-execution>
</execution>
```

### 4.2.9 commands元素
![commands元素][15]

`commands`元素是在操作类型为`OperRunCommand`的时候使用，用于运行命令操作，即通过部署工具执行脚本文件、或执行shell语句，bat语句等。如当用户需要执行脚本文件，且给脚本传递参数，可按如下设置，此设置表示执行路径`scripts/windows/test/message/install_msg_service.bat`的文件，并传递占位符参数`$${server_msg_tcp_port}`，以供bat脚本使用：
```xml
<configuration>
	<commands>
		<command>
			<exec><![CDATA[scripts/windows/test/message/install_msg_service.bat]]></exec>
			<args>
				<arg><![CDATA[$${server_msg_tcp_port}]]></arg>
			</args>
		</command>
	</commands>
</configuration>
```

说明：
> * `commands`元素下有>=1个`command`元素，每个command对应一个运行命令，部署工具会依次执行。
> * `command`元素下包含`exec`及`args`元素，分别是要执行的命令及传递的参数。
> * `exec`元素是需要执行的命令，它可以是脚本文件，也可以是某一脚本命令。若是脚本文件，可写脚本文件的绝对路径或相对路径（相对install目录的路径）。若是脚本命令，则直接写命令代码即可。
> * `args`元素是传递给`exec`元素命令的参数。`args`下可有多个`arg`元素，一个`arg`对应一个参数。

### 4.2.10 replace-files元素
![replace-files元素][16]

`replace-files`元素主要用于设置文件替换，它包含>=1个`replace-file`元素，`replace-file`元素表示进行文件替换操作，并通过`file-type`属性指定替换方式，部署工具当前支持的文件替换方式有三种：

 (1) **使用模板文件替换**：`file-type`属性设置为`template`，此方式会对使用指定的模板文件，部署工具对模板文件进行占位符替换后，直接替换到指定的目录中。若指定目录已有同名文件，则会对同名文件添加`_backup`后缀进行备份。使用此方式，在`target`元素中需要设置模板文件位置`source`元素及替换目标文件位置`destination`。`target`元素可以设置多个，以进行多个文件的替换。
 (2) **对xml文件的查找替换**：对xml文件，可设置查找匹配相应的元素或属性，然后对匹配到的元素或属性进行替换操作。与模板替换方式不同，此操作是局部替换。首次替换时，会对要替换的文件进行添加"_backup"备份，若发现备份文件已有，则不会再备份。它需要设置`target`元素下的`file-path`元素及`replacement`元素。`file-path`元素指定需要替换的xml文件位置，`replacement`指定需要查找的方式及需要替换的值。`replacement`元素包含`find-type`,`find-key`,`replace-type`,`replace-attr-name`,`replace-value`元素，分别是:
 - `find-type`查找类型：支持`attribute`,`element`,`xpath`三种查找方式，即查找属性，查找元素，xpath查找
 - `find-key`查找值：若查找类型是`attribute`，则可以设置为`attribute=vlue`，如需要查找属性值为"name",值为"workDir"的元素，则设置为"name=workDir"即可。
 - `replace-type`替换类型：支持`attribute`,`element`两种，即替换属性值、替换元素值。
 - `replace-attr-name`替换属性的名称：找到元素后，若是替换属性值，则设置此属性名。如上面查找到"name",值为"workDir"的元素，但是要替换此元素的"value"属性，此处应设置为"value"。若是替换元素值，则不需要设置此元素。
 - `replace-value`替换值：找到元素后，确定要替换的属性或元素，则把属性或元素值设置为此值。
 (3) **对properties文件的匹配替换**：对properties文件，可设置查找匹配相应的key，然后替换此key的值。首次替换时，会对要替换的文件进行添加"_backup"备份，若发现备份文件已有，则不会再备份。它需要设置`target`元素下的`file-path`元素及`replacement`元素。`replacement`元素需设置`find-key`,`replace-value`元素：、
 - `find-key`查找值：若查找key为`server_msg_tcp_port`，直接设置此值即可。
 - `replace-value`替换值：找到key后，把此key的值设置为此值。

> * `target`元素都可添加`condiction`以限制执行此替换的条件，例如只有是https部署时，才会替换某文件，则在`target`元素中添加属性`condiction="$${server_web_extranet_protocol}==https"`，若占位符`$${server_web_extranet_protocol}`值不是https，则会跳过此target，不进行操作。
> * `replace-file`元素下所有元素的值建议都添加在`<![CDATA[]]>`中，以免出现特殊字符匹配失败的情况。

### 4.2.11 datasourse/statements元素
![datasource及statements元素][17]

`datasourse`/`statements`元素是用于设置与数据库操作相关的配置，主要在`OperRunDbStatement`操作中进行设置。`datasourse`元素设置数据库连接连接信息，与jdbc连接数据库内容一致，包括`driver-class-name`,`url`,`username`,`password`。如下示例使用配置文件中的占位符设置：
```xml
<datasource>
    <driver-class-name><![CDATA[com.mysql.jdbc.Driver]]></driver-class-name>
    <url><![CDATA[jdbc:mysql://$${server_mysql_ip}:$${server_mysql_port}/$${server_mysql_dbname}?useUnicode=true&characterEncoding=utf8]]></url>
    <username><![CDATA[$${server_mysql_db_username}]]></username>
	<password><![CDATA[$${server_mysql_db_password}]]></password>
</datasource>
```
`statements`元素是需要执行sql语句。它包含>=1个`statement`元素，每个`statement`元素对应一条sql语句，一般在部署中使用较多的是`insert`,`update`,`delete`等语句。如下所示：
```xml
<statements>
	<statement>
		<![CDATA[UPDATE `$${server_mysql_dbname}`.`sys_config` SET `value` = '$${deployment_home}/system_file/test/source'
		WHERE `name` = 'store.server.folder.source';]]>
	</statement>
	<statement>
		<![CDATA[UPDATE `$${server_mysql_dbname}`.`sys_config` SET `value` = '$${deployment_home}/system_file/test/temp'
		WHERE `name` = 'store.server.folder.temp';]]>
	</statement>
</statements>
```

注意：
> * 建议把在运行部署工具时才确定的变量，同时需要应用到数据库中的时候才放在此处执行。如上述示例中，根据当前部署路径更新到数据库表中。
> * 不建议把大量的sql语句在此执行，若需要执行大量sql语句，可写在sql脚本中，然后通过`OperRunCommand`操作执行shell/bat脚本。
> * 元素的值建议都添加在`<![CDATA[]]>`中，以免出现特殊字符匹配失败的情况。


### 4.2.12 args元素
![args元素][18]

`args`元素当前主要用在`OperGenerateQrcode`生成二维码操作中，为操作提供相应的参数，且参数的顺序是固定的。如下示例是生成二维码：
```xml
<execution name="生成下载移动端二维码图" id="generateQrCode" display="true" class-name="deploy.OperGenerateQrcode">
	<configuration>
		<args>
			<!-- 二维码内容 -->
			<arg><![CDATA[http://$${server_web_extranet_host}:$${server_web_intranet_port}/data]]></arg>
			<!-- 图片宽度 -->
			<arg><![CDATA[110]]></arg>
			<!-- 图片高度 -->
			<arg><![CDATA[110]]></arg>
			<!-- 生成图片存放绝对路径,包含图片名称及后缀，如scan.png -->
			<arg><![CDATA[$${deployment_home}/package/test/qrcode.png]]></arg>
		</args>
	</configuration>
</execution>
```

说明：
> * OperGenerateQrcode操作，参数顺序为（1）二维码内容，（2）图片宽度，（3）图片高度，（4）生成图片存放绝对路径

## 4.3 流程配置文件功能示例
了解配置文件各元素结构与作用后，下面以数据库mariadb在linux下安装卸载为示例，通过组合各元素，完成对数据库的安装。分以下三步实现：
### 4.3.1 分析安装及卸载mariadb需要的模块
安装及卸载mariadb，可分为（1）mariadb安装，（2）卸载。而在安装过程中需要进行：（3）配置文件替换，（4）修改文件执行权限，（5）同步调整系统时间。各模块均可独立运行，也有依赖关系。如在安装mariadb前需先完成(3)(4)(5)操作。最后，把mariadb组合为安装及卸载显示。如此分析，xml的框架就可以出来，如下图：

![mariadb安装卸载][19]
![基本功能][20]

mariadb安装、卸载，其中"安装MySQL及初始化数据库"，分为两个操作，分别是安装及初始化。而基本功能就是（3）（4）（5）功能。

### 4.3.2 确定用户统一配置
安装mariadb，需要配置的内容包括数据库，服务器IP端口信息等等，这些信息设置在custom_config.properties文件中，作为占位符在部署工具的配置中使用。
```
### mysql数据库配置
#数据库IP
server.mysql.host = 127.0.0.1
#数据库端口
server.mysql.port = 3306
#数据库名
server.mysql.dbname = mytest
#数据库用户名
server.mysql.db.username = root
#数据库密码
server.mysql.db.password = 123456
```

### 4.3.3 编写流程配置文件

经过上面的分析，这些模块中，每个模块都可以作为一个`execution`元素，mariadb的安装与卸载可为一组(group)（3）替换配置文件（4）修改文件执行权限（5）同步系统时间可为一组，作为基本功能。因此，

- 编写mariadb安装`execution`:操作是`OperRunDependency`类，它需要执行两个依赖(`dependency`)操作。而实际的操作是两个子操作(`sub-execution`)，它们是`OperRunCommand`类，一个是安装数据库，一个是初始化数据库。分别通过执行`install_mysqldb.sh`和`import_mysql_sql.sh`实现。安装同时开通端口，安装前需先执行（3）（4）（5），因此使用`dependency`来实现。因此，xml配置如下：
```xml
<execution name="安装MySQL及初始化数据库" id="installMysqlAndInitDb" display="true" class-name="deploy.OperRunDependency">
	<dependencies>
		<dependency ref-id="installMysql"/>
		<dependency ref-id="createMysqlDb"/>
	</dependencies>
	<sub-execution>
		<execution name="安装MySQL" id="installMysql" display="true" class-name="deploy.OperRunCommand">
			<configuration>
				<commands>
					<command charset="utf-8">
						<exec><![CDATA[scripts/linux/db/mariadb/install_mysqldb.sh]]></exec>
						<args>
							<arg><![CDATA[$${server.mysql.db.password}]]></arg>
						</args>
					</command>
					<!-- 开通端口 -->
					<command charset="utf-8">
						<exec><![CDATA[scripts/linux/common/add_port.sh]]></exec>
						<args>
							<arg><![CDATA[$${server.mysql.port}]]></arg>
						</args>
					</command>
				</commands>
			</configuration>
			<dependencies>
				<dependency ref-id="updateConfigFiles"/>
				<dependency ref-id="chmodFile"/>
				<dependency ref-id="syncOsTime"/>
			</dependencies>
		</execution>
		<execution name="初始化MySQL数据库" id="createMysqlDb" display="true" class-name="deploy.OperRunCommand">
			<configuration>
				<commands>
					<command charset="utf-8">
						<exec><![CDATA[scripts/linux/db/mariadb/import_mysql_sql.sh]]></exec>
						<args>
							<arg><![CDATA[$${server.mysql.db.username}]]></arg>
							<arg><![CDATA[$${server.mysql.db.password}]]></arg>
							<arg><![CDATA[$${server.mysql.port}]]></arg>
						</args>
					</command>
				</commands>
			</configuration>
			<dependencies>
				<dependency ref-id="updateConfigFiles"/>
				<dependency ref-id="chmodFile"/>
				<dependency ref-id="syncOsTime"/>
			</dependencies>
		</execution>
	</sub-execution>
</execution>
```

- 由于要把mariadb安装和卸载为一组显示，添加组合(`group`)用于显示，即可。如下：
```xml
<group name="MySQL安装与卸载">
    安装及卸载的execution
</group>
```

此示例，使用部署工具运行后，显示结果如下：
![mysql安装卸载][21]

## 4.4 部署脚本编写
在编写流程部署文件时，大部分部署功能都应在脚本中实现，脚本先验证部署通过后，再放到部署工具中使用。部署脚本在linux中使用shell编写，windows中使用bat脚本编写。具体shell及bat的语法不在这里介绍（读者请自己学习）。下面是linux下安装mysql的脚本示例：
```shell
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

```
由示例可见，一般执行脚本，都建议先定位到部署目录，然后基于部署目录设置相应的操作目录，额外的参数可通过xml流程配置中输入。其它的就是普通脚本编写了。脚本编写完成后，可统一放到scripts目录下，并按模块分目录，便于查找即可。

注意：
> * windows下bat脚本，使用`%~dp0`定位当前bat的目录。
> * linux下shell脚本，使用` $(pwd)`定位运行部署工具（deploy-tool.jar）的sh文件所在位置（即install目录）。因此获取部署目录，使用`$(dirname $(pwd))`即可。
> * linux下若使用sh文件A调用另外一个sh文件B，而sh文件B是使用`ps|grep|awk`获取进程号（以便进行kill操作），会有子Shell的（临时）进程的问题，导致获取的进程号不正确。需要对文件A与文件B进行子进程过滤。如可使用``PID=`ps aux|grep -v grep|grep -v A|grep -v B|awk '{print $2}'` ``。其中A及B是文件名。



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