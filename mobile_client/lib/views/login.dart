import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:mobile_client/app_state.dart';
import 'package:mobile_client/constants.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/user_login.dart';
import 'package:mobile_client/services/auth_service.dart';
import 'package:provider/provider.dart';

class Login extends StatefulWidget {
  const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final _authService = locator<AuthService>();
  final _googleSignIn = GoogleSignIn(
    clientId: googleClientId,
    scopes: ['email', 'profile', 'openid'],
  );

  bool _isLoading = false;
  bool _invalidCredentials = false;
  final _formKey = GlobalKey<FormState>();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _submitForm() async {
    setState(() {
      _invalidCredentials = false;
    });

    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _isLoading = true;
    });

    await _login();

    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _login() async {
    final username = _usernameController.text;
    final password = _passwordController.text;
    final userLogin = UserLogin(username: username, password: password);
    final jwt = await _authService.login(userLogin);

    if (jwt == null) {
      setState(() {
        _invalidCredentials = true;
      });
      return;
    }

    Provider.of<AppState>(context, listen: false).setJwt(jwt);
    Navigator.pushReplacementNamed(context, '/cars');
  }

  Future<void> _loginWithGoogle() async {
    final account = await _googleSignIn.signIn();

    if (account == null) {
      return;
    }

    final auth = await account.authentication;
    final token = auth.idToken;

    Provider.of<AppState>(context, listen: false).setGoogleToken(token!);
    Navigator.pushReplacementNamed(context, '/cars');
  }

  @override
  Widget build(BuildContext context) {
    final inversePrimaryColor = Theme.of(context).colorScheme.inversePrimary;
    final errorColor = Theme.of(context).colorScheme.error;
    final localizations = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppBar(
        backgroundColor: inversePrimaryColor,
        title: Text(localizations.login),
      ),
      drawer: Drawer(
        child: ListView(
          children: [
            const DrawerHeader(
              child: Column(
                children: [
                  Text(
                    'DreamCars',
                    style: TextStyle(
                      fontSize: 24,
                    ),
                  ),
                ],
              ),
            ),
            ListTile(
              title: Text(localizations.login),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/login');
              },
            ),
            ListTile(
              title: Text(localizations.register),
              onTap: () {
                Navigator.pushReplacementNamed(context, '/register');
              },
            ),
          ],
        ),
      ),
      body: _isLoading
          ? SpinKitDualRing(color: inversePrimaryColor)
          : Form(
              key: _formKey,
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.all(32),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      TextFormField(
                        controller: _usernameController,
                        decoration: InputDecoration(
                          labelText: localizations.username,
                        ),
                        validator: (value) {
                          if (value == null || value.trim().isEmpty) {
                            return localizations.required;
                          }

                          return null;
                        },
                      ),
                      TextFormField(
                        controller: _passwordController,
                        decoration: InputDecoration(
                          labelText: localizations.password,
                        ),
                        validator: (value) {
                          if (value == null || value.trim().isEmpty) {
                            return localizations.required;
                          }

                          return null;
                        },
                        obscureText: true,
                      ),
                      const SizedBox(height: 16),
                      Text(
                        _invalidCredentials ? localizations.invalidCredentials : '',
                        style: TextStyle(color: errorColor),
                      ),
                      const SizedBox(height: 16),
                      ElevatedButton(
                        onPressed: _submitForm,
                        child: Text(localizations.login.toUpperCase()),
                      ),
                      const SizedBox(height: 8),
                      Text(localizations.or.toUpperCase()),
                      const SizedBox(height: 8),
                      ElevatedButton(
                        onPressed: _loginWithGoogle,
                        child: Text(localizations.loginWithGoogle.toUpperCase()),
                      ),
                    ],
                  ),
                ),
              ),
            ),
    );
  }
}
