package pl.jurassic.roger.util.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import pl.jurassic.roger.data.database.BreakTimeData
import pl.jurassic.roger.data.database.JobTimeData
import pl.jurassic.roger.data.database.WorkTimeData

@Dao
interface WorkTimeDao {

    @Transaction
    @Query("SELECT * FROM job_time")
    fun getAllWorkTime(): Single<List<WorkTimeData>>

    @Insert(onConflict = REPLACE)
    fun insertJobTime(jobTimeData: JobTimeData)

    @Insert(onConflict = REPLACE)
    fun insertBreakTime(breakTimeData: BreakTimeData)
}