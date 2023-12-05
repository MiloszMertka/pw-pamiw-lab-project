import 'dart:convert';

import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/models/equipment_option.dart';

List<Car> carsFromJson(String str) => List<Car>.from(json.decode(str).map((x) => Car.fromJson(x)));

class Car {
  final int? id;
  final String name;
  final String color;
  final Engine engine;
  final List<EquipmentOption> equipmentOptions;

  Car({this.id, required this.name, required this.color, required this.engine, required this.equipmentOptions});

  factory Car.fromJson(Map<String, dynamic> json) => Car(
        id: json["id"],
        name: json["name"],
        color: json["color"],
        engine: Engine.fromJson(json["engine"]),
        equipmentOptions: List<EquipmentOption>.from(json["equipmentOptions"].map((x) => EquipmentOption.fromJson(x))),
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "color": color,
        "engine": engine.toJson(),
        "equipmentOptions": List<dynamic>.from(equipmentOptions.map((x) => x.toJson())),
      };
}
