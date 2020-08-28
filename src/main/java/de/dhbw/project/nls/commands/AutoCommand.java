package de.dhbw.project.nls.commands;

import de.dhbw.project.nls.DataStorage;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AutoCommand extends Command {

    @Override
    public final void setData(DataStorage data) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            PatternName[] patternNames = field.getAnnotationsByType(PatternName.class);
            if (patternNames.length == 0)
                continue;

            String key = patternNames[0].key();

            field.setAccessible(true);
            if (field.getType() == String.class) {
                try {
                    if (data.get(key) == null) {
                        field.set(this, null);
                    } else {
                        field.set(this, data.get(key).get(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (field.getType() == List.class) {
                try {
                    field.set(this, data.get(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
