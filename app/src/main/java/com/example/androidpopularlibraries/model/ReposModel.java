package com.example.androidpopularlibraries.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReposModel {
    @Expose private Integer id;

    @SerializedName("node_id")
    @Expose private String nodeId;

    @Expose private String name;

    @SerializedName("full_name")
    @Expose private String fullName;

    @Expose private Boolean _private;

    @Expose private UserModel userModel;

    @SerializedName("html_url")
    @Expose private String htmlUrl;

    @Expose private Object description;

    @Expose private Boolean fork;

    @Expose private String url;

    @SerializedName("forks_url")
    @Expose private String forksUrl;

    @SerializedName("keys_url")
    @Expose private String keysUrl;

    @SerializedName("collaborators_url")
    @Expose private String collaboratorsUrl;

    @SerializedName("teams_url")
    @Expose private String teamsUrl;

    @SerializedName("hooks_url")
    @Expose private String hooksUrl;

    @SerializedName("issue_events_url")
    @Expose private String issueEventsUrl;

    @SerializedName("events_url")
    @Expose private String eventsUrl;

    @SerializedName("assignees_url")
    @Expose private String assigneesUrl;

    @SerializedName("branches_url")
    @Expose private String branchesUrl;

    @SerializedName("tags_url")
    @Expose private String tagsUrl;

    @SerializedName("blobs_url")
    @Expose private String blobsUrl;

    @SerializedName("git_tags_url")
    @Expose private String gitTagsUrl;

    @SerializedName("git_refs_url")
    @Expose private String gitRefsUrl;

    @SerializedName("trees_url")
    @Expose private String treesUrl;

    @SerializedName("statuses_url")
    @Expose private String statusesUrl;

    @SerializedName("languages_url")
    @Expose private String languagesUrl;

    @SerializedName("stargazers_url")
    @Expose private String stargazersUrl;

    @SerializedName("contributors_url")
    @Expose private String contributorsUrl;

    @SerializedName("subscribers_url")
    @Expose private String subscribersUrl;

    @SerializedName("subscription_url")
    @Expose private String subscriptionUrl;

    @SerializedName("commits_url")
    @Expose private String commitsUrl;

    @SerializedName("git_commits_url")
    @Expose private String gitCommitsUrl;

    @SerializedName("comments_url")
    @Expose private String commentsUrl;

    @SerializedName("issue_comment_url")
    @Expose private String issueCommentUrl;

    @SerializedName("contents_url")
    @Expose private String contentsUrl;

    @SerializedName("compare_url")
    @Expose private String compareUrl;

    @SerializedName("merges_url")
    @Expose private String mergesUrl;

    @SerializedName("archive_url")
    @Expose private String archiveUrl;

    @SerializedName("downloads_url")
    @Expose private String downloadsUrl;

    @SerializedName("issues_url")
    @Expose private String issuesUrl;

    @SerializedName("pulls_url")
    @Expose private String pullsUrl;

    @SerializedName("milestones_url")
    @Expose private String milestonesUrl;

    @SerializedName("notifications_url")
    @Expose private String notificationsUrl;

    @SerializedName("labels_url")
    @Expose private String labelsUrl;

    @SerializedName("releases_url")
    @Expose private String releasesUrl;

    @SerializedName("deployments_url")
    @Expose private String deploymentsUrl;

    @SerializedName("created_at")
    @Expose private String createdAt;

    @SerializedName("updated_at")
    @Expose private String updatedAt;

    @SerializedName("pushed_at")
    @Expose private String pushedAt;

    @SerializedName("git_url")
    @Expose private String gitUrl;

    @SerializedName("ssh_url")
    @Expose private String sshUrl;

    @SerializedName("clone_url")
    @Expose private String cloneUrl;

    @SerializedName("svn_url")
    @Expose private String svnUrl;

    @Expose private Object homepage;

    @Expose private Integer size;

    @SerializedName("stargazers_count")
    @Expose private Integer stargazersCount;

    @SerializedName("watchers_count")
    @Expose private Integer watchersCount;

    @Expose private String language;

    @SerializedName("has_issues")
    @Expose private Boolean hasIssues;

    @SerializedName("has_projects")
    @Expose private Boolean hasProjects;

    @SerializedName("has_downloads")
    @Expose private Boolean hasDownloads;

    @SerializedName("has_wiki")
    @Expose private Boolean hasWiki;

    @SerializedName("has_pages")
    @Expose private Boolean hasPages;

    @SerializedName("forks_count")
    @Expose private Integer forksCount;

    @SerializedName("mirror_url")
    @Expose private Object mirrorUrl;

    @Expose private Boolean archived;

    @Expose private Boolean disabled;

    @SerializedName("open_issues_count")
    @Expose private Integer openIssuesCount;

    @Expose private Object license;

    @Expose private Integer forks;

    @SerializedName("open_issues")
    @Expose private Integer openIssues;

    @Expose private Integer watchers;

    @SerializedName("default_branch")
    @Expose private String defaultBranch;

    public Integer getId() {
        return id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean getPrivate() {
        return _private;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Object getDescription() {
        return description;
    }

    public Boolean getFork() {
        return fork;
    }

    public String getUrl() {
        return url;
    }

    public String getForksUrl() {
        return forksUrl;
    }

    public String getKeysUrl() {
        return keysUrl;
    }

    public String getCollaboratorsUrl() {
        return collaboratorsUrl;
    }

    public String getTeamsUrl() {
        return teamsUrl;
    }

    public String getHooksUrl() {
        return hooksUrl;
    }

    public String getIssueEventsUrl() {
        return issueEventsUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public String getAssigneesUrl() {
        return assigneesUrl;
    }

    public String getBranchesUrl() {
        return branchesUrl;
    }

    public String getTagsUrl() {
        return tagsUrl;
    }

    public String getBlobsUrl() {
        return blobsUrl;
    }

    public String getGitTagsUrl() {
        return gitTagsUrl;
    }

    public String getGitRefsUrl() {
        return gitRefsUrl;
    }

    public String getTreesUrl() {
        return treesUrl;
    }

    public String getStatusesUrl() {
        return statusesUrl;
    }

    public String getLanguagesUrl() {
        return languagesUrl;
    }

    public String getStargazersUrl() {
        return stargazersUrl;
    }

    public String getContributorsUrl() {
        return contributorsUrl;
    }

    public String getSubscribersUrl() {
        return subscribersUrl;
    }

    public String getSubscriptionUrl() {
        return subscriptionUrl;
    }

    public String getCommitsUrl() {
        return commitsUrl;
    }

    public String getGitCommitsUrl() {
        return gitCommitsUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public String getIssueCommentUrl() {
        return issueCommentUrl;
    }

    public String getContentsUrl() {
        return contentsUrl;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public String getMergesUrl() {
        return mergesUrl;
    }

    public String getArchiveUrl() {
        return archiveUrl;
    }

    public String getDownloadsUrl() {
        return downloadsUrl;
    }

    public String getIssuesUrl() {
        return issuesUrl;
    }

    public String getPullsUrl() {
        return pullsUrl;
    }

    public String getMilestonesUrl() {
        return milestonesUrl;
    }

    public String getNotificationsUrl() {
        return notificationsUrl;
    }

    public String getLabelsUrl() {
        return labelsUrl;
    }

    public String getReleasesUrl() {
        return releasesUrl;
    }

    public String getDeploymentsUrl() {
        return deploymentsUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public String getSvnUrl() {
        return svnUrl;
    }

    public Object getHomepage() {
        return homepage;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getHasIssues() {
        return hasIssues;
    }

    public Boolean getHasProjects() {
        return hasProjects;
    }

    public Boolean getHasDownloads() {
        return hasDownloads;
    }

    public Boolean getHasWiki() {
        return hasWiki;
    }

    public Boolean getHasPages() {
        return hasPages;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public Object getMirrorUrl() {
        return mirrorUrl;
    }

    public Boolean getArchived() {
        return archived;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public Integer getOpenIssuesCount() {
        return openIssuesCount;
    }

    public Object getLicense() {
        return license;
    }

    public Integer getForks() {
        return forks;
    }

    public Integer getOpenIssues() {
        return openIssues;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }
}
