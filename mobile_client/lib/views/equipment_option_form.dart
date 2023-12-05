import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:mobile_client/constants.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:mobile_client/services/equipment_option_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';

class EquipmentOptionForm extends StatefulWidget {
  const EquipmentOptionForm({super.key, this.equipmentOption});

  final EquipmentOption? equipmentOption;

  @override
  State<EquipmentOptionForm> createState() => _EquipmentOptionFormState();
}

class _EquipmentOptionFormState extends State<EquipmentOptionForm> {
  final _equipmentOptionService = locator<EquipmentOptionService>();

  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _nameController.text = widget.equipmentOption?.name ?? '';
  }

  @override
  void dispose() {
    _nameController.dispose();
    super.dispose();
  }

  Future<void> _submitForm() async {
    if (widget.equipmentOption == null) {
      _createEquipmentOption();
    } else {
      _updateEquipmentOption();
    }
  }

  Future<void> _createEquipmentOption() async {
    final equipmentOption = EquipmentOption(name: _nameController.text);
    await _equipmentOptionService.createEquipmentOption(equipmentOption);
    Navigator.pop(context);
  }

  Future<void> _updateEquipmentOption() async {
    final equipmentOption = EquipmentOption(name: _nameController.text);
    await _equipmentOptionService.updateEquipmentOption(widget.equipmentOption!.id!, equipmentOption);
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    return AppScaffold(
      title: "Equipment Option Form",
      body: Form(
        key: _formKey,
        child: Padding(
          padding: const EdgeInsets.all(32),
          child: Column(
            children: <Widget>[
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(
                  labelText: 'Name',
                ),
              ),
              const SizedBox(height: 32),
              ElevatedButton(
                onPressed: _submitForm,
                child: const Text('SAVE'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
