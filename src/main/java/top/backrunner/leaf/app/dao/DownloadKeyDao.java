package top.backrunner.leaf.app.dao;

import top.backrunner.leaf.app.entity.DownloadKeyInfo;
import top.backrunner.leaf.core.dao.BaseDao;

public interface DownloadKeyDao extends BaseDao<DownloadKeyInfo> {
    public boolean exists(String key);
    public DownloadKeyInfo get(String key);
}
