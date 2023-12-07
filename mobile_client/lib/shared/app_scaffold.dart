import 'package:flutter/material.dart';

class AppScaffold extends StatefulWidget {
  const AppScaffold({super.key, required this.title, required this.body, this.floatingActionButton});

  final String title;
  final Widget body;
  final Widget? floatingActionButton;

  @override
  State<AppScaffold> createState() => _AppScaffoldState();
}

class _AppScaffoldState extends State<AppScaffold> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      floatingActionButton: widget.floatingActionButton,
      drawer: Drawer(
        child: ListView(
          children: [
            DrawerHeader(
              child: Column(
                children: [
                  const Text(
                    'DreamCars',
                    style: TextStyle(
                      fontSize: 24,
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextButton(onPressed: () {}, child: const Text('Logout')),
                ],
              ),
            ),
            ListTile(
              title: const Text('Cars'),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/cars');
              },
            ),
            ListTile(
              title: const Text('Engines'),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/engines');
              },
            ),
            ListTile(
              title: const Text('Equipment Options'),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/equipment-options');
              },
            ),
            ListTile(
              title: const Text('Settings'),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/settings');
              },
            ),
          ],
        ),
      ),
      body: widget.body,
    );
  }
}
