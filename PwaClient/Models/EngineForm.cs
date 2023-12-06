using System.ComponentModel.DataAnnotations;

public class EngineForm
{
    [Required]
    [MaxLength(255)]
    public string Name { get; set; } = "";

    [Required]
    [Range(0, long.MaxValue)]
    public int? HorsePower { get; set; } = null;
}
