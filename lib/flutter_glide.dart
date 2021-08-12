import 'dart:io';

import 'package:flutter/cupertino.dart' hide ImageProvider;
import 'package:flutter/material.dart' hide ImageProvider;
import 'package:flutter/services.dart';

import 'image_provider.dart';

class Glide extends StatefulWidget {
  /// Loading组件
  final Widget? placeholder;
  /// 图片宽度
  final double width;
  /// 图片高度
  final double height;
  /// 图片放大系数，默认值为3.0
  /// 此参数是为了解决Flutter纹理加载后模糊的BUG
  /// 如果出现图片模糊的情况，请将此参数的值调高
  final double scaleRatio;
  /// 图片展示模式
  final BoxFit fit;
  /// 图片内间距
  final EdgeInsetsGeometry padding;
  /// 图片资源提供者
  final ImageProvider image;
  /// 图片渲染质量，默认为低质量
  /// 注：设置为高质量会有BUG，默认情况为低质量
  final FilterQuality filterQuality;

  Glide.network(String url, {
    Key? key,
    this.placeholder,
    required this.width,
    required this.height,
    this.scaleRatio: 3.0,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.filterQuality: FilterQuality.medium,
  }): this.image = NetworkImageProvider(url);

  Glide.file(File file, {
    Key? key,
    this.placeholder,
    required this.width,
    required this.height,
    this.scaleRatio: 3.0,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }): this.image = FileImageProvider(file);

  Glide.drawable(String drawable, {
    Key? key,
    this.placeholder,
    required this.width,
    required this.height,
    this.scaleRatio: 3.0,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }): this.image = DrawableImageProvider(drawable);

  Glide.asset(String asset, {
    Key? key,
    this.placeholder,
    required this.width,
    required this.height,
    this.scaleRatio: 3.0,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }): this.image = AssetImageProvider(asset);

  const Glide(this.image, {
    Key? key,
    this.placeholder,
    required this.width,
    required this.height,
    this.scaleRatio: 3.0,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _GlideState();
}

class _GlideState extends State<Glide> {
  int? _textureId;
  double? _width;
  double? _height;

  static const MethodChannel _channel =
  const MethodChannel('flutter_glide');

  @override
  void initState() {
    super.initState();
    Map<String, dynamic> map = widget.image.resolve();
    map["width"] = widget.width;
    map["height"] = widget.height;
    map["fit"] = widget.fit.index;
    map["scaleRatio"] = widget.scaleRatio;
    _channel.invokeMethod("create", map)
        .then((value) => setState(() {
      _textureId = value["textureId"];
      _width = value["width"] * 1.0;
      _height = value["height"] * 1.0;
    }));
  }

  @override
  void dispose() {
    super.dispose();
    _channel.setMethodCallHandler(null);
  }


  @override
  Widget build(BuildContext context) => Container(
      width: widget.width,
      height: widget.height,
      padding: widget.padding,
      alignment: Alignment.center,
      child: _textureId == null
          ? widget.placeholder??Container()
          : Container(width: _width, height: _height, child: Texture(textureId: _textureId!)
      )
  );

}


