package com.example.androidpopularlibraries.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @Expose private String login;

    @Expose private Integer id;

    @SerializedName("node_id")
    @Expose private String nodeId;

    @SerializedName("avatar_url")
    @Expose private String avatarUrl;

    @SerializedName("gravatar_id")
    @Expose private String gravatarId;

    @Expose private String url;

    @SerializedName("html_url")
    @Expose private String htmlUrl;

    @SerializedName("followers_url")
    @Expose private String followersUrl;

    @SerializedName("following_url")
    @Expose private String followingUrl;

    @SerializedName("gists_url")
    @Expose private String gistsUrl;

    @SerializedName("starred_url")
    @Expose private String starredUrl;

    @SerializedName("subscriptions_url")
    @Expose private String subscriptionsUrl;

    @SerializedName("organizations_url")
    @Expose private String organizationsUrl;

    @SerializedName("repos_url")
    @Expose private String reposUrl;

    @SerializedName("events_url")
    @Expose private String eventsUrl;

    @SerializedName("received_events_url")
    @Expose private String receivedEventsUrl;

    @Expose private String type;

    @SerializedName("site_admin")
    @Expose private Boolean siteAdmin;

    @Expose private String name;

    @Expose private Object company;

    @Expose private String blog;

    @Expose private String location;

    @Expose private Object email;

    @Expose private Object hireable;

    @Expose private Object bio;

    @SerializedName("public_repos")
    @Expose private Integer publicRepos;

    @SerializedName("public_gists")
    @Expose private Integer publicGists;

    @Expose private Integer followers;

    @Expose private Integer following;

    @SerializedName("created_at")
    @Expose private String createdAt;

    @SerializedName("updated_at")
    @Expose private String updatedAt;

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public String getGistsUrl() {
        return gistsUrl;
    }


    public String getStarredUrl() {
        return starredUrl;
    }

    public String getSubscriptionsUrl() {
        return subscriptionsUrl;
    }

    public String getOrganizationsUrl() {
        return organizationsUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public String getReceivedEventsUrl() {
        return receivedEventsUrl;
    }

    public String getType() {
        return type;
    }

    public Boolean getSiteAdmin() {
        return siteAdmin;
    }

    public String getName() {
        return name;
    }

    public Object getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public Object getEmail() {
        return email;
    }

    public Object getHireable() {
        return hireable;
    }

    public Object getBio() {
        return bio;
    }

    public Integer getPublicRepos() {
        return publicRepos;
    }

    public Integer getPublicGists() {
        return publicGists;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}