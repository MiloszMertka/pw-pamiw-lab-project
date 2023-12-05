import 'package:flutter/material.dart';
import 'package:mobile_client/constants.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/services/engine_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';

class EngineForm extends StatefulWidget {
  const EngineForm({super.key, this.engine});

  final Engine? engine;

  @override
  State<EngineForm> createState() => _EngineFormState();
}

class _EngineFormState extends State<EngineForm> {
  final _engineService = locator<EngineService>();

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
    if (widget.engine == null) {
      _createEngine();
    } else {
      _updateEngine();
    }
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
    return AppScaffold(
      title: "Engine Form",
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
              TextFormField(
                controller: _horsePowerController,
                decoration: const InputDecoration(
                  labelText: 'Horse Power',
                ),
                keyboardType: TextInputType.number,
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
