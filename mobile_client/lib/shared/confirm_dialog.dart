import 'package:flutter/material.dart';

Future<bool> showConfirmDialog({
  required BuildContext context,
  required String title,
  required String message,
  String cancelButtonLabel = 'CANCEL',
  String confirmButtonLabel = 'CONFIRM',
  bool useDangerColor = false,
}) async {
  return await showDialog<bool>(
        context: context,
        builder: (BuildContext context) {
          return ConfirmDialog(
            title: title,
            message: message,
            cancelButtonLabel: cancelButtonLabel,
            confirmButtonLabel: confirmButtonLabel,
            useDangerColor: useDangerColor,
          );
        },
      ) ??
      false;
}

class ConfirmDialog extends StatelessWidget {
  final String title;
  final String message;
  final String cancelButtonLabel;
  final String confirmButtonLabel;
  final bool useDangerColor;

  const ConfirmDialog({
    Key? key,
    required this.title,
    required this.message,
    this.cancelButtonLabel = 'CANCEL',
    this.confirmButtonLabel = 'CONFIRM',
    this.useDangerColor = false,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;

    return AlertDialog(
      title: Text(title),
      content: Text(message),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context, false),
          child: Text(cancelButtonLabel),
        ),
        TextButton(
          onPressed: () => Navigator.pop(context, true),
          style: TextButton.styleFrom(
            foregroundColor: useDangerColor ? colorScheme.error : colorScheme.primary,
          ),
          child: Text(confirmButtonLabel),
        ),
      ],
    );
  }
}
