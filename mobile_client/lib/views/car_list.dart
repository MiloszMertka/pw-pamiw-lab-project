import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/car.dart';
import 'package:mobile_client/services/car_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:mobile_client/shared/resource_card.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class CarList extends StatefulWidget {
  const CarList({super.key});

  @override
  State<CarList> createState() => _CarListState();
}

class _CarListState extends State<CarList> {
  final _carService = locator<CarService>();

  List<Car> _cars = [];
  bool _isLoading = false;

  Future<void> _fetchCars() async {
    setState(() {
      _isLoading = true;
    });
    final fetchedCars = await _carService.fetchCars();
    setState(() {
      _cars = fetchedCars;
      _isLoading = false;
    });
  }

  Future<void> _deleteCar(Car car) async {
    setState(() {
      _isLoading = true;
    });
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
    final inversePrimaryColor = Theme.of(context).colorScheme.inversePrimary;
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.cars,
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          _navigateToCarForm(null);
        },
      ),
      body: _isLoading
          ? SpinKitDualRing(color: inversePrimaryColor)
          : ListView.builder(
              itemCount: _cars.length,
              itemBuilder: (context, index) {
                return Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 8),
                  child: ResourceCard(
                    title: '${localizations.name}: ${_cars[index].name}',
                    onEdit: () => _navigateToCarForm(_cars[index]),
                    onDelete: () => _deleteCar(_cars[index]),
                    deleteDialogTitle: localizations.deleteCar,
                    children: <Widget>[
                      Text('${localizations.color}: ${_cars[index].color}'),
                      Text('${localizations.engine}: ${_cars[index].engine.name} ${_cars[index].engine.horsePower} HP'),
                      Text('${localizations.equipmentOptions}:'),
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
