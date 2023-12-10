import 'dart:convert';

import 'package:mobile_client/constants.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:http/http.dart' as http;
import 'package:mobile_client/services/http_headers.dart';

class EquipmentOptionService {
  const EquipmentOptionService();

  Future<List<EquipmentOption>> fetchEquipmentOptions() async {
    final uri = Uri.parse(equipmentOptionsEndpoint);
    final response = await http.get(uri, headers: createHttpHeaders());
    return equipmentOptionsFromJson(response.body);
  }

  Future<void> createEquipmentOption(EquipmentOption equipmentOption) async {
    final uri = Uri.parse(equipmentOptionsEndpoint);
    await http.post(uri, body: json.encode(equipmentOption.toJson()), headers: createHttpHeaders());
  }

  Future<void> updateEquipmentOption(int id, EquipmentOption equipmentOption) async {
    final uri = Uri.parse('$equipmentOptionsEndpoint/$id');
    await http.put(uri, body: json.encode(equipmentOption.toJson()), headers: createHttpHeaders());
  }

  Future<void> deleteEquipmentOption(EquipmentOption equipmentOption) async {
    final uri = Uri.parse('$equipmentOptionsEndpoint/${equipmentOption.id}');
    await http.delete(uri, headers: createHttpHeaders());
  }
}
