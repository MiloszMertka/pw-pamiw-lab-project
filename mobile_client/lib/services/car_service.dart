import 'dart:convert';

import 'package:mobile_client/constants.dart';
import 'package:mobile_client/models/car.dart';
import 'package:http/http.dart' as http;
import 'package:mobile_client/services/http_headers.dart';

class CarService {
  const CarService();

  Future<List<Car>> fetchCars() async {
    final uri = Uri.parse(carsEndpoint);
    final response = await http.get(uri, headers: createHttpHeaders());
    return carsFromJson(response.body);
  }

  Future<void> createCar(Car car) async {
    final uri = Uri.parse(carsEndpoint);
    await http.post(uri, body: json.encode(car.toJson()), headers: createHttpHeaders());
  }

  Future<void> updateCar(int id, Car car) async {
    final uri = Uri.parse('$carsEndpoint/$id');
    await http.put(uri, body: json.encode(car.toJson()), headers: createHttpHeaders());
  }

  Future<void> deleteCar(Car car) async {
    final uri = Uri.parse('$carsEndpoint/${car.id}');
    await http.delete(uri, headers: createHttpHeaders());
  }
}
