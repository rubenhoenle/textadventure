package de.dhbw.project.nls.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PatternName {
    public String key() default "";
}
