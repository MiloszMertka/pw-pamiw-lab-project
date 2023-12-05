public class EquipmentOption
{
    public long? Id { get; set; }
    public string Name { get; set; }

    public EquipmentOption(string name)
    {
        Name = name;
    }
}
