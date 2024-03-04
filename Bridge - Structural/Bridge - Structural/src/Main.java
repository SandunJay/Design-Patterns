public class Main {
    public static void main(String[] args) {
        TV lgtv = new LGTV();
        TV sonyTv = new SonyTV();


// Here instead of invoking the method in IMPL layer directly, we can use the interface

        new RemoteControllerImpl(lgtv).on();
        new RemoteControllerImpl(lgtv).off();
        new RemoteControllerImpl(lgtv).tune(10);
        new RemoteControllerImpl(sonyTv).on();
        new RemoteControllerImpl(sonyTv).off();
        new RemoteControllerImpl(sonyTv).tune(20);
    }
}