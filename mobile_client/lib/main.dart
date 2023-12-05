import 'package:flutter/material.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/car.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:mobile_client/views/car_form.dart';
import 'package:mobile_client/views/car_list.dart';
import 'package:mobile_client/views/engine_form.dart';
import 'package:mobile_client/views/engine_list.dart';
import 'package:mobile_client/views/equipment_option_form.dart';
import 'package:mobile_client/views/equipment_option_list.dart';
import 'package:mobile_client/views/home.dart';

void main() {
  setupLocator();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Mobile Client',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      routes: {
        '/': (context) => const Home(),
        '/cars': (context) => const CarList(),
        '/car-form': (context) {
          var car = ModalRoute.of(context)!.settings.arguments as Car?;
          return CarForm(car: car);
        },
        '/engines': (context) => const EngineList(),
        '/engine-form': (context) {
          var engine = ModalRoute.of(context)!.settings.arguments as Engine?;
          return EngineForm(engine: engine);
        },
        '/equipment-options': (context) => const EquipmentOptionList(),
        '/equipment-option-form': (context) {
          var equipmentOption = ModalRoute.of(context)!.settings.arguments as EquipmentOption?;
          return EquipmentOptionForm(equipmentOption: equipmentOption);
        },
      },
    );
  }
}
