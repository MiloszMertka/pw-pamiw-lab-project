import 'package:flutter/material.dart';
import 'package:mobile_client/models/jwt.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppState extends ChangeNotifier {
  bool _isDarkMode = false;
  Locale _locale = const Locale('en');
  static Jwt? _jwt;
  static String? _googleToken;

  bool get isDarkMode => _isDarkMode;
  Locale get locale => _locale;
  static Jwt? get jwt => _jwt;
  static String? get googleToken => _googleToken;

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

  void setJwt(Jwt? jwt) {
    _jwt = jwt;
    notifyListeners();
  }

  void setGoogleToken(String? token) {
    _googleToken = token;
    notifyListeners();
  }
}
