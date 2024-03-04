public class SonyTV implements TV{
    @Override
    public void on() {
        System.out.println("Turning on Sony TV");
    }

    @Override
    public void off() {
        System.out.println("Turning off Sony TV");
    }

    @Override
    public void tune(int channel) {
        System.out.println("Turning on channel " + channel);
    }
}
