import 'package:flutter/material.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/services/engine_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:mobile_client/shared/resource_card.dart';

class EngineList extends StatefulWidget {
  const EngineList({super.key});

  @override
  State<EngineList> createState() => _EngineListState();
}

class _EngineListState extends State<EngineList> {
  final _engineService = locator<EngineService>();

  List<Engine> _engines = [];

  Future<void> _fetchEngines() async {
    final fetchedEngines = await _engineService.fetchEngines();
    setState(() {
      _engines = fetchedEngines;
    });
  }

  Future<void> _deleteEngine(Engine engine) async {
    await _engineService.deleteEngine(engine);
    await _fetchEngines();
  }

  Future<void> _navigateToEngineForm(Engine? engine) async {
    await Navigator.pushNamed(context, '/engine-form', arguments: engine);
    await _fetchEngines();
  }

  @override
  void initState() {
    super.initState();
    _fetchEngines();
  }

  @override
  Widget build(BuildContext context) {
    return AppScaffold(
      title: 'Engines',
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          _navigateToEngineForm(null);
        },
      ),
      body: ListView.builder(
        itemCount: _engines.length,
        itemBuilder: (context, index) {
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 8),
            child: ResourceCard(
              title: 'Name: ${_engines[index].name}',
              onEdit: () => _navigateToEngineForm(_engines[index]),
              onDelete: () => _deleteEngine(_engines[index]),
              deleteDialogTitle: 'Delete Engine',
              children: <Widget>[
                Text('Horse Power: ${_engines[index].horsePower} HP'),
              ],
            ),
          );
        },
      ),
    );
  }
}
