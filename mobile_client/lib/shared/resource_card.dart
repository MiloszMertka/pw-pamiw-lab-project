import 'package:flutter/material.dart';
import 'package:mobile_client/shared/delete_dialog.dart';

class ResourceCard extends StatelessWidget {
  final String title;
  final VoidCallback onEdit;
  final VoidCallback onDelete;
  final String deleteDialogTitle;
  final List<Widget> children;

  const ResourceCard({
    super.key,
    required this.title,
    required this.onEdit,
    required this.onDelete,
    required this.deleteDialogTitle,
    required this.children,
  });

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;

    return Card(
      child: Column(
        children: <Widget>[
          ListTile(
            title: Text(title),
            subtitle: Column(crossAxisAlignment: CrossAxisAlignment.start, children: children),
          ),
          Row(
            children: <Widget>[
              TextButton(
                onPressed: onEdit,
                child: const Text('EDIT'),
              ),
              const SizedBox(width: 8),
              TextButton(
                style: TextButton.styleFrom(foregroundColor: colorScheme.error),
                onPressed: () async {
                  if (!await showDeleteDialog(context, deleteDialogTitle)) {
                    return;
                  }

                  onDelete();
                },
                child: const Text('DELETE'),
              )
            ],
          ),
        ],
      ),
    );
  }
}