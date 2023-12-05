import 'dart:convert';

List<EquipmentOption> equipmentOptionsFromJson(String str) => List<EquipmentOption>.from(json.decode(str).map((x) => EquipmentOption.fromJson(x)));

class EquipmentOption {
  final int? id;
  final String name;

  EquipmentOption({this.id, required this.name});

  factory EquipmentOption.fromJson(Map<String, dynamic> json) => EquipmentOption(
        id: json["id"],
        name: json["name"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
      };
}
