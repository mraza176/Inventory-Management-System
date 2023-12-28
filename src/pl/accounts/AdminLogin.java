package pl.accounts;

import pl.AdminPanel;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.*;
import raven.swing.AvatarIcon;

public class AdminLogin extends SimpleDrawerBuilder {

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/pl/icons/profile.png"), 60, 60, 999))
                .setTitle("   Admin");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        String[][] menu = {
            {"~Product~"},
            {"Product Management", "Add Product", "Update Product", "Delete Product"},
            {"~Category~"},
            {"Categories Management", "Add New Category", "Delete Category"},
            {"~Expenses~"},
            {"Revenue"}
        };
        String[] icons = {
            "product.svg", "category.svg", "expense.svg"
        };
        return new SimpleMenuOption()
                .setMenus(menu)
                .setIcons(icons)
                .setBaseIconPath("pl/icons")
                .setIconScale(0.40f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int menu, int subMenu) {
                        AdminPanel.getInstance().menuActions(menu, subMenu);
                    }
                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData();
    }
}
