import 'dart:io';

import 'package:flutter/cupertino.dart' hide ImageProvider;
import 'package:flutter/material.dart' hide ImageProvider;
import 'package:flutter/services.dart';

import 'entities.dart';
import 'image_provider.dart';

class Glide extends StatefulWidget {
  /// Loading组件
  final Widget? placeholder;
  /// 图片宽度
  final double? width;
  /// 图片高度
  final double? height;
  /// 图片展示模式
  final BoxFit fit;
  /// 图片内间距
  final EdgeInsetsGeometry padding;
  /// 图片外边距
  final EdgeInsetsGeometry margin;
  /// 图片资源提供者
  final ImageProvider image;
  /// 图片渲染质量，默认为低质量
  /// 注：设置为高质量会有BUG，默认情况为低质量
  final FilterQuality filterQuality;

  Glide.network(String url, {
    Key? key,
    this.placeholder,
    this.width,
    this.height,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.margin: EdgeInsets.zero,
    this.filterQuality: FilterQuality.medium,
  }): this.image = NetworkImageProvider(url);

  Glide.file(File file, {
    Key? key,
    this.placeholder,
    this.width,
    this.height,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.margin: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }): this.image = FileImageProvider(file);

  Glide.drawable(String drawable, {
    Key? key,
    this.placeholder,
    this.width,
    this.height,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.margin: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }): this.image = DrawableImageProvider(drawable);

  Glide.asset(String asset, {
    Key? key,
    this.placeholder,
    this.width,
    this.height,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.margin: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }): this.image = AssetImageProvider(asset);

  const Glide(this.image, {
    Key? key,
    this.placeholder,
    this.width,
    this.height,
    this.fit: BoxFit.contain,
    this.padding: EdgeInsets.zero,
    this.margin: EdgeInsets.zero,
    this.filterQuality: FilterQuality.low,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _GlideState();

  NativeImage get _image => image.resolve()
    ..fit = fit
    ..width = width
    ..height = height;
}

class _GlideState extends State<Glide> {
  NativeImageResult? _imageResult;

  static const MethodChannel _channel = const MethodChannel('org.axen.flutter/flutter_glide');

  @override
  void initState() {
    super.initState();
    _loadImage(widget._image);
  }

  @override
  void didUpdateWidget(covariant Glide oldWidget) {
    super.didUpdateWidget(oldWidget);
    final NativeImage oldImage = oldWidget._image;
    final NativeImage image = widget._image;
    if (oldImage != image) {
      _loadImage(image);
      _releaseImage(oldImage);
    } else {
      _updateImage(_imageResult);
    }
  }

  @override
  void dispose() {
    super.dispose();
    _releaseImage(widget._image);
  }


  @override
  Widget build(BuildContext context) {
    Widget child;
    if (_imageResult == null) {
      child = widget.placeholder??SizedBox(
        width: widget.width??64.0,// 宽高要设置默认大小，否则在ListView中会报错
        height: widget.height??64.0,
      );
    } else {
      child = Container(
        width: _imageResult?.size.width??0,
        height: _imageResult?.size.height??0,
        child: Texture(textureId: _imageResult!.textureId),
      );
    }
    return Container(
      width: widget.width,
      height: widget.height,
      padding: widget.padding,
      margin: widget.margin,
      child: ClipRect(
          child: FittedBox(
            alignment: Alignment.center,
            fit: widget.fit,
            child: child,
          )
      ),
    );
  }

  void _loadImage(final NativeImage image) {
    Map<String, dynamic> data = image.toMap();
    _channel.invokeMethod("load", data)
        .then((value) {
      int textureId = value["textureId"];
      int width = value["width"];
      int height = value["height"];
      Size size = _caculateImageWidgetSize(width, height);
      NativeImageResult result = NativeImageResult(textureId, size);
      if (mounted) {
        _updateImage(result);
      }
    });
  }

  void _updateImage(NativeImageResult? result) {
    setState(() => _imageResult = result);
  }

  void _releaseImage(NativeImage image) {
    _channel.invokeMethod("release", {
      "source": image.source
    });
  }

  /// 计算图片显示的宽高
  /// @param width 图片真实宽度
  /// @param height 图片真实高度
  Size _caculateImageWidgetSize(int w, int h) {
    final double? wWidth = widget.width;
    final double? wHeight = widget.height;
    final double cWidth;
    final double cHeight;
    if (wWidth == null && wHeight == null) {
      cWidth = w * 1.0;
      cHeight = h * 1.0;
    } else if (wWidth == null && wHeight != null) {
      cHeight = wHeight * 1.0;
      cWidth = wHeight * w / h;
    } else if (wWidth != null && wHeight == null) {
      cWidth = wWidth;
      cHeight = cWidth * h / w;
    } else {
      cWidth = wWidth!;
      cHeight = wHeight!;
    }
    final double width = w * 1.0;
    final double height = h * 1.0;
    if ((width <= cWidth && height <= cHeight)) {
      return Size(width, height);
    }
    if (widget.fit == BoxFit.fill) {
      return Size(cWidth, cHeight);
    }
    if (widget.fit == BoxFit.fitWidth
        || (width <= height && widget.fit == BoxFit.cover)
        || (width >= height && (widget.fit == BoxFit.scaleDown || widget.fit == BoxFit.contain))
    ) {
      double wRatio = cWidth / width;
      double iHeight = height * wRatio;
      return Size(cWidth, iHeight);
    }
    if (widget.fit == BoxFit.fitHeight
        || (width > height && widget.fit == BoxFit.cover)
        || (width < height && (widget.fit == BoxFit.scaleDown || widget.fit == BoxFit.contain))
    ) {
      double hRatio = cHeight / height;
      double iWidth = width * hRatio;
      return Size(iWidth, cHeight);
    }
    return Size(width, height);
  }

}


