package backend;

import backend.controller.DataController;

public class ServerAllocation implements Runnable {

    private DataController dataController;

    public ServerAllocation() {
        dataController = new DataController();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                dataController.licensesVerificationWithServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
