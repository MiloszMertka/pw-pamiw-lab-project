import 'package:dropdown_textfield/dropdown_textfield.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/car.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:mobile_client/services/car_service.dart';
import 'package:mobile_client/services/engine_service.dart';
import 'package:mobile_client/services/equipment_option_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class CarForm extends StatefulWidget {
  const CarForm({super.key, this.car});

  final Car? car;

  @override
  State<CarForm> createState() => _CarFormState();
}

class _CarFormState extends State<CarForm> {
  final _carService = locator<CarService>();
  final _engineService = locator<EngineService>();
  final _equipmentOptionService = locator<EquipmentOptionService>();

  List<Engine> _engines = [];
  List<EquipmentOption> _equipmentOptions = [];
  bool _isLoading = false;
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _colorController = TextEditingController();
  final _engineController = SingleValueDropDownController();
  final _equipmentOptionsController = MultiValueDropDownController();

  @override
  void initState() {
    super.initState();
    final car = widget.car;
    if (car != null) {
      _nameController.text = car.name;
      _colorController.text = car.color;
      _engineController.dropDownValue = DropDownValueModel(name: car.engine.name, value: car.engine);
      _equipmentOptionsController.dropDownValueList = car.equipmentOptions.map((e) => DropDownValueModel(name: e.name, value: e)).toList();
    }
    _fetchData();
  }

  @override
  void dispose() {
    _nameController.dispose();
    _colorController.dispose();
    _engineController.dispose();
    _equipmentOptionsController.dispose();
    super.dispose();
  }

  Future<void> _fetchData() async {
    setState(() {
      _isLoading = true;
    });

    await _fetchEngines();
    await _fetchEquipmentOptions();

    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _fetchEngines() async {
    final fetchedEngines = await _engineService.fetchEngines();
    setState(() {
      _engines = fetchedEngines;
    });
  }

  Future<void> _fetchEquipmentOptions() async {
    final fetchedEquipmentOptions = await _equipmentOptionService.fetchEquipmentOptions();
    setState(() {
      _equipmentOptions = fetchedEquipmentOptions;
    });
  }

  Future<void> _submitForm() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _isLoading = true;
    });

    if (widget.car == null) {
      await _createCar();
    } else {
      await _updateCar();
    }

    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _createCar() async {
    final car = Car(
        name: _nameController.text,
        color: _colorController.text,
        engine: _engineController.dropDownValue!.value as Engine,
        equipmentOptions: _equipmentOptionsController.dropDownValueList!.map((e) => e.value as EquipmentOption).toList());
    await _carService.createCar(car);
    Navigator.pop(context);
  }

  Future<void> _updateCar() async {
    final car = Car(
        name: _nameController.text,
        color: _colorController.text,
        engine: _engineController.dropDownValue!.value as Engine,
        equipmentOptions: _equipmentOptionsController.dropDownValueList!.map((e) => e.value as EquipmentOption).toList());
    await _carService.updateCar(widget.car!.id!, car);
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final inversePrimaryColor = theme.colorScheme.inversePrimary;
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.carForm,
      body: _isLoading
          ? SpinKitDualRing(color: inversePrimaryColor)
          : Form(
              key: _formKey,
              child: Padding(
                padding: const EdgeInsets.all(32),
                child: Column(
                  children: <Widget>[
                    TextFormField(
                      controller: _nameController,
                      decoration: InputDecoration(
                        labelText: localizations.name,
                      ),
                      validator: (value) {
                        if (value == null || value.trim().isEmpty) {
                          return localizations.required;
                        }

                        return null;
                      },
                    ),
                    TextFormField(
                      controller: _colorController,
                      decoration: InputDecoration(
                        labelText: localizations.color,
                      ),
                      validator: (value) {
                        if (value == null || value.trim().isEmpty) {
                          return localizations.required;
                        }

                        return null;
                      },
                    ),
                    DropDownTextField(
                      controller: _engineController,
                      dropDownItemCount: _engines.length,
                      dropDownList: _engines.map((engine) => DropDownValueModel(name: engine.name, value: engine)).toList(),
                      validator: (value) {
                        if (value == null) {
                          return localizations.required;
                        }

                        return null;
                      },
                    ),
                    DropDownTextField.multiSelection(
                      controller: _equipmentOptionsController,
                      displayCompleteItem: true,
                      dropDownItemCount: _equipmentOptions.length,
                      dropDownList: _equipmentOptions.map((equipmentOption) => DropDownValueModel(name: equipmentOption.name, value: equipmentOption)).toList(),
                      submitButtonColor: inversePrimaryColor,
                      submitButtonText: localizations.save.toUpperCase(),
                      submitButtonTextStyle: theme.textTheme.labelLarge,
                    ),
                    const SizedBox(height: 32),
                    ElevatedButton(
                      onPressed: _submitForm,
                      child: Text(localizations.save.toUpperCase()),
                    ),
                  ],
                ),
              ),
            ),
    );
  }
}
