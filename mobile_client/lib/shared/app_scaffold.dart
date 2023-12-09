import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:mobile_client/app_state.dart';
import 'package:provider/provider.dart';

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
    final localizations = AppLocalizations.of(context)!;

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
                  Consumer<AppState>(builder: (context, appState, child) {
                    return TextButton(
                        onPressed: () {
                          appState.setJwt(null);
                          Navigator.pushReplacementNamed(context, '/login');
                        },
                        child: Text(localizations.logout));
                  }),
                ],
              ),
            ),
            ListTile(
              title: Text(localizations.cars),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/cars');
              },
            ),
            ListTile(
              title: Text(localizations.engines),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/engines');
              },
            ),
            ListTile(
              title: Text(localizations.equipmentOptions),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/equipment-options');
              },
            ),
            ListTile(
              title: Text(localizations.settings),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/settings');
              },
            ),
            ListTile(
              title: Text(localizations.carScanner),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/car-scanner');
              },
            ),
          ],
        ),
      ),
      body: widget.body,
    );
  }
}
