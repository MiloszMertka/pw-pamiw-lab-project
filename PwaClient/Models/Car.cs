public class Car
{
    public long? Id { get; set; }
    public string Name { get; set; }
    public string Color { get; set; }
    public Engine Engine { get; set; }
    public List<EquipmentOption> EquipmentOptions { get; set; }

    public Car(string name, string color, Engine engine, List<EquipmentOption> equipmentOptions)
    {
        Name = name;
        Color = color;
        Engine = engine;
        EquipmentOptions = equipmentOptions;
    }
}
