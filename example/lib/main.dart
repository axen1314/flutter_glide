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

  @override
  void initState() {
    super.initState();
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
                Image.network("http://img.netbian.com/file/2020/0904/7cab180eca805cce596b6870cb4e1379.jpg", width: 200, height: 200),
                // Glide.network("http://img.netbian.com/file/2020/0904/7cab180eca805cce596b6870cb4e1379.jpg", width: 200, height: 200),
                Container(
                  child: Glide.drawable("R.drawable.ic_bar_back", width: 24, height: 24),
                )
              ],
            )
        ),
      ),
    );
  }
}
