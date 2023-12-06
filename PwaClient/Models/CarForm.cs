using System.ComponentModel.DataAnnotations;

public class CarForm
{
    [Required]
    [MaxLength(255)]
    public string Name { get; set; } = "";

    [Required]
    [MaxLength(255)]
    public string Color { get; set; } = "";

    [Required]
    public long? EngineId { get; set; } = null;

    [Required]
    public long[] EquipmentOptionsIds { get; set; } = new long[] { };
}
