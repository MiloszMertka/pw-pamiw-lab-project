import 'package:flutter/material.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/car.dart';
import 'package:mobile_client/services/car_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:mobile_client/shared/resource_card.dart';

class CarList extends StatefulWidget {
  const CarList({super.key});

  @override
  State<CarList> createState() => _CarListState();
}

class _CarListState extends State<CarList> {
  final _carService = locator<CarService>();

  List<Car> _cars = [];

  Future<void> _fetchCars() async {
    final fetchedCars = await _carService.fetchCars();
    setState(() {
      _cars = fetchedCars;
    });
  }

  Future<void> _deleteCar(Car car) async {
    await _carService.deleteCar(car);
    await _fetchCars();
  }

  Future<void> _navigateToCarForm(Car? car) async {
    await Navigator.pushNamed(context, '/car-form', arguments: car);
    await _fetchCars();
  }

  @override
  void initState() {
    super.initState();
    _fetchCars();
  }

  @override
  Widget build(BuildContext context) {
    return AppScaffold(
      title: 'Cars',
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          _navigateToCarForm(null);
        },
      ),
      body: ListView.builder(
        itemCount: _cars.length,
        itemBuilder: (context, index) {
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 8),
            child: ResourceCard(
              title: 'Name: ${_cars[index].name}',
              onEdit: () => _navigateToCarForm(_cars[index]),
              onDelete: () => _deleteCar(_cars[index]),
              deleteDialogTitle: 'Delete Car',
              children: <Widget>[
                Text('Color: ${_cars[index].color}'),
                Text('Engine: ${_cars[index].engine.name} ${_cars[index].engine.horsePower} HP'),
                const Text('Equipment Options:'),
                ListBody(
                  children: _cars[index].equipmentOptions.map((equipmentOption) {
                    return Text('- ${equipmentOption.name}');
                  }).toList(),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}
