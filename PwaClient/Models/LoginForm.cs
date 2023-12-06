using System.ComponentModel.DataAnnotations;

public class LoginForm
{
    [Required]
    [MaxLength(255)]
    public string Username { get; set; } = "";

    [Required]
    [MaxLength(255)]
    public string Password { get; set; } = "";
}
