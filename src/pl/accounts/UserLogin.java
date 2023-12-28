package pl.accounts;

import pl.*;
import bll.*;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.*;
import raven.swing.*;

public class UserLogin extends SimpleDrawerBuilder {

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/pl/icons/profile.png"), 60, 60, 999))
                .setTitle(BusinessLogic.getInstance().getUserName(UserPanel.getInstance().username));
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        String[][] menu = {
            {"~Product~"},
            {"Products"},
            {"~Cart~"},
            {"Cart"},
            {"~Order~"},
            {"Orders"}
        };
        String[] icons = {
            "product.svg", "cart.svg", "order.svg"
        };
        return new SimpleMenuOption()
                .setMenus(menu)
                .setIcons(icons)
                .setBaseIconPath("pl/icons")
                .setIconScale(0.45f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int menu, int subMenu) {
                        UserPanel.getInstance().menuActions(menu, subMenu);
                    }
                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData();
    }

}
