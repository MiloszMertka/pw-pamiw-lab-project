import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:intl/intl.dart';
import 'package:mobile_client/app_state.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/car.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:mobile_client/views/car_form.dart';
import 'package:mobile_client/views/car_list.dart';
import 'package:mobile_client/views/car_scanner.dart';
import 'package:mobile_client/views/engine_form.dart';
import 'package:mobile_client/views/engine_list.dart';
import 'package:mobile_client/views/equipment_option_form.dart';
import 'package:mobile_client/views/equipment_option_list.dart';
import 'package:mobile_client/views/login.dart';
import 'package:mobile_client/views/register.dart';
import 'package:mobile_client/views/settings.dart';
import 'package:provider/provider.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

void main() async {
  setupLocator();

  WidgetsFlutterBinding.ensureInitialized();
  final appState = AppState();
  await appState.loadSettings();

  runApp(ChangeNotifierProvider.value(
    value: appState,
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return Consumer<AppState>(
      builder: (context, appState, child) => MaterialApp(
        title: 'Mobile Client',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: const Color.fromRGBO(89, 74, 226, 1)),
          useMaterial3: true,
        ),
        darkTheme: ThemeData.dark(useMaterial3: true),
        themeMode: appState.isDarkMode ? ThemeMode.dark : ThemeMode.light,
        supportedLocales: const <Locale>[
          Locale('en'),
          Locale('pl'),
        ],
        localizationsDelegates: const <LocalizationsDelegate>[
          AppLocalizations.delegate,
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
          GlobalCupertinoLocalizations.delegate,
        ],
        localeResolutionCallback: (locale, supportedLocales) {
          Intl.defaultLocale = appState.locale.toLanguageTag();
          return appState.locale;
        },
        locale: appState.locale,
        routes: {
          '/': (context) => const Login(),
          '/cars': (context) => const CarList(),
          '/car-form': (context) {
            var car = ModalRoute.of(context)!.settings.arguments as Car?;
            return CarForm(car: car);
          },
          '/engines': (context) => const EngineList(),
          '/engine-form': (context) {
            var engine = ModalRoute.of(context)!.settings.arguments as Engine?;
            return EngineForm(engine: engine);
          },
          '/equipment-options': (context) => const EquipmentOptionList(),
          '/equipment-option-form': (context) {
            var equipmentOption = ModalRoute.of(context)!.settings.arguments as EquipmentOption?;
            return EquipmentOptionForm(equipmentOption: equipmentOption);
          },
          '/settings': (context) => const Settings(),
          '/login': (context) => const Login(),
          '/register': (context) => const Register(),
          '/car-scanner': (context) => const CarScanner(),
        },
      ),
    );
  }
}
