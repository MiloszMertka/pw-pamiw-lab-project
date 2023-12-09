import 'package:flutter/material.dart';
import 'package:mobile_client/shared/delete_dialog.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class ResourceCard extends StatelessWidget {
  final String title;
  final VoidCallback? onEdit;
  final VoidCallback? onDelete;
  final String? deleteDialogTitle;
  final List<Widget> children;
  final bool readonly;

  const ResourceCard({
    super.key,
    required this.title,
    required this.children,
    this.onEdit,
    this.onDelete,
    this.deleteDialogTitle,
    this.readonly = false,
  });

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;
    final localizations = AppLocalizations.of(context)!;

    return Card(
      child: Column(
        children: <Widget>[
          ListTile(
            title: Text(title),
            subtitle: Column(crossAxisAlignment: CrossAxisAlignment.start, children: children),
          ),
          readonly
              ? Container()
              : Row(
                  children: <Widget>[
                    TextButton(
                      onPressed: onEdit,
                      child: Text(localizations.edit.toUpperCase()),
                    ),
                    const SizedBox(width: 8),
                    TextButton(
                      style: TextButton.styleFrom(foregroundColor: colorScheme.error),
                      onPressed: () async {
                        if (!await showDeleteDialog(context, deleteDialogTitle!)) {
                          return;
                        }

                        onDelete!();
                      },
                      child: Text(localizations.delete.toUpperCase()),
                    )
                  ],
                ),
        ],
      ),
    );
  }
}
