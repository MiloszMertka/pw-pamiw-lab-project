import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/equipment_option.dart';
import 'package:mobile_client/services/equipment_option_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class EquipmentOptionForm extends StatefulWidget {
  const EquipmentOptionForm({super.key, this.equipmentOption});

  final EquipmentOption? equipmentOption;

  @override
  State<EquipmentOptionForm> createState() => _EquipmentOptionFormState();
}

class _EquipmentOptionFormState extends State<EquipmentOptionForm> {
  final _equipmentOptionService = locator<EquipmentOptionService>();

  bool _isLoading = false;
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
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _isLoading = true;
    });

    if (widget.equipmentOption == null) {
      await _createEquipmentOption();
    } else {
      await _updateEquipmentOption();
    }

    setState(() {
      _isLoading = false;
    });
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
    final inversePrimaryColor = Theme.of(context).colorScheme.inversePrimary;
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.equipmentOptionForm,
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
