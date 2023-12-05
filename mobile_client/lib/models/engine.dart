import 'dart:convert';

List<Engine> enginesFromJson(String str) => List<Engine>.from(json.decode(str).map((x) => Engine.fromJson(x)));

class Engine {
  final int? id;
  final String name;
  final int horsePower;

  Engine({this.id, required this.name, required this.horsePower});

  factory Engine.fromJson(Map<String, dynamic> json) => Engine(
        id: json["id"],
        name: json["name"],
        horsePower: json["horsePower"],
      );

  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "horsePower": horsePower,
      };
}
