package net.kappabyte.kappaengine.scenes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.scenes.components.Camera;
import net.kappabyte.kappaengine.util.Log;

public class Scene implements Collection<GameObject> {

    private Camera camera;

    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    public final <T extends Component> Collection<T> GetComponents(Class<T> clazz) {
        List<T> components = new ArrayList<>();
        for (GameObject object : gameObjects) {
            components.addAll(object.GetComponents(clazz));
        }

        return components;
    }

    public final void setActiveCamera(Camera camera) {
        this.camera = camera;
    }

    public final Camera getActiveCamera() {
        return camera;
    }

    @Override
    public int size() {
        return gameObjects.size();
    }

    @Override
    public boolean isEmpty() {
        return gameObjects.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return gameObjects.contains(o);
    }

    @Override
    public Iterator<GameObject> iterator() {
        return gameObjects.iterator();
    }

    @Override
    public Object[] toArray() {
        return gameObjects.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return gameObjects.toArray(a);
    }

    @Override
    public boolean add(GameObject e) {
        Log.info("-1");
        setGameObjectScene(e, this);
        return gameObjects.add(e);
    }

    @Override
    public boolean remove(Object o) {
        setGameObjectScene((GameObject) o, null);

        return gameObjects.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return gameObjects.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends GameObject> c) {
        for (Object o : c) {
            if (o instanceof GameObject) {
                setGameObjectScene((GameObject) o, this);
            }
        }
        return gameObjects.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (o instanceof GameObject) {
                setGameObjectScene((GameObject) o, null);
            }
        }
        return gameObjects.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (Object o : c) {
            if (o instanceof GameObject && !gameObjects.contains(o)) {
                setGameObjectScene((GameObject) o, null);
            }
        }
        return gameObjects.retainAll(c);
    }

    @Override
    public void clear() {
        for (GameObject gameObject : gameObjects) {
            setGameObjectScene(gameObject, null);
        }
        gameObjects.clear();
    }

    private void setGameObjectScene(GameObject gameObject, Scene scene) {
        try {
            Field field = gameObject.getClass().getDeclaredField("scene");
            field.setAccessible(true);
            field.set(gameObject, scene);
            Log.debug("Added game object" + gameObject + " to scene " + scene);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            Log.error("failed to set scene for gameObject!");
            e.printStackTrace();
        }
    }
}
