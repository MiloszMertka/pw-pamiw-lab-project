import 'package:flutter/material.dart';
import 'package:mobile_client/app_state.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:provider/provider.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class Settings extends StatelessWidget {
  const Settings({super.key});

  @override
  Widget build(BuildContext context) {
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.settings,
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 32),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const SizedBox(height: 32),
            Consumer<AppState>(
              builder: (context, appState, child) => Column(
                children: <Widget>[
                  Row(
                    children: [
                      Text(localizations.darkMode),
                      const Spacer(),
                      Switch(
                        value: appState.isDarkMode,
                        onChanged: (value) => appState.setIsDarkMode(value),
                      )
                    ],
                  ),
                  Row(
                    children: [
                      Text(localizations.language),
                      const Spacer(),
                      DropdownButton<Locale>(
                        value: appState.locale,
                        onChanged: (value) {
                          if (value != null) {
                            appState.setLocale(value);
                          }
                        },
                        items: const <DropdownMenuItem<Locale>>[
                          DropdownMenuItem<Locale>(
                            value: Locale('en'),
                            child: Text('EN'),
                          ),
                          DropdownMenuItem<Locale>(
                            value: Locale('pl'),
                            child: Text('PL'),
                          ),
                        ],
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
