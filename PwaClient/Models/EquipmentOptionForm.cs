using System.ComponentModel.DataAnnotations;

public class EquipmentOptionForm
{
    [Required]
    [MaxLength(255)]
    public string Name { get; set; } = "";
}
