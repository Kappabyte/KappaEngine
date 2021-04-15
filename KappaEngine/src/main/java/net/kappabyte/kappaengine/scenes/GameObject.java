package net.kappabyte.kappaengine.scenes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.util.Log;

public class GameObject implements Collection<Component> {
    private Transform transform;
    private ArrayList<Component> objectComponents = new ArrayList<>();

    private Scene scene;

    public GameObject() {
        transform = new Transform(new Vector3f(), new Vector3f(), new Vector3f(1,1,1));
    }

    public Transform getTransform() {
        return transform;
    }

    public final <T extends Component> T GetComponent(Class<T> clazz) {
        for(Component component : objectComponents) {
            if(clazz.isInstance(component)) {
                return clazz.cast(component);
            }
        }

        return null;
    }

    public final <T extends Component> Collection<T> GetComponents(Class<T> clazz) {
        List<T> valid = new ArrayList<>();
        for(Component component : objectComponents) {
            if(clazz.isInstance(component)) {
                valid.add(clazz.cast(component));
            }
        }

        return valid;
    }

    public final Scene getScene() {
        return scene;
    }

    @Override
    public int size() {
        return objectComponents.size();
    }

    @Override
    public boolean isEmpty() {
        return objectComponents.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return objectComponents.contains(o);
    }

    @Override
    public Iterator<Component> iterator() {
        return objectComponents.iterator();
    }

    @Override
    public Object[] toArray() {
        return objectComponents.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return objectComponents.toArray(a);
    }

    @Override
    public boolean add(Component e) {
        setComponentGameObject(e, this);
        e.onStart();
        return objectComponents.add(e);
    }

    @Override
    public boolean remove(Object o) {
        if(o instanceof Component) {
            ((Component)o).onDestroy();
            setComponentGameObject((Component) o, null);
        }
        return objectComponents.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return objectComponents.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Component> c) {
        for(Component component : c) {
            setComponentGameObject(component, this);
            component.onStart();
        }
        return objectComponents.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for(Object o : c) {
            if(o instanceof Component) {
                ((Component)o).onDestroy();
                setComponentGameObject((Component) o, null);
            }
        }
        return objectComponents.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for(Component component : objectComponents) {
            if(!c.contains(component)) {
                component.onDestroy();
                setComponentGameObject(component, null);
            }
        }
        return objectComponents.retainAll(c);
    }

    @Override
    public void clear() {
        for(Component component : objectComponents) {
            component.onDestroy();
            setComponentGameObject(component, null);
        }
        objectComponents.clear();
    }
    
    private void setComponentGameObject(Component component, GameObject gameObject) {
        try {
            Field field = Component.class.getDeclaredField("gameObject");
            field.setAccessible(true);
            field.set(component, gameObject);
            Log.debug("Added component " + component + " to gameObect " + gameObject);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            Log.error("failed to set gameObject for component!");
            e.printStackTrace();
        }
    }
}
