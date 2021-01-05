package hanze.nl.tijdtools;

public interface ITijdFuncties {

    void initSimulatorTijden(int interval, int syncInterval);
    String getSimulatorWeergaveTijd();
    int getCounter();
    int getTijdCounter();
    void simulatorStep() throws InterruptedException;
}
