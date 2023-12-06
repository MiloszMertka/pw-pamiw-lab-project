using System.ComponentModel.DataAnnotations;

public class RegisterForm
{
    [Required]
    [MaxLength(255)]
    public string Username { get; set; } = "";

    [Required]
    [MaxLength(255)]
    public string Password { get; set; } = "";

    [Required]
    [MaxLength(255)]
    [Compare("Password")]
    public string PasswordConfirmation { get; set; } = "";
}
