package ru.korniltsev.telegram.core.mortar.mortarscreen;

import mortar.MortarScope;
import ru.korniltsev.telegram.core.mortar.mortarscreen.ModuleFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a screen as defining a {@link MortarScope}, with a factory class to
 * create its Dagger module.
 *
 * @see WithModule
 * @see ScreenScoper
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface WithModuleFactory {
  Class<? extends ModuleFactory> value();
}
