class UserRegister {
  final String username;
  final String password;

  UserRegister({required this.username, required this.password});

  Map<String, dynamic> toJson() => {
        "username": username,
        "password": password,
      };
}
