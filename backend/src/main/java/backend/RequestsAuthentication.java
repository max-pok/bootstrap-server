package backend;

import backend.controller.RequestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimerTask;

public class RequestsAuthentication implements Runnable {
    RequestController requestController;

    public RequestsAuthentication() {
        requestController = new RequestController();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                requestController.requestVerification();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
