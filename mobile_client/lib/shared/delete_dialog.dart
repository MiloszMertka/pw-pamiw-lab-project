import 'package:flutter/material.dart';
import 'package:mobile_client/shared/confirm_dialog.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

Future<bool> showDeleteDialog(BuildContext context, String title) async {
  final localizations = AppLocalizations.of(context)!;

  return await showConfirmDialog(
    context: context,
    title: title,
    message: 'Do you really want to delete this record? This process cannot be undone.',
    useDangerColor: true,
    cancelButtonLabel: localizations.cancel.toUpperCase(),
    confirmButtonLabel: localizations.delete.toUpperCase(),
  );
}
