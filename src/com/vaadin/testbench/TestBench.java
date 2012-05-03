package com.vaadin.testbench;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vaadin.testbench.commands.TestBenchCommandExecutor;

/**
 */
public class TestBench {

    public static WebDriver createDriver(WebDriver driver) {
        Set<Class<?>> allInterfaces = extractInterfaces(driver);
        allInterfaces.addAll(extractInterfaces(TestBenchDriver.class));
        final Class<?>[] allInterfacesArray = allInterfaces
                .toArray(new Class<?>[allInterfaces.size()]);
        Object proxy = Proxy
                .newProxyInstance(driver.getClass().getClassLoader(),
                        allInterfacesArray, new CachedInvocationHandler(
                                new TestBenchDriver(driver), driver));
        return (WebDriver) proxy;
    }

    public static WebElement createElement(WebElement webElement,
            TestBenchCommandExecutor tbCommandExecutor) {
        Set<Class<?>> allInterfaces = extractInterfaces(webElement);
        allInterfaces.addAll(extractInterfaces(TestBenchElement.class));
        final Class<?>[] allInterfacesArray = allInterfaces
                .toArray(new Class<?>[allInterfaces.size()]);
        Object proxy = Proxy.newProxyInstance(webElement.getClass()
                .getClassLoader(), allInterfacesArray,
                new CachedInvocationHandler(new TestBenchElement(webElement,
                        tbCommandExecutor), webElement));
        return (WebElement) proxy;
    }

    private static Set<Class<?>> extractInterfaces(final Object object) {
        return extractInterfaces(object.getClass());
    }

    private static Set<Class<?>> extractInterfaces(final Class<?> clazz) {
        final Set<Class<?>> allInterfaces = new HashSet<Class<?>>();
        extractInterfaces(allInterfaces, clazz);

        return allInterfaces;
    }

    private static void extractInterfaces(final Set<Class<?>> addTo,
            final Class<?> clazz) {
        if (clazz == null || Object.class.equals(clazz)) {
            return; // Done
        }

        final Class<?>[] classes = clazz.getInterfaces();
        for (final Class<?> interfaceClass : classes) {
            addTo.add(interfaceClass);
            for (final Class<?> superInterface : interfaceClass.getInterfaces()) {
                addTo.add(superInterface);
                extractInterfaces(addTo, superInterface);
            }
        }
        extractInterfaces(addTo, clazz.getSuperclass());
    }
}
