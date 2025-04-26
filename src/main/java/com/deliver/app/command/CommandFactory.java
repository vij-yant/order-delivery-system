package com.deliver.app.command;

public class CommandFactory {
    private static final ThreadLocal<PickUpCommand> pickUpCommandThreadLocal =
            ThreadLocal.withInitial(() -> null);

    private static final ThreadLocal<DeliverCommand> deliverCommandThreadLocal =
            ThreadLocal.withInitial(() -> null);

    public static PickUpCommand getPickUpCommand() {
        PickUpCommand cmd = pickUpCommandThreadLocal.get();
        if (cmd == null) {
            cmd = new PickUpCommand();
            pickUpCommandThreadLocal.set(cmd);
        }
        return cmd;
    }

    public static DeliverCommand getDeliverCommand() {
        DeliverCommand cmd = deliverCommandThreadLocal.get();
        if (cmd == null) {
            cmd = new DeliverCommand();
            deliverCommandThreadLocal.set(cmd);
        }
        return cmd;
    }

    public static void clear() {
        pickUpCommandThreadLocal.remove();
        deliverCommandThreadLocal.remove();
    }
}
