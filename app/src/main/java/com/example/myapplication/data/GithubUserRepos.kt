package com.example.myapplication.data


data class GithubUserRepos(
    val id: Int? = null,
    val node_Id: String? = null,
    val name: String? = null,
    val full_name: String? = null,
    val private: Boolean? = null,
    val html_url: String? = null,
    val description: String? = null,
    val fork: Boolean? = null,
    val url: String? = null,
    val forks_url: String? = null,
    val keys_url: String? = null,
    val collaborators_url: String? = null,
    val teams_url: String? = null,
    val hooks_url: String? = null,
    val issue_events_url: String? = null,
    val events_url: String? = null,
    val assignees_url: String? = null,
    val branches_url: String? = null,
    val tags_url: String? = null,
    val blobs_url: String? = null,
    val git_tags_url: String? = null,
    val git_refs_url: String? = null,
    val trees_url: String? = null,
    val statuses_url: String? = null,
    val languages_url: String? = null,
    val stargazers_url: String? = null,
    val contributors_url: String? = null,
    val subscribers_url: String? = null,
    val subscription_url: String? = null,
    val commits_url: String? = null,
    val git_commits_url: String? = null,
    val comments_url: String? = null,
    val issue_comment_url: String? = null,
    val contents_url: String? = null,
    val compare_url: String? = null,
    val merges_url: String? = null,
    val archive_url: String? = null,
    val downloads_url: String? = null,
    val issues_url: String? = null,
    val pulls_url: String? = null,
    val milestones_url: String? = null,
    val notifications_url: String? = null,
    val labels_url: String? = null,
    val releases_url: String? = null,
    val deployments_url: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val pushedAt: String? = null,
    val git_url: String? = null,
    val ssh_url: String? = null,
    val clone_url: String? = null,
    val svn_url: String? = null,
    val homepage: String? = null,
    val size: Int? = null,
    val stargazers_count: Int? = null,
    val watchers_count: Int? = null,
    val language: String? = null,
    val has_issues: Boolean? = null,
    val has_projects: Boolean? = null,
    val has_downloads: Boolean? = null,
    val hasWiki: Boolean? = null,
    val has_pages: Boolean? = null,
    val has_discussions: Boolean? = null,
    val forks_count: Int? = null,
    val mirror_url: String? = null,
    val archived: Boolean? = null,
    val disabled: Boolean? = null,
    val openIssuesCount: Int? = null,
    val license: License? = License(),
    val allowForking: Boolean? = null,
    val web_commit_signoff_required: Boolean? = null,
    val topics: ArrayList<String> = arrayListOf(),
    val visibility: String? = null,
    val forks: Int? = null,
    val open_issues: Int? = null,
    val watchers: Int? = null,
    val default_branch: String? = null

)