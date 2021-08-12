import 'dart:io';

enum Resource { NETWORK, DRAWABLE, FILE, ASSET }

/// Drawable图片资源
class DrawableImageProvider extends ImageProvider<String> {
  final String drawable;

  DrawableImageProvider(this.drawable);

  @override
  Map<String, dynamic> resolve() => {"resource": drawable, "resourceType": Resource.DRAWABLE.index};
}

/// 文件图片资源
class FileImageProvider extends ImageProvider<File> {
  final File file;

  FileImageProvider(this.file);

  @override
  Map<String, dynamic> resolve() => {"resource": file.path, "resourceType": Resource.FILE.index};
}

/// 网络图片资源
class NetworkImageProvider extends ImageProvider<String> {
  final String url;

  NetworkImageProvider(this.url);

  @override
  Map<String, dynamic> resolve() => {"resource": url, "resourceType": Resource.NETWORK.index};

}

/// Asset图片资源
class AssetImageProvider extends ImageProvider<String> {
  final String asset;

  AssetImageProvider(this.asset);
  
  @override
  Map<String, dynamic> resolve() => {"resource": asset, "resourceType": Resource.ASSET.index};
  
}

abstract class ImageProvider<T> {
  Map<String, dynamic> resolve();
}