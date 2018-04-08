package org.birdback.histudents.utils;

import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * Created by meixin.song on 2018/3/9.
 */

public class ReflectUtil {
    public static <T> T getTypeInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            Log.w("HI_STUDENTS" ,o.getClass().getSimpleName() + " Can't new instance " + (i == 0 ? "Presenter" : "Model") + ", Because unused MVP\n" + e.getMessage() + "\n" + e.getStackTrace()[0] + "\n" + e.getStackTrace()[1] + "\n" + o.getClass() + " not Generic Type");
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
