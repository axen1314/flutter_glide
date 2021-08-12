import 'package:flutter/material.dart';

import 'package:flutter_glide/flutter_glide.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String url = "http://img.netbian.com/file/2020/0904/7cab180eca805cce596b6870cb4e1379.jpg";

  @override
  void initState() {
    super.initState();
    Future.delayed(Duration(seconds: 2), () {
      setState(() {
        url = "http://pic1.win4000.com/wallpaper/5/545b2003c6ac9.jpg";
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
            child: Column(
              children: [
                Image.network(url, width: 200, height: 200),
                Glide.network(url, width: 200, height: 200)
              ],
            )
        ),
      ),
    );
  }
}
