package com.alexolirib.tasks.constants;

import com.alexolirib.tasks.entities.PriorityEntity;

import java.util.HashMap;
import java.util.List;

public class PriorityCacheConstants {
    private static HashMap<Integer, String> mPriorityCache = new HashMap<>();

    private PriorityCacheConstants() {
    }

    public static void setValues(List<PriorityEntity> values) {
        for(PriorityEntity entity: values){
            mPriorityCache.put(entity.getId(), entity.getDescription());
        }
    }

    public static String get(Integer id) {
        return mPriorityCache.get(id);
    }
}
