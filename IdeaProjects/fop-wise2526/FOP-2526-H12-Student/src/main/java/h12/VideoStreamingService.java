package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a video streaming service.
 *
 * @param accounts the user accounts on the service
 * @author Nhan Huynh
 */
@DoNotTouch
public record VideoStreamingService(
    Set<UserAccount> accounts
) {

    /**
     * Creates an empty video streaming service.
     */
    @DoNotTouch
    public VideoStreamingService() {
        this(new HashSet<>());
    }

    /**
     * Adds a user account to the service. If an account with the same user already exists or the streaming service of
     * the user does not match this streaming service, the account is not added.
     *
     * @param account the account to add
     * @return {@code true} if the account was added, {@code false} if an account with the same user already exists or
     * the streaming service of the user does not match this streaming service
     */
    @StudentImplementationRequired("H12.7.1")
    public boolean addUserAccount(UserAccount account) {
        // Önce hızlı kontrol: Streaming service eşleşiyor mu? && User mevcut değil mi? && Ekleme başarılı mı?
        return account.user().streamingService() == this                                    // 1. Streaming service kontrolü (hızlı)
            && !accounts.stream().anyMatch(hesap -> hesap.user().equals(account.user()))  // 2. User zaten mevcut değilse (yavaş)
            && accounts.add(account);
    }

    /**
     * Removes a user account from the service, including all references to the user account.
     * If the account does not exist, nothing happens.
     *
     * @param account the account to remove
     * @return {@code true} if the account was removed, {@code false} if the account did not exist
     */
    @DoNotTouch
    public boolean removeUserAccount(UserAccount account) {
        if (accounts.remove(account)) {
            account.history().clear();
            account.ownedChannels().forEach(channel -> channel.subscribers().remove(account.user()));
            account.subscribedChannels().forEach(channel -> channel.subscribers().remove(account.user()));
            return true;
        }
        return false;
    }

    /**
     * Returns the user account with the given display name.
     *
     * @param displayName the display name of the user
     * @return the user account with the given display name
     * @throws IllegalArgumentException if no user with the given display name exists
     */
    @StudentImplementationRequired("H12.7.2")
    public UserAccount getUserAccountByName(String displayName) {
        return accounts.stream()
            .filter(accountRecord -> accountRecord.user().displayName().equals(displayName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Returns the most active user, i.e., the user with the most videos in their watch history.
     *
     * @return the most active user, or an empty Optional if there are no users
     */
    @StudentImplementationRequired("H12.7.3")
    public Optional<User> getMostActiveUser() {
        return accounts.stream()
            .max(Comparator.comparingInt(account -> account.history().size()))
            .map(UserAccount::user);
    }

    /**
     * Returns the top watched videos across all user accounts, sorted by the number of times they were watched (most
     * watched first).
     *
     * @param count the maximum number of videos to return
     * @return the top watched videos
     */
    @StudentImplementationRequired("H12.7.4")
    public List<Video> getTopWatchedVideos(int count) {
        return accounts.stream()
            .flatMap(accountRecord -> accountRecord.history().videos().stream())
            .map(Map.Entry::getKey)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<Video, Long>comparingByValue().reversed())
            .limit(count)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * Returns all users who have watched at least one video in the given category.
     *
     * @param categoryName the name of the category
     * @return all users who have watched at least one video in the given category
     */
    @StudentImplementationRequired("H12.7.5")
    public Set<User> getUserByCategory(String categoryName) {
        return accounts.stream()
            .filter(accountRecord -> accountRecord.history().videos().stream()
                .map(Map.Entry::getKey)
                .flatMap(videoRecord -> videoRecord.metaData().stream())
                .anyMatch(metaInfo -> metaInfo.category().name().equals(categoryName)))
            .map(UserAccount::user)
            .collect(Collectors.toSet());
    }

    /**
     * Returns the top categories from all playlists across all user accounts, sorted by the number of videos in each
     * category (most videos first).
     *
     * @param count the maximum number of categories to return
     * @return the top categories from all playlists
     */
    @StudentImplementationRequired("H12.7.6")
    public List<Category> getTopCategoriesFromAllPlaylists(int count) {
        return accounts.stream()
            .map(UserAccount::user)
            .flatMap(user -> user.playlists().stream())
            .flatMap(listEntry -> listEntry.videos().stream())
            .flatMap(videoRecord -> videoRecord.metaData().stream())
            .map(VideoMetaData::category)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<Category, Long>comparingByValue().reversed())
            .limit(count)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
