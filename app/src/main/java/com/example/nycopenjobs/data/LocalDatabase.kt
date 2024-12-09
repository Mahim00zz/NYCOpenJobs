package com.example.nycopenjobs.data

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nycopenjobs.model.FavoriteJobPost
import com.example.nycopenjobs.model.JobPost
import com.example.nycopenjobs.util.TAG
import kotlinx.coroutines.flow.Flow

@Dao
interface JobPostDao {
    //get ALL()
    @Query("SELECT * FROM JobPost ORDER BY postingLastUpdated DESC")
    fun getAll(): Flow<List<JobPost>>

    //getOne
    @Query("SELECT * FROM JobPost WHERE jobId = :id")
    fun get(id: Int): JobPost

    //update or insert job post
    @Upsert(entity = JobPost::class)
    suspend fun upsert(jobPostings: List<JobPost>)
}



@Database(entities = [JobPost::class,FavoriteJobPost::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    //we use a DAO (Data Access Object) to access the database
    abstract fun jobPostDao(): JobPostDao
    abstract fun favJobPostDao(): FavJobPostDao


    companion object {
        private const val DATABASE = "local_database"

        @Volatile
        private var Instance: LocalDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("""
            ALTER TABLE JobPost 
            ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0
        """)



                database.execSQL("""
            CREATE TABLE IF NOT EXISTS favorite_job_posts (
                jobId TEXT PRIMARY KEY NOT NULL
            )
        """)
            }
        }

        fun getDatabase(context: Context): LocalDatabase {
            Log.i(TAG, "getting database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE)
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration().
                    build().also { Instance = it }
            }
        }
    }
}


@Dao
interface FavJobPostDao {

    @Insert
    suspend fun markAsFavorite(favoriteJobPost: FavoriteJobPost)

    @Delete
    suspend fun unmarkAsFavorite(favoriteJobPost: FavoriteJobPost)

    @Query("SELECT COUNT(*) > 0 FROM favorite_job_posts WHERE jobId = :jobId")
    suspend fun isFavorite(jobId: Int): Boolean
}





