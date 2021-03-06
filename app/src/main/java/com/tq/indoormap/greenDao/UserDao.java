package com.tq.indoormap.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tq.indoormap.entity.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Guid = new Property(1, String.class, "guid", false, "GUID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Gender = new Property(3, String.class, "gender", false, "GENDER");
        public final static Property Avatar = new Property(4, String.class, "avatar", false, "AVATAR");
        public final static Property Password = new Property(5, String.class, "password", false, "PASSWORD");
        public final static Property Birthday = new Property(6, String.class, "birthday", false, "BIRTHDAY");
        public final static Property Phone = new Property(7, String.class, "phone", false, "PHONE");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"GUID\" TEXT UNIQUE ," + // 1: guid
                "\"NAME\" TEXT," + // 2: name
                "\"GENDER\" TEXT," + // 3: gender
                "\"AVATAR\" TEXT," + // 4: avatar
                "\"PASSWORD\" TEXT," + // 5: password
                "\"BIRTHDAY\" TEXT," + // 6: birthday
                "\"PHONE\" TEXT);"); // 7: phone
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String guid = entity.getGuid();
        if (guid != null) {
            stmt.bindString(2, guid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(4, gender);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(5, avatar);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(6, password);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(7, birthday);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String guid = entity.getGuid();
        if (guid != null) {
            stmt.bindString(2, guid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(4, gender);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(5, avatar);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(6, password);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(7, birthday);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(8, phone);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // guid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // gender
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // avatar
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // password
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // birthday
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // phone
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGuid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGender(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAvatar(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPassword(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBirthday(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPhone(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
