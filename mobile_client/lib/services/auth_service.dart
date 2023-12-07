import 'dart:convert';

import 'package:mobile_client/constants.dart';
import 'package:mobile_client/models/jwt.dart';
import 'package:mobile_client/models/user_login.dart';
import 'package:http/http.dart' as http;
import 'package:mobile_client/models/user_register.dart';

class AuthService {
  const AuthService();

  Future<Jwt?> login(UserLogin userLogin) async {
    final uri = Uri.parse(loginEndpoint);
    final response = await http.post(uri, body: json.encode(userLogin.toJson()), headers: {
      'Content-Type': 'application/json',
    });

    if (response.statusCode != 200) {
      return null;
    }

    return Jwt.fromJson(json.decode(response.body));
  }

  Future<void> register(UserRegister userRegister) async {
    final uri = Uri.parse(registerEndpoint);
    await http.post(uri, body: json.encode(userRegister.toJson()), headers: {
      'Content-Type': 'application/json',
    });
  }
}
