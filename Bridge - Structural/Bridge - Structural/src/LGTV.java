public class LGTV implements TV{
    @Override
    public void on() {
        System.out.println("Turning off LG TV");
    }

    @Override
    public void off() {
        System.out.println("Turning off LG TV");
    }

    @Override
    public void tune(int channel) {
        System.out.println("Turning on channel " + channel);
    }

}
