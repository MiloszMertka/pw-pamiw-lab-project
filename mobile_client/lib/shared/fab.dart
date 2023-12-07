import 'package:flutter/material.dart';

class Fab extends StatelessWidget {
  final VoidCallback onPressed;

  const Fab({super.key, required this.onPressed});

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      onPressed: onPressed,
      child: const Icon(Icons.add),
    );
  }
}
