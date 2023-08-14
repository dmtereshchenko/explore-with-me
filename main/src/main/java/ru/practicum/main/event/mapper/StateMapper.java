package ru.practicum.main.event.mapper;

import ru.practicum.Constant;

public class StateMapper {

    public static Constant.State toState(Constant.StateAction stateAction) {
        if (stateAction.equals(Constant.StateAction.SEND_TO_REVIEW)) {
            return Constant.State.PENDING;
        } else if (stateAction.equals(Constant.StateAction.PUBLISH_EVENT)) {
            return Constant.State.PUBLISHED;
        } else {
            return Constant.State.CANCELED;
        }
    }
}
