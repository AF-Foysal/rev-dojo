package dev.affoysal.backend.Event;

import java.util.Map;

import dev.affoysal.backend.Entity.UserEntity;
import dev.affoysal.backend.Enumeration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {

    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;

}
