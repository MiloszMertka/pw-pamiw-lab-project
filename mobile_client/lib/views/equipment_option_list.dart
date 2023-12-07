import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:mobile_client/services/equipment_option_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:mobile_client/shared/resource_card.dart';

class EquipmentOptionList extends StatefulWidget {
  const EquipmentOptionList({super.key});

  @override
  State<EquipmentOptionList> createState() => _EquipmentOptionListState();
}

class _EquipmentOptionListState extends State<EquipmentOptionList> {
  final _equipmentOptionService = locator<EquipmentOptionService>();

  List<EquipmentOption> _equipmentOptions = [];
  bool _isLoading = false;

  Future<void> _fetchEquipmentOptions() async {
    setState(() {
      _isLoading = true;
    });
    final fetchedEquipmentOptions = await _equipmentOptionService.fetchEquipmentOptions();
    setState(() {
      _equipmentOptions = fetchedEquipmentOptions;
      _isLoading = false;
    });
  }

  Future<void> _deleteEquipmentOption(EquipmentOption equipmentOption) async {
    setState(() {
      _isLoading = true;
    });
    await _equipmentOptionService.deleteEquipmentOption(equipmentOption);
    await _fetchEquipmentOptions();
  }

  Future<void> _navigateToEquipmentOptionForm(EquipmentOption? equipmentOption) async {
    await Navigator.pushNamed(context, '/equipment-option-form', arguments: equipmentOption);
    await _fetchEquipmentOptions();
  }

  @override
  void initState() {
    super.initState();
    _fetchEquipmentOptions();
  }

  @override
  Widget build(BuildContext context) {
    final inversePrimaryColor = Theme.of(context).colorScheme.inversePrimary;

    return AppScaffold(
      title: 'Equipment Options',
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          _navigateToEquipmentOptionForm(null);
        },
      ),
      body: _isLoading
          ? SpinKitDualRing(color: inversePrimaryColor)
          : ListView.builder(
              itemCount: _equipmentOptions.length,
              itemBuilder: (context, index) {
                return Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 8),
                  child: ResourceCard(
                    title: 'Name: ${_equipmentOptions[index].name}',
                    onEdit: () => _navigateToEquipmentOptionForm(_equipmentOptions[index]),
                    onDelete: () => _deleteEquipmentOption(_equipmentOptions[index]),
                    deleteDialogTitle: 'Delete Equipment Option',
                    children: const <Widget>[],
                  ),
                );
              },
            ),
    );
  }
}
