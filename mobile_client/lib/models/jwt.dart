class Jwt {
  final String token;

  Jwt({required this.token});

  factory Jwt.fromJson(Map<String, dynamic> json) => Jwt(
        token: json["token"],
      );

  Map<String, dynamic> toJson() => {
        "token": token,
      };
}
