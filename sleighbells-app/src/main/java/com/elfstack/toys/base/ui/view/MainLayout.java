package com.elfstack.toys.base.ui.view;

import com.elfstack.toys.security.CurrentUser;
import com.elfstack.toys.security.dev.DevUser;
import com.elfstack.toys.taskmanagement.ui.view.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Layout
@PermitAll // When security is enabled, allow all authenticated users
public final class MainLayout extends AppLayout {

    private final CurrentUser currentUser;
    private final AuthenticationContext authenticationContext;

    MainLayout(CurrentUser currentUser, AuthenticationContext authenticationContext) {
        this.currentUser = currentUser;
        this.authenticationContext = authenticationContext;
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), new Scroller(naySideNav()), createUserMenu());
    }

    private Div createHeader() {
        // TODO Replace with real application logo and name
        var appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("Sleighbells App");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        return header;
    }

    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.addClassNames(Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNav naySideNav(){
            SideNav nav = new SideNav();
             SideNavItem messagesLink = new SideNavItem("Messages",
                    MessagesView.class, VaadinIcon.ENVELOPE.create());

            messagesLink.addItem(new SideNavItem("Inbox", InboxView.class,
                    VaadinIcon.INBOX.create()));
         /*   Arrays.asList("step1", "viw2", "div3").forEach(
                    x-> messagesLink.addItem(new SideNavItem())
            );
*/
    /*    messagesLink.addItem(new SideNavItem("Product A", MessExtView.class,
                new RouteParameters("productId", "product-a-123")));

        messagesLink.addItem( new SideNavItem("Product B", MessExtView.class,
                new RouteParameters("productId", "product-b-456")));*/
            messagesLink.addItem(new SideNavItem("Sent", SentView.class,
                    VaadinIcon.PAPERPLANE.create()));
            messagesLink.addItem(new SideNavItem("Trash", TrashView.class,
                    VaadinIcon.TRASH.create()));

         /*   for(String prm: Arrays.asList("a1", "b2", "c3")){
                messagesLink.addItem(new SideNavItem(prm, MessExtView.class, new RouteParameters("prm", prm.toUpperCase()),
                        VaadinIcon.TRASH.create()));
            }
*/
            SideNavItem adminSection = new SideNavItem("Admin");
            adminSection.setPrefixComponent(VaadinIcon.COG.create());
            adminSection.addItem(new SideNavItem("Users", UsersView.class,
                    VaadinIcon.GROUP.create()));
            adminSection.addItem(new SideNavItem("Permissions",
                    PermissionsView.class, VaadinIcon.KEY.create()));
        adminSection.addItem(new SideNavItem("Люди",
                PersonListView.class, VaadinIcon.MALE.create()));
        nav.addItem(messagesLink);
        log.info("verify MainLayout");
        log.info(String.valueOf(currentUser.requirePrincipal().getAuthorities().stream().count()));

        currentUser.requirePrincipal().getAuthorities().forEach(x-> log.info(x.getAuthority()));

        if (currentUser.requirePrincipal().getAuthorities().stream().filter(x->x.getAuthority().equals("ROLE_ADMIN")).count() > 0)
             nav.addItem(adminSection);
           return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }

    private Component createUserMenu() {
        var user = currentUser.require();

        var avatar = new Avatar(user.getFullName(), user.getPictureUrl());
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.setColorIndex(5);

        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames(Margin.MEDIUM);

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add(user.getFullName());
        if (user.getProfileUrl() != null) {
            userMenuItem.getSubMenu().addItem("View Profile",
                    event -> UI.getCurrent().getPage().open(user.getProfileUrl()));
        }
        // TODO Add additional items to the user menu if needed
        userMenuItem.getSubMenu().addItem("Logout", event -> authenticationContext.logout());

        return userMenu;
    }

    private static final Logger log = LoggerFactory.getLogger(MainLayout.class);

}
