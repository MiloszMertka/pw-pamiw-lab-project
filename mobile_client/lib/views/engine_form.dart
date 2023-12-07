import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/services/engine_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class EngineForm extends StatefulWidget {
  const EngineForm({super.key, this.engine});

  final Engine? engine;

  @override
  State<EngineForm> createState() => _EngineFormState();
}

class _EngineFormState extends State<EngineForm> {
  final _engineService = locator<EngineService>();

  bool _isLoading = false;
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _horsePowerController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _nameController.text = widget.engine?.name ?? '';
    _horsePowerController.text = widget.engine?.horsePower.toString() ?? '';
  }

  @override
  void dispose() {
    _nameController.dispose();
    _horsePowerController.dispose();
    super.dispose();
  }

  Future<void> _submitForm() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _isLoading = true;
    });

    if (widget.engine == null) {
      await _createEngine();
    } else {
      await _updateEngine();
    }

    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _createEngine() async {
    final engine = Engine(name: _nameController.text, horsePower: int.parse(_horsePowerController.text));
    await _engineService.createEngine(engine);
    Navigator.pop(context);
  }

  Future<void> _updateEngine() async {
    final engine = Engine(name: _nameController.text, horsePower: int.parse(_horsePowerController.text));
    await _engineService.updateEngine(widget.engine!.id!, engine);
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    final inversePrimaryColor = Theme.of(context).colorScheme.inversePrimary;
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.engineForm,
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
                      controller: _horsePowerController,
                      decoration: InputDecoration(
                        labelText: localizations.horsePower,
                      ),
                      keyboardType: TextInputType.number,
                      validator: (value) {
                        if (value == null || value.trim().isEmpty) {
                          return localizations.required;
                        }

                        if (int.tryParse(value) == null) {
                          return localizations.integer;
                        }

                        if (int.parse(value) <= 0) {
                          return localizations.positive;
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
