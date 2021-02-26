package backend.threads;

import backend.controllers.ClientController;

public class ServerAllocation implements Runnable {


    public ServerAllocation() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
//                clientController.licensesVerificationWithServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
