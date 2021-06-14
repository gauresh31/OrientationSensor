package com.kt.sensororientation;

import android.content.Context;

public class SensorSingleton {
    private static SensorSingleton instance = null;
    private static Context context;

    /**
     * To initialize the class. It must be called before call the method getInstance()
     *
     * @param ctx The Context used
     */
    public static void initialize(Context ctx) {
        context = ctx;
    }

    /**
     * Check if the class has been initialized
     *
     * @return true  if the class has been initialized
     * false Otherwise
     */
    public static boolean hasBeenInitialized() {
        return context != null;

    }

    /**
     * The private constructor.
     */
    private SensorSingleton() {
        // Use context to initialize the variables.
    }

    public static synchronized SensorSingleton getInstance() {
        if (context == null) {
            throw new IllegalArgumentException("Unable to get the instance. This class must be initialized before");
        }

        if (instance == null) {
            instance = new SensorSingleton();
        }

        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not allowed.");
    }
}
