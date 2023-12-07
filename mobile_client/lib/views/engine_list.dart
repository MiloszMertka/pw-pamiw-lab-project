import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:mobile_client/locator.dart';
import 'package:mobile_client/models/engine.dart';
import 'package:mobile_client/services/engine_service.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:mobile_client/shared/resource_card.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class EngineList extends StatefulWidget {
  const EngineList({super.key});

  @override
  State<EngineList> createState() => _EngineListState();
}

class _EngineListState extends State<EngineList> {
  final _engineService = locator<EngineService>();

  List<Engine> _engines = [];
  bool _isLoading = false;

  Future<void> _fetchEngines() async {
    setState(() {
      _isLoading = true;
    });
    final fetchedEngines = await _engineService.fetchEngines();
    setState(() {
      _engines = fetchedEngines;
      _isLoading = false;
    });
  }

  Future<void> _deleteEngine(Engine engine) async {
    setState(() {
      _isLoading = true;
    });
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
    final inversePrimaryColor = Theme.of(context).colorScheme.inversePrimary;
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.engines,
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          _navigateToEngineForm(null);
        },
      ),
      body: _isLoading
          ? SpinKitDualRing(color: inversePrimaryColor)
          : ListView.builder(
              itemCount: _engines.length,
              itemBuilder: (context, index) {
                return Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 8),
                  child: ResourceCard(
                    title: '${localizations.name}: ${_engines[index].name}',
                    onEdit: () => _navigateToEngineForm(_engines[index]),
                    onDelete: () => _deleteEngine(_engines[index]),
                    deleteDialogTitle: localizations.deleteEngine,
                    children: <Widget>[
                      Text('${localizations.horsePower}: ${_engines[index].horsePower} HP'),
                    ],
                  ),
                );
              },
            ),
    );
  }
}
