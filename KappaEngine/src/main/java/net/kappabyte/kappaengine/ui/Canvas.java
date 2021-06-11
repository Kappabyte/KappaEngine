package net.kappabyte.kappaengine.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.kappabyte.kappaengine.scenes.Parent;

public class Canvas implements Parent<UIObject> {
    public ArrayList<UIObject> uiObjects = new ArrayList<>();

    public final <T extends UIComponent> Collection<T> GetUIComponents(Class<T> clazz) {
        List<T> components = new ArrayList<>();
        for (UIObject object : uiObjects) {
            components.addAll(object.GetUIComponentsInChildren(clazz));
        }

        return components;
    }

    @Override
    public UIObject[] getChildren() {
        UIObject[] objects = new UIObject[uiObjects.size()];

        objects = uiObjects.toArray(objects);

        return objects;
    }
}
