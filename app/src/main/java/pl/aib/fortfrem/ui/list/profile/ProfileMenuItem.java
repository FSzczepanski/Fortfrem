package pl.aib.fortfrem.ui.list.profile;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.navigation.NavAction;

public class ProfileMenuItem {
    public Integer getIconId() {
        return iconId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public enum Type {
        PROFILE,
        INFO,
        LOGOUT
    }

    private Integer iconId;
    private Integer labelId;
    private Type type;

    @Nullable
    private NavAction navAction;

    public ProfileMenuItem(Integer iconId, Integer labelId, Type type) {
        this.iconId = iconId;
        this.labelId = labelId;
        this.type = type;
    }

    @Nullable
    public NavAction getNavAction() {
        return this.navAction;
    }

    public void setNavAction(@Nullable NavAction navAction) {
        this.navAction = navAction;
    }

    public Type getType() {
        return this.type;
    }

}
