# FlutterGlide

基于Glide的Flutter图片加载控件，支持加载网络图片、Drawable资源、文件资源以及Asset文件夹资源。

## Getting Started

在`pubspec.yaml`添加如下配置:
```yaml
dependencies:
    flutter_glide:
        git:
            url: 'https://github.com/axen1314/flutter_glide.git'
            ref: 'v1.1.4'
```
添加配置后，运行pub更新

## 使用

FlutterGlide提供Glide控件，其使用方式与官方Image控件类似：

1.加载网络图片
```
    Glide.network(
        "http://img.netbian.com/file/2020/0904/7cab180eca805cce596b6870cb4e1379.jpg"
        width: 200,
        height: 200
    );
```
2.加载drawable图片
```
    Glide.drawable(
        "R.drawable.ic_laucher"
        width: 200,
        height: 200
    );
```
3.加载asset图片
```
    Glide.asset(
        "ic_home.png"
        width: 200,
        height: 200
    );
```
4.加载文件
```
    Glide.file(
        File("test.jpg"),
        width: 200,
        height: 200
    );
```



