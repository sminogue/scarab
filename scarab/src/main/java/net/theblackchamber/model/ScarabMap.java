package net.theblackchamber.model;

import net.theblackchamber.model.auto._ScarabMap;

public class ScarabMap extends _ScarabMap {

    private static ScarabMap instance;

    private ScarabMap() {}

    public static ScarabMap getInstance() {
        if(instance == null) {
            instance = new ScarabMap();
        }

        return instance;
    }
}
