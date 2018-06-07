package pl.jurassic.roger

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val it = arrayListOf<DateTime>(DateTime.now().minusDays(103), DateTime.now().minusDays(93), DateTime.now().minusDays(70), DateTime.now())

        println("Day of Weeek ${it.first().dayOfWeek}")

        it.forEach {
            println("Day of year ${it.dayOfYear}")
        }

        println("\n\n\n")

        val someList = arrayListOf<DateTime>()

        val tmp = it.first()
        for (i in (1 until tmp.dayOfWeek).reversed()) {
            someList.add(tmp.minusDays(i))
        }

        for (i in 0 until it.size - 1) {
            someList.add(it[i])
            val lol = it[i + 1].dayOfYear - it[i].dayOfYear
            for (x in 1 until lol) {
                someList.add(it[i].plusDays(x))
            }
        }

        someList.add(it.last())

        someList.forEach {
            println(it.dayOfYear)
        }

        assertEquals(someList.last().dayOfYear - someList.first().dayOfYear, someList.size)
    }
}
