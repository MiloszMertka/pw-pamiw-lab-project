public class Engine
{
    public long? Id { get; set; }
    public string Name { get; set; }
    public int HorsePower { get; set; }

    public Engine(string name, int horsePower)
    {
        Name = name;
        HorsePower = horsePower;
    }
}
