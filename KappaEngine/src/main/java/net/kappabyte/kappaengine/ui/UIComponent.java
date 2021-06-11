package net.kappabyte.kappaengine.ui;

import net.kappabyte.kappaengine.scenes.Transform;

public class UIComponent {
    public UIObject uiObject;

    public UIObject getUIObject() {
        return uiObject;
    }

    public Transform getTransform() {
        return uiObject.getTransform();
    }

    public void onStart() {}
    public void onUpdate() {}
    public void onDestroy() {}
}
