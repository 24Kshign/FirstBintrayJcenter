package com.mifengkong.frtools.database;

import android.database.sqlite.SQLiteDatabase;

import com.mifengkong.frtools.database.curd.QuerySupport;

import java.util.List;

/**
 * Created by jiangyongxing on 2017/7/9.
 * 描述：
 */

public interface IDaoSupport<T> {
    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);
    // 插入数据
    public long insert(T t);

    // 批量插入  检测性能
    public void insert(List<T> datas);

    // 获取专门查询的支持类
    QuerySupport<T> querySupport();

    // 按照语句查询



    int delete(String whereClause, String... whereArgs);

    int update(T obj, String whereClause, String... whereArgs);
}
