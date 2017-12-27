package com.mifengkong.frtools.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by jiangyongxing on 2017/7/9.
 * 描述：
 */

public class DaoSupportFactory {

    private static Context mContext;

    private static DaoSupportFactory mDaoSupportFactory;
    private SQLiteDatabase mSQLiteDatabase;

    public static void initContext(Context context){
        mContext = context;
    }

    private DaoSupportFactory() {
        this(mContext,"database");

    }

    private DaoSupportFactory(Context context, String dataBaseName) {
        if (context == null) {
            throw new IllegalArgumentException("请先initContext");
        }
        File dbRoot = context.getDatabasePath(dataBaseName);
//        File dbRoot = new File(context.getExternalCache Dir()  + File.separator
//                + dataBaseName + File.separator + "database");
//        Log.e("jiang", dbRoot.getAbsolutePath());
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }

        File dbFile = new File(dbRoot.getAbsolutePath(),"database.db");
        //打开或者创建一个数据库
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

    }

    public static DaoSupportFactory getFactory(Context context,String dataBaseName) {
        if (mDaoSupportFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mDaoSupportFactory == null) {
                    mDaoSupportFactory = new DaoSupportFactory(context,dataBaseName);
                }
            }
        }
        return mDaoSupportFactory;
    }

    public static DaoSupportFactory getFactory() {
        if (mDaoSupportFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mDaoSupportFactory == null) {
                    mDaoSupportFactory = new DaoSupportFactory();
                }
            }
        }
        return mDaoSupportFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport daoSupport = new DaoSupport();
        daoSupport.init(mSQLiteDatabase, clazz);
        return daoSupport;
    }
}
