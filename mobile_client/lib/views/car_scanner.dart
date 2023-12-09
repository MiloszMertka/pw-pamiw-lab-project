import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:mobile_client/models/car.dart';
import 'package:mobile_client/shared/app_scaffold.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:mobile_client/shared/resource_card.dart';
import 'package:mobile_scanner/mobile_scanner.dart';

class CarScanner extends StatefulWidget {
  const CarScanner({super.key});

  @override
  State<CarScanner> createState() => _CarScannerState();
}

class _CarScannerState extends State<CarScanner> {
  Car? _car;

  @override
  Widget build(BuildContext context) {
    final localizations = AppLocalizations.of(context)!;

    return AppScaffold(
      title: localizations.carScanner,
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 32),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Container(
              padding: const EdgeInsets.symmetric(vertical: 32),
              height: 400,
              width: double.infinity,
              child: MobileScanner(
                onDetect: (capture) {
                  final barcode = capture.barcodes.first;
                  final value = barcode.rawValue;

                  if (value == null) {
                    return;
                  }

                  setState(() {
                    _car = Car.fromJson(json.decode(value));
                  });
                },
              ),
            ),
            _car != null
                ? ResourceCard(
                    readonly: true,
                    title: '${localizations.name}: ${_car!.name}',
                    children: <Widget>[
                      Text('${localizations.color}: ${_car!.color}'),
                      Text('${localizations.engine}: ${_car!.engine.name} ${_car!.engine.horsePower} HP'),
                      Text('${localizations.equipmentOptions}:'),
                      ListBody(
                        children: _car!.equipmentOptions.map((equipmentOption) {
                          return Text('- ${equipmentOption.name}');
                        }).toList(),
                      ),
                    ],
                  )
                : Container(),
          ],
        ),
      ),
    );
  }
}
