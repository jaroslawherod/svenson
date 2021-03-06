package org.svenson.info;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.svenson.info.methodhandle.MethodHandleAccessFactory;
import org.svenson.info.reflectasm.ReflectasmAccessFactory;
import org.svenson.info.reflection.ReflectionAccessFactory;
import org.svenson.test.FunkyNonProperties;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class JavaObjectSupportTestCase {
    private final static Logger log = LoggerFactory.getLogger(JavaObjectSupportTestCase.class);
    private final JavaObjectSupport support;
    final JSONClassInfo classInfo;
    final TestObject object = new TestObject();


    public JavaObjectSupportTestCase(AccessFactory factory) {
        support = new JavaObjectSupport(factory);
        classInfo = support.createClassInfo(TestObject.class);
    }


    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(
                new Object[]{new MethodHandleAccessFactory()},
                new Object[]{new ReflectasmAccessFactory()},
                new Object[]{new ReflectionAccessFactory()}
        );
    }

    @Test
    public void shouldHandlePlainProperty() {

        final JSONPropertyInfo plain = classInfo.getPropertyInfo("plain");
        plain.setProperty(object, "value");

        assertThat(object.getPlain(), is("value"));
        assertThat(plain.getProperty(object), is((Object) "value"));
    }

    @Test
    public void shouldSkipStaticProperty() {

        final JSONPropertyInfo staticProp = classInfo.getPropertyInfo("staticProp");

        assertThat(staticProp, nullValue());
    }

    @Test
    public void shouldSkipClassProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("class");

        assertThat(property, nullValue());
    }

    @Test
    public void shouldHandleGenericProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("generic");
        property.setProperty(object, "value");


        assertThat(object.getGeneric(), is("value"));
        assertThat(property.getProperty(object), is((Object) "value"));
    }


    @Test
    public void shouldHandleOverrideProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("override");
        property.setProperty(object, "value");

        assertThat(object.getOverride(), is("value"));
        assertThat(property.getProperty(object), is((Object) "value"));
    }

    @Test
    public void shouldHandleOverloadProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("overload");
        property.setProperty(object, "value");

        assertThat(object.getOverload(), is("value"));
        assertThat(property.getProperty(object), is((Object) "value"));
    }

    @Test
    public void shouldHandleBooleanProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("switcher");
        property.setProperty(object, true);

        assertThat(object.isSwitcher(), is(true));
        assertThat(property.getProperty(object), is((Object) true));
    }

    @Test
    public void shouldHandleOverrideBooleanProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("overrideSwitcher");
        property.setProperty(object, true);

        assertThat(object.isOverrideSwitcher(), is(true));
        assertThat(property.getProperty(object), is((Object) true));
    }

    @Test
    public void shouldHandlePlainAddableProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("plainValues");
        assertThat(property.canAdd(), is(true));
        property.add(object, "new value");


        assertThat(object.getPlainValues(), hasItem("new value"));
        assertThat((Iterable<String>) property.getProperty(object), hasItem("new value"));
    }

    @Test
    public void shouldHandleOverloadAddableProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("overloadPlainValues");
        assertThat(property.canAdd(), is(true));
        property.add(object, "new value");


        assertThat(object.getOverloadPlainValues(), hasItem("new value"));
        assertThat((Iterable<String>) property.getProperty(object), hasItem("new value"));
    }

    @Test
    public void shouldHandleOverriddenPlainAddableProperty() {

        final JSONPropertyInfo property = classInfo.getPropertyInfo("overridePlainValues");
        assertThat(property.canAdd(), is(true));
        property.add(object, "new value");


        assertThat(object.getOverridePlainValues(), hasItem("new value"));
        assertThat((Iterable<String>) property.getProperty(object), hasItem("new value"));
    }


    @Test
    public void testFunkyNonProps() {
        final JSONClassInfo classInfo = support.createClassInfo(FunkyNonProperties.class);
        assertThat(classInfo.getPropertyNames().size(), is(0));
    }
}
