package com.elfstack.toys.security.dev;

import com.elfstack.toys.security.AppUserInfo;
import com.elfstack.toys.security.domain.UserId;
import org.jspecify.annotations.Nullable;

import java.time.ZoneId;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link AppUserInfo} used by {@link DevUser} for development environments.
 * <p>
 * This record provides a simple immutable implementation of the {@link AppUserInfo} interface, storing user information
 * for development and testing purposes. It contains all the essential user profile data needed by the application, with
 * appropriate null checks for required fields.
 * </p>
 * <p>
 * This implementation is specifically designed for development and test environments and should not be used in
 * production. It's primarily used by the {@link DevUser} class to represent test user information.
 * </p>
 *
 * @param userId            the unique identifier for the user (never {@code null})
 * @param preferredUsername the user's preferred username (never {@code null}).
 * @param fullName          the user's full name (never {@code null})
 * @param profileUrl        the URL to the user's profile page, or {@code null} if not available
 * @param pictureUrl        the URL to the user's profile picture, or {@code null} if not available
 * @param email             the user's email address, or {@code null} if not available
 * @param zoneId            the user's time zone (never {@code null})
 * @param locale            the user's locale (never {@code null})
 * @see DevUser The development user class that uses this record
 * @see AppUserInfo The interface this record implements
 */
class DevUserInfo implements AppUserInfo {
    private UserId userId;
    private String preferredUsername;
    private String fullName;
    private String profileUrl;
    private String pictureUrl;
    private String email;
    private ZoneId zoneId;
    private Locale locale;

    public DevUserInfo(UserId userId, String preferredUsername, String fullName, String profileUrl, String pictureUrl, String email, ZoneId zoneId, Locale locale) {
        this.userId = userId;
        this.preferredUsername = preferredUsername;
        this.fullName = fullName;
        this.profileUrl = profileUrl;
        this.pictureUrl = pictureUrl;
        this.email = email;
        this.zoneId = zoneId;
        this.locale = locale;
    }

/*   DevUserInfo {
        requireNonNull(userId);
        requireNonNull(preferredUsername);
        requireNonNull(fullName);
        requireNonNull(zoneId);
        requireNonNull(locale);
    }*/

    @Override
    public UserId getUserId() {
        return userId;
    }

    @Override
    public String getPreferredUsername() {
        return preferredUsername;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public @Nullable String getProfileUrl() {
        return profileUrl;
    }

    @Override
    public @Nullable String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public @Nullable String getEmail() {
        return email;
    }

    @Override
    public ZoneId getZoneId() {
        return zoneId;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
