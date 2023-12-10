import 'package:mobile_client/app_state.dart';

Map<String, String> createHttpHeaders() {
  var headers = {
    'Content-Type': 'application/json',
  };

  if (AppState.jwt != null) {
    headers['X-Authorization'] = 'Bearer ${AppState.jwt!.token}';
  }

  if (AppState.googleToken != null) {
    headers['Authorization'] = 'Bearer ${AppState.googleToken}';
  }

  return headers;
}
