import 'package:flutter/material.dart';
import 'package:mobile_client/shared/confirm_dialog.dart';

Future<bool> showDeleteDialog(BuildContext context, String title) async {
  return await showConfirmDialog(
    context: context,
    title: title,
    message: 'Do you really want to delete this record? This process cannot be undone.',
    useDangerColor: true,
    confirmButtonLabel: 'DELETE',
  );
}
