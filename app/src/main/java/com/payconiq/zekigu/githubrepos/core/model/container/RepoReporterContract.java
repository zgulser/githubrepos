package com.payconiq.zekigu.githubrepos.core.model.container;

import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;

/**
 * Desc: This interface added for convenience to display whatever you want
 *       depending on your needs.
 */
public interface RepoReporterContract {
    String getRepoVisibleName(BaseRepo baseRepo);
}
