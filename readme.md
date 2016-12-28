## lsharepreferlib
对SharedPreferences的简单封装，使用更加简单.
## Download
Maven:
```
<dependency>
  <groupId>com.ljb</groupId>
  <artifactId>sharepre</artifactId>
  <version>0.1.2</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```
compile 'com.ljb:sharepre:0.1.2'
```
## Configuration
- LSharePreferUtils.init(this);//在Appliaction初始化
- 你可以使用LSharePreferUtils put 或者get 方法来存储或获取数据。