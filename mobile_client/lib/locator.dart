import 'package:get_it/get_it.dart';
import 'package:mobile_client/services/auth_service.dart';
import 'package:mobile_client/services/car_service.dart';
import 'package:mobile_client/services/engine_service.dart';
import 'package:mobile_client/services/equipment_option_service.dart';

final locator = GetIt.I;

void setupLocator() {
  locator.registerSingleton<CarService>(const CarService());
  locator.registerSingleton<EngineService>(const EngineService());
  locator.registerSingleton<EquipmentOptionService>(const EquipmentOptionService());
  locator.registerSingleton<AuthService>(const AuthService());
}
