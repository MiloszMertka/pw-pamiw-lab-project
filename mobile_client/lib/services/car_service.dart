import 'dart:convert';

import 'package:mobile_client/app_state.dart';
import 'package:mobile_client/constants.dart';
import 'package:mobile_client/models/car.dart';
import 'package:http/http.dart' as http;

class CarService {
  const CarService();

  Future<List<Car>> fetchCars() async {
    final uri = Uri.parse(carsEndpoint);
    final response = await http.get(uri, headers: {
      'Authorization': 'Bearer ${AppState.jwt!.token}',
    });
    return carsFromJson(response.body);
  }

  Future<void> createCar(Car car) async {
    final uri = Uri.parse(carsEndpoint);
    await http.post(uri, body: json.encode(car.toJson()), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${AppState.jwt!.token}',
    });
  }

  Future<void> updateCar(int id, Car car) async {
    final uri = Uri.parse('$carsEndpoint/$id');
    await http.put(uri, body: json.encode(car.toJson()), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${AppState.jwt!.token}',
    });
  }

  Future<void> deleteCar(Car car) async {
    final uri = Uri.parse('$carsEndpoint/${car.id}');
    await http.delete(uri, headers: {
      'Authorization': 'Bearer ${AppState.jwt!.token}',
    });
  }
}
