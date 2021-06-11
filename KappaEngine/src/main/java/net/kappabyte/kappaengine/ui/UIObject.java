package net.kappabyte.kappaengine.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.scenes.Parent;
import net.kappabyte.kappaengine.scenes.Transform;
import net.kappabyte.kappaengine.util.ReflectionUtil;

public abstract class UIObject implements Parent<UIObject> {
    private String name;

    private Transform transform;
    private ArrayList<UIComponent> objectUIComponents = new ArrayList<>();
    private ArrayList<UIObject> children = new ArrayList<>();

    private Parent<UIObject> parent;

    public UIObject(String name) {
        this.name = name;

        transform = new Transform(new Vector3f(), new Vector3f(), new Vector3f(1,1,1));
        try {
            ReflectionUtil.setPrivateFieldValue(UIComponent.class.getDeclaredField("UIObject"), transform, this);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public Transform getTransform() {
        return transform;
    }

    public String getName() {
        return name;
    }

    public final <T extends UIComponent> T GetUIComponent(Class<T> clazz) {
        for(UIComponent UIComponent : objectUIComponents) {
            if(clazz.isInstance(UIComponent)) {
                return clazz.cast(UIComponent);
            }
        }

        return null;
    }

    public final <T extends UIComponent> Collection<T> GetUIComponents(Class<T> clazz) {
        List<T> valid = new ArrayList<>();
        for(UIComponent UIComponent : objectUIComponents) {
            if(clazz.isInstance(UIComponent)) {
                valid.add(clazz.cast(UIComponent));
            }
        }

        return valid;
    }

    public final <T extends UIComponent> Collection<T> GetUIComponentsInChildren(Class<T> clazz) {
        List<T> valid = new ArrayList<>();
        for(UIComponent UIComponent : objectUIComponents) {
            if(clazz.isInstance(UIComponent)) {
                valid.add(clazz.cast(UIComponent));
            }
        }

        for(UIObject object : children) {
            valid.addAll(object.GetUIComponentsInChildren(clazz));
        }

        return valid;
    }

    public final Canvas getCanvas() {
        if(parent == null) return null;

        if(parent instanceof Canvas) {
            return (Canvas) parent;
        }

        if(parent instanceof UIObject) {
            return ((UIObject) parent).getCanvas();
        }

        return null;
    }

    public boolean addUIComponent(UIComponent e) {
        try {
            ReflectionUtil.setPrivateFieldValue(UIComponent.class.getDeclaredField("UIObject"), e, this);
        } catch (NoSuchFieldException | SecurityException e1) {
            e1.printStackTrace();
        }
        e.onStart();
        return objectUIComponents.add(e);
    }

    public boolean removeUIComponent(Object o) {
        if(o instanceof UIComponent) {
            ((UIComponent)o).onDestroy();
            try {
                ReflectionUtil.setPrivateFieldValue(UIComponent.class.getDeclaredField("UIObject"), o, null);
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
        return objectUIComponents.remove(o);
    }

    public void setParent(Parent<UIObject> parent) {
        if(this.parent != null) {
            if(parent instanceof UIObject) {
                ((UIObject) this.parent).children.remove(this);
            } else if(parent instanceof Canvas) {
                getCanvas().uiObjects.remove(this);
            }
        }

        this.parent = parent;
        if(parent instanceof UIObject) {
            ((UIObject) parent).children.add(this);
        } else if(parent instanceof Canvas) {
            ((Canvas)parent).uiObjects.add(this);
        }
    }

    public void addChild(UIObject child) {
        child.setParent(this);
    }

    public Parent<UIObject> getParent() {
        return parent;
    }

    public UIObject getParentUIObject() {
        if(parent instanceof UIObject) {
            return (UIObject) parent;
        }

        return null;
    }

    @Override
    public UIObject[] getChildren() {
        UIObject[] children = new UIObject[this.children.size()];
        children = this.children.toArray(children);

        return children;
    }
}
