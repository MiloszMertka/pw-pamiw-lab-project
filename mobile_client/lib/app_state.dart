import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppState extends ChangeNotifier {
  bool _isDarkMode = false;
  Locale _locale = const Locale('en');

  bool get isDarkMode => _isDarkMode;
  Locale get locale => _locale;

  Future<void> loadSettings() async {
    final preferences = await SharedPreferences.getInstance();
    _isDarkMode = preferences.getBool('isDarkMode') ?? false;
    final localeLanguageCode = preferences.getString('localeLanguageCode');

    if (localeLanguageCode != null) {
      _locale = Locale(localeLanguageCode);
    }

    notifyListeners();
  }

  Future<void> saveSettings() async {
    final preferences = await SharedPreferences.getInstance();
    preferences.setBool('isDarkMode', _isDarkMode);
    preferences.setString('localeLanguageCode', _locale.languageCode);
  }

  void setIsDarkMode(bool isDarkMode) {
    _isDarkMode = isDarkMode;
    saveSettings();
    notifyListeners();
  }

  void setLocale(Locale locale) {
    _locale = locale;
    saveSettings();
    notifyListeners();
  }
}
