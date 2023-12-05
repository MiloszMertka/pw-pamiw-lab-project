public class CarForm
{
    public string Name { get; set; } = "";
    public string Color { get; set; } = "";
    public long EngineId { get; set; }
    public long[] EquipmentOptionsIds { get; set; } = new long[] { };
}
