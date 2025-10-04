package dev.affoysal.backend.Event.Listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.affoysal.backend.Enumeration.EventType;
import dev.affoysal.backend.Event.UserEvent;
import dev.affoysal.backend.Service.EmailService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final EmailService emailService;

    @EventListener
    public void onUserEvent(UserEvent event) {
        String fullName = event.getUser().getFirstName() + " " + event.getUser().getLastName();
        switch (event.getType()) {
            case EventType.REGISTRATION -> emailService.sendNewAccountEmail(fullName, event.getUser().getEmail(),
                    (String) event.getData().get("token"));
            case EventType.RESET_PASSWORD -> emailService.sendResetPasswordEmail(fullName, event.getUser().getEmail(),
                    (String) event.getData().get("token"));
            default -> {
            }
        }
    }
}
